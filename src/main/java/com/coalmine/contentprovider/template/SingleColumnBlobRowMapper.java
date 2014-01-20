package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnBlobRowMapper implements RowMapper<byte[]> {
	@Override
	public byte[] mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBlob(cursor, 0);
	}
}


