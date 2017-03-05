package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class SingleColumnBooleanRowMapper implements RowMapper<Boolean> {
	@Override
	public Boolean mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBoolean(cursor, 0);
	}
}
