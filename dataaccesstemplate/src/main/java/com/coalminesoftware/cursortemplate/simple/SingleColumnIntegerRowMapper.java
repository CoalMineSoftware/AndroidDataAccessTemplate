package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class SingleColumnIntegerRowMapper implements RowMapper<Integer> {
	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, 0);
	}
}


