package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnFloatRowMapper implements RowMapper<Float> {
	private String mColumnName;

	public NamedColumnFloatRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Float mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getFloat(cursor, mColumnName);
	}
}
