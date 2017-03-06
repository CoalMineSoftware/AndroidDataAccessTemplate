Android Data Access Template (formerly ContentProviderTemplate)
===============================================================


Modeled after [Spring](http://spring.io/) JdbcTemplate, Data Access Template aims to streamline the use of Android [content providers](http://developer.android.com/guide/topics/providers/content-providers.html) and [SQLite databases](https://developer.android.com/training/basics/data-storage/databases.html). Just like JdbcTemplate encapsulates the details of querying, Data Access Template encapsulates the details of not only querying but also inserting and updating, greatly reducing the amount of boilerplate code needed in a project.

The design of Data Access Template also encourages developers to separate object-building and `ContentValues`-building logic from other responsibilities that sometimes get intertwined with other data access code. Developers are encouraged to write self-contained `RowMapper`/`RowCallbackHandler` implementations for querying, and `ContentValuesMapper` implementations for inserting and updating. Such classes serve a single purpose and can potentially be reused throughout a project (or projects.)

For convenience, the library provides simple `RowMapper` implementations for use when querying the value of a single column. There are even annotation-driven mapper implementations – `AnnotationRowMapper` and `AnnotationContentValuesMapper` – that allow you to map to and from your model classes by simply annotating fields that you want the mapper to handle.

Content Provider Example Usages
-------------------------------

To query for a list of business objects, where each `Cursor` row corresponds to an instance of a given business object, write a mapper like the one below:

    public class ContactRowMapper implements RowMapper<Contact> {
        // Column names the mapper expects in a Cursor. Not required but recommended.
        public static String[] REQUIRED_COLUMNS = new String[] {
                ContactsContract.Contracts._ID,
                ContactsContract.Contracts.DISPLAY_NAME_PRIMARY};
        
        @Override
        public Contact mapRow(Cursor cursor, int rowNumber) {
            Contact contact = new Contact();
            
            contact.setId(CursorUtils.getLong(cursor, ContactsContract.Contracts._ID));
            contact.setDisplayName(CursorUtils.getString(cursor, ContactsContract.Contracts.DISPLAY_NAME_PRIMARY));
            
            return contact;
        }
    }

Then construct a `ContentProviderClientTemplate` using either a `ContentResolver` or `ContentProviderClient` and use it to query the relevant URI:

    List<Contact> contacts = new ContentProviderClientTemplate(resolver).queryForList(
            ContactsContract.Contacts.CONTENT_URI,
            ContactRowMapper.REQUIRED_COLUMNS,
            new ContactRowMapper()) {

Inserting and updating a ContentProvider works similarly, but using a `ContentValuesMapper` to generate a `ContentValues` object from a model object:

    public class WidgetContentValuesMapper implements ContentValuesMapper<Widget> {
        @Override
        public ContentValues mapContentValues(Widget widget) {
            ContentValues contentValues = new ContentValues();
            
            contentValues.put(valueKey, widget.getFoo());
            contentValues.put(valueKey, widget.getBar());
            
            return contentValues;
        }
    }

SQLiteDatabase Usage
--------------------

Because ContentProviders and SQLiteDatabases have similar interfaces that both rely on `Cursors` and `ContentValues`, interacting with a SQLite database using Data Access Template is done in a very similar way. Instead of using `ContentProviderClientTemplate`, developers use `SQLiteDatabaseClientTemplate`.

In scenarios where it is necessary to use transactions, see `SQLiteDatabaseClientTemplate#performTransaction(TransactionCallback callback)`.
