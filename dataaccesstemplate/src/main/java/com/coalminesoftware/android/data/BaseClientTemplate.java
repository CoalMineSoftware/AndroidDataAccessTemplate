package com.coalminesoftware.android.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.database.Cursor;

public class BaseClientTemplate {
	protected <RowModel> RowModel mapRow(Cursor cursor, RowMapper<RowModel> rowMapper) {
		if(!cursor.moveToNext()) {
			return null;
		}

		if(!cursor.isLast()) {
			throw new IllegalArgumentException("Multiple rows returned for the given query.");
		}

		return rowMapper.mapRow(cursor, 0);
	}

	protected <RowModel> List<RowModel> mapRows(Cursor cursor, RowMapper<RowModel> rowMapper) {
		List<RowModel> queryResults = new ArrayList<RowModel>();

		int rowNumber = 0;
		while(cursor.moveToNext()) {
			queryResults.add(rowMapper.mapRow(cursor, rowNumber++));
		}

		return queryResults;
	}

	protected void processRows(Cursor cursor, RowCallbackHandler callbackHandler) {
		int rowNumber = 0;
		while(cursor.moveToNext()) {
			callbackHandler.processRow(cursor, rowNumber++);
		}
	}

	protected static String[] toArray(Collection<String> strings) {
		return strings == null?
				null :
				strings.toArray(new String[strings.size()]);
	}
}
