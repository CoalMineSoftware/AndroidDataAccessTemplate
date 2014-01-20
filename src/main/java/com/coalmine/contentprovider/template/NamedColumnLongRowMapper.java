package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnLongRowMapper implements RowMapper<Long> {
	private String columnName;

	public NamedColumnLongRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, columnName);
	}
}


