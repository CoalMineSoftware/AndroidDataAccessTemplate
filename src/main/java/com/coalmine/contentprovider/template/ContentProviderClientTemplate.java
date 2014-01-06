package com.coalmine.contentprovider.template;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

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
}


