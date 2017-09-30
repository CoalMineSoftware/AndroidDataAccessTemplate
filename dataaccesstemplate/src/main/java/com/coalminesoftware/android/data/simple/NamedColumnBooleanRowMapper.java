package com.coalminesoftware.android.data.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.RowMapper;

public class NamedColumnBooleanRowMapper implements RowMapper<Boolean> {
	private String mColumnName;

	public NamedColumnBooleanRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Boolean mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBoolean(cursor, mColumnName);
	}
}


