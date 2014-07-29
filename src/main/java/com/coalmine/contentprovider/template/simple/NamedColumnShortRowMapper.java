package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

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


