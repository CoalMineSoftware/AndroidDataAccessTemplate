package com.coalmine.contentprovider.template;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class ContentProviderClientTemplate {
	private ContentResolver contentResolver;


	public ContentProviderClientTemplate() { }

	public ContentProviderClientTemplate(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}


	public <T> T query(Uri uri, String selectClause, String[] selectionArguments, RowMapper<T> rowMapper) {
		T queryResult = null;

		Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, null, selectClause, selectionArguments, null);
            if(cursor.moveToNext()) {
            	if(!cursor.isLast()) {
            		throw new IllegalArgumentException("Multiple rows returned for query.");
            	}

            	queryResult = rowMapper.mapRow(cursor, 0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return queryResult;
	}

	public <T> List<T> queryForList(Uri uri, String selectClause, String[] selectionArguments, RowMapper<T> rowMapper) {
		List<T> queryResults = new ArrayList<T>();

		Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, null, selectClause, selectionArguments, null);
            int rowNumber = 0;
            while(cursor.moveToNext()) {
            	queryResults.add(rowMapper.mapRow(cursor, rowNumber++));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return queryResults;
	}

	public void query(Uri uri, String selectClause, String[] selectionArguments, RowCallbackHandler callbackHandler) {
		Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, null, selectClause, selectionArguments, null);
            int rowNumber = 0;
            while(cursor.moveToNext()) {
            	callbackHandler.processRow(cursor, rowNumber++);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
	}

	public void setContentResolver(ContentResolver contentResolver) {
	    this.contentResolver = contentResolver;
    }
}


