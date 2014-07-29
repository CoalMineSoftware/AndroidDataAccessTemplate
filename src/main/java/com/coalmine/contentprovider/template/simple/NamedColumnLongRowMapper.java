package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


