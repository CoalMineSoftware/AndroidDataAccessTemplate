package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class NamedColumnShortRowMapper implements RowMapper<Short> {
	private String columnName;

	public NamedColumnShortRowMapper(final String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Short mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getShort(cursor, columnName);
	}
}


