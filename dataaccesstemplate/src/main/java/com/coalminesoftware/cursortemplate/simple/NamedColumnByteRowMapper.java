package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnByteRowMapper implements RowMapper<Byte> {
	private String mColumnName;

	public NamedColumnByteRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Byte mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getByte(cursor, mColumnName);
	}
}
