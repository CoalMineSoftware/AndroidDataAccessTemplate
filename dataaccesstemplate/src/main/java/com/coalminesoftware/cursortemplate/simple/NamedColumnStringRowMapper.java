package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnStringRowMapper implements RowMapper<String> {
	private String columnName;

	public NamedColumnStringRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, columnName);
	}
}


