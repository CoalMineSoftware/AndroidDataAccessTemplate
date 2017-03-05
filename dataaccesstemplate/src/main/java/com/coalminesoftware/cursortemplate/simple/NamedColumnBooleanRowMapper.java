package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnBooleanRowMapper implements RowMapper<Boolean> {
	private String columnName;

	public NamedColumnBooleanRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Boolean mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBoolean(cursor, columnName);
	}
}


