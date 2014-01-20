package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnStringRowMapper implements RowMapper<String> {
	private String columnName;

	public NamedColumnStringRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, columnName);
	}
}


