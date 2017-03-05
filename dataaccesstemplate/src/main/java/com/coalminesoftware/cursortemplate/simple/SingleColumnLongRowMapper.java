package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class SingleColumnLongRowMapper implements RowMapper<Long> {
	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, 0);
	}
}


