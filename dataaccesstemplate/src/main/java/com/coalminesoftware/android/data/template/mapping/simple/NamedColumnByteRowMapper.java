package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

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
