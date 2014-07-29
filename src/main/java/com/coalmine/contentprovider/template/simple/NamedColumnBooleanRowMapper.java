package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


