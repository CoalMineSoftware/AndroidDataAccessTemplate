package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnShortRowMapper implements RowMapper<Short> {
	private String columnName;

	public NamedColumnShortRowMapper(final String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Short mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getShort(cursor, columnName);
	}
}


