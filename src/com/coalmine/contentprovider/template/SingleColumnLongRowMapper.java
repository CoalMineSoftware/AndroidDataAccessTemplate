package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnLongRowMapper implements RowMapper<Long> {
	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, 0);
	}
}


