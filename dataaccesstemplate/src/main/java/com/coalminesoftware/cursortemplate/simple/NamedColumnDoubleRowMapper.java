package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnDoubleRowMapper implements RowMapper<Double> {
	private String columnName;

	public NamedColumnDoubleRowMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Double mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getDouble(cursor, columnName);
	}
}

