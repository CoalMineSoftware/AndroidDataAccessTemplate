package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


