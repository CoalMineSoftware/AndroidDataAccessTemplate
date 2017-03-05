package com.coalminesoftware.cursortemplate;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.contentprovider.ContentProviderClientTemplate;

/** Used with {@link ContentProviderClientTemplate} query() methods when a one-to-one relationship
 * exists between Cursor rows and business objects, which are returned in a list.
 * 
 *  @see RowCallbackHandler
 *  @see ContentProviderClientTemplate */
public interface RowMapper<RowModel> {
	/** Used to build an object for each row in the given Cursor, without altering it with calls like {@link Cursor#moveToNext()} or {@link Cursor#close()}. */
	RowModel mapRow(Cursor cursor, int rowNumber);
}


