package com.coalminesoftware.android.data.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.RowMapper;

public class SingleColumnBlobRowMapper implements RowMapper<byte[]> {
	@Override
	public byte[] mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBlob(cursor, 0);
	}
}
