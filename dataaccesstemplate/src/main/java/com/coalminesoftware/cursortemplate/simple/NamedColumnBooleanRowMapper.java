package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

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


