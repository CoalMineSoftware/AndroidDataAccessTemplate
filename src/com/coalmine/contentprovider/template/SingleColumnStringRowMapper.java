package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnStringRowMapper implements RowMapper<String> {
	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, 0);
	}
}


