package com.coalminesoftware.android.data.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.RowMapper;

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
