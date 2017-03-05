package com.coalminesoftware.cursortemplate.simple;

import android.database.Cursor;

import com.coalminesoftware.cursortemplate.CursorUtils;
import com.coalminesoftware.cursortemplate.RowMapper;

public class SingleColumnFloatRowMapper implements RowMapper<Float> {
	@Override
	public Float mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getFloat(cursor, 0);
	}
}


