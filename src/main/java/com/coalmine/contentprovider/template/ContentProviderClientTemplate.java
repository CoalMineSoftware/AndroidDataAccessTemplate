package com.coalmine.contentprovider.template;

import java.util.ArrayList;
import java.util.List;

import com.coalmine.contentprovider.template.contentvalue.ContentValuesMapper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/** Providers a simpler API for developers to interact with {@link ContentProvider}s, eliminating
 * much of the boilerplate code.  The design also encouraging developers to write self-contained,
 * reusable {@link RowMapper}s and {@link RowCallbackHandler}s for building business objects from
 * Cursor rows, and {@link ContentValuesMapper}s for generating {@link ContentValues} from business
 * objects. */
public class ContentProviderClientTemplate {
	private ContentResolver contentResolver;


	public ContentProviderClientTemplate(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}


	public <RowModel> RowModel query(Uri uri, String[] projection, RowMapper<RowModel> rowMapper) {
		return query(uri, projection, null, null, null, rowMapper);
	}

	public <RowModel> RowModel query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder, RowMapper<RowModel> rowMapper) {
		RowModel queryResult = null;

		Cursor cursor = null;
		try {
			cursor = contentResolver.query(uri, projection, selectClause, selectionArguments, sortOrder);
			if(cursor.moveToNext()) {
				if(!cursor.isLast()) {
					throw new IllegalArgumentException("Multiple rows returned for the given URI/query.");
				}

				queryResult = rowMapper.mapRow(cursor, 0);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return queryResult;
	}

	public <RowModel> List<RowModel> queryForList(Uri uri, String[] projection, RowMapper<RowModel> rowMapper) {
		return queryForList(uri, projection, null, null, null, rowMapper);
	}

	public <RowModel> List<RowModel> queryForList(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder, RowMapper<RowModel> rowMapper) {
		List<RowModel> queryResults = new ArrayList<RowModel>();

		Cursor cursor = null;
		try {
			cursor = contentResolver.query(uri, projection, selectClause, selectionArguments, sortOrder);
			int rowNumber = 0;
			while(cursor.moveToNext()) {
				queryResults.add(rowMapper.mapRow(cursor, rowNumber++));
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return queryResults;
	}

	public void query(Uri uri, String[] projection, RowCallbackHandler callbackHandler) {
		query(uri, projection, null, null, null, callbackHandler);
	}

	public void query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder, RowCallbackHandler callbackHandler) {
		Cursor cursor = null;
		try {
			int rowNumber = 0;
			cursor = contentResolver.query(uri, projection, selectClause, selectionArguments, sortOrder);
			while(cursor.moveToNext()) {
				callbackHandler.processRow(cursor, rowNumber++);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * Inserts a record into the given URI, using the given mapper to generate the ContentValues.
	 * 
	 * @return The URI of the inserted record, as reported by the ContentProvider being inserted into.
	 */
	public <RowModel> Uri insert(Uri uri, RowModel rowObject, ContentValuesMapper<RowModel> mapper) {
		return contentResolver.insert(uri, mapper.mapContentValues(rowObject));
	}

	/**
	 * Updates the given URI, using the given mapper to generate the ContentValues.
	 * 
	 * @return The number of rows affected by the update.
	 */
	public <RowModel> int update(Uri uri, RowModel rowObject, ContentValuesMapper<RowModel> mapper, String whereClause, String[] selectionArguments) {
		return contentResolver.update(uri, mapper.mapContentValues(rowObject), whereClause, selectionArguments);
	}

	/**
	 * Updates the given URI, using the given mapper to generate the ContentValues.
	 * 
	 * @return The number of rows affected by the update.
	 */
	public <RowModel> int update(Uri uri, RowModel rowObject, ContentValuesMapper<RowModel> mapper) {
		return update(uri, rowObject, mapper, null, null);
	}
}


