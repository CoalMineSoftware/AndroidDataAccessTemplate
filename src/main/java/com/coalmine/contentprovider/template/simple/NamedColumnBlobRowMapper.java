package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class NamedColumnBlobRowMapper implements RowMapper<byte[]> {
	private String columnName;

	public NamedColumnBlobRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public byte[] mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBlob(cursor, columnName);
	}
}


