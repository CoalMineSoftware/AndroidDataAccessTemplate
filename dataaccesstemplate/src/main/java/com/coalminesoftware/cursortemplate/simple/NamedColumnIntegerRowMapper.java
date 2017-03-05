package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnIntegerRowMapper implements RowMapper<Integer> {
	private String mColumnName;

	public NamedColumnIntegerRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, mColumnName);
	}
}
