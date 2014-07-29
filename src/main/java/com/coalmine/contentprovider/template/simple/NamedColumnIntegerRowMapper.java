package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


