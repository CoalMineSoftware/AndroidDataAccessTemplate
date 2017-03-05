package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class SingleColumnShortRowMapper implements RowMapper<Short> {
	@Override
	public Short mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getShort(cursor, 0);
	}
}


