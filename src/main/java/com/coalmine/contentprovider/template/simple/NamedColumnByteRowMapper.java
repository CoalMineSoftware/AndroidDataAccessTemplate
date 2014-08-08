package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class NamedColumnByteRowMapper implements RowMapper<Byte> {
	private String columnName;

	public NamedColumnByteRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Byte mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getByte(cursor, columnName);
	}
}


