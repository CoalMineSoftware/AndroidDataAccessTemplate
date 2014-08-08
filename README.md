ContentProviderTemplate
=======================

Modeled after [Spring](http://spring.io/) JdbcTemplate, ContentProviderTemplate aims to streamline the use of Android [ContentProviders](http://developer.android.com/guide/topics/providers/content-providers.html).  Like JdbcTemplate encapsulates the details of querying, ContentProviderTemplate encapsulates the details of not only querying but also inserting and updating, greatly reducing the amount of boilerplate code needed in a project.

The design of ContentProviderTemplate also encourages developers to separate object-building and ContentValues-building logic from other responsibilities that sometimes get intertwined with other data access code.  Developers are encouraged to write self-contained RowMapper/RowCallbackHandler implemenations for querying, and ContentValuesMapper implementations for inserting/updating.  Such mappers and handlers serve a single purpose and can potentially be reused throughout a project (or projects.)

For convenience, the library provides simple RowMapper implementations for use when querying the value of a single column.  There are even annotation-driven mapper implementations (AnnotationRowMapper and AnnotationContentValuesMapper) that allow you to map to and from your model classes by simply annotating fields that you want the mapper to handle.
