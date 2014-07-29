package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


