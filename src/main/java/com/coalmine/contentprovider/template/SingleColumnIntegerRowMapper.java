package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnIntegerRowMapper implements RowMapper<Integer> {
	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, 0);
	}
}


