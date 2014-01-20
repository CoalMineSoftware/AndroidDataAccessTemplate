package com.coalmine.contentprovider.template;

import android.database.Cursor;

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


