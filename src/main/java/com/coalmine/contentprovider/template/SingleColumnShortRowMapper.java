package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnShortRowMapper implements RowMapper<Short> {
	@Override
	public Short mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getShort(cursor, 0);
	}
}


