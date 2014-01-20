package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class NamedColumnDoubleRowMapper implements RowMapper<Double> {
	private String columnName;

	public NamedColumnDoubleRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Double mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getDouble(cursor, columnName);
	}
}


