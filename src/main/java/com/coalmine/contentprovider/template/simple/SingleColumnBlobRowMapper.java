package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class SingleColumnBlobRowMapper implements RowMapper<byte[]> {
	@Override
	public byte[] mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBlob(cursor, 0);
	}
}


