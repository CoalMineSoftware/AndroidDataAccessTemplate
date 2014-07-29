package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


