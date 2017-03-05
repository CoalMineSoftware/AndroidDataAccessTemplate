package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnFloatRowMapper implements RowMapper<Float> {
	private String columnName;

	public NamedColumnFloatRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Float mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getFloat(cursor, columnName);
	}
}
