package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnIntegerRowMapper implements RowMapper<Integer> {
	private String columnName;

	public NamedColumnIntegerRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, columnName);
	}
}


