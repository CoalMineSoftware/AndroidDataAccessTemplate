package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnFloatRowMapper implements RowMapper<Float> {
	private String columnName;

	public NamedColumnFloatRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Float mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getFloat(cursor, columnName);
	}
}


