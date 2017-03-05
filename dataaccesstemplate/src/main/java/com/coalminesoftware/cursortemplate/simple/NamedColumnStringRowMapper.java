package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnStringRowMapper implements RowMapper<String> {
	private String mColumnName;

	public NamedColumnStringRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, mColumnName);
	}
}
