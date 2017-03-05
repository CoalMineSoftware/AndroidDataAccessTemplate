package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnLongRowMapper implements RowMapper<Long> {
	private String mColumnName;

	public NamedColumnLongRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, mColumnName);
	}
}
