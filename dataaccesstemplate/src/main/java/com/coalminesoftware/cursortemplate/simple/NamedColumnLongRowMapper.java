package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnLongRowMapper implements RowMapper<Long> {
	private String columnName;

	public NamedColumnLongRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, columnName);
	}
}


