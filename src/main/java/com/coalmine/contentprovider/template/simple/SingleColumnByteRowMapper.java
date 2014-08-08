package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class SingleColumnByteRowMapper implements RowMapper<Byte> {
	@Override
	public Byte mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getByte(cursor, 0);
	}
}


