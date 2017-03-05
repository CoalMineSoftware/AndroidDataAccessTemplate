package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnIntegerRowMapper implements RowMapper<Integer> {
	private String columnName;

	public NamedColumnIntegerRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, columnName);
	}
}
