package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnBlobRowMapper implements RowMapper<byte[]> {
	private String mColumnName;

	public NamedColumnBlobRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public byte[] mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBlob(cursor, mColumnName);
	}
}
