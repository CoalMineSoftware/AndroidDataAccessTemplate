package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnByteRowMapper implements RowMapper<Byte> {
	@Override
	public Byte mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getByte(cursor, 0);
	}
}
