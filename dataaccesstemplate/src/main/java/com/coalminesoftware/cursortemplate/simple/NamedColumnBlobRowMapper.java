package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

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


