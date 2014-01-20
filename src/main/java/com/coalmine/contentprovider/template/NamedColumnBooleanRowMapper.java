package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnBooleanRowMapper implements RowMapper<Boolean> {
	private String columnName;

	public NamedColumnBooleanRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Boolean mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBoolean(cursor, columnName);
	}
}


