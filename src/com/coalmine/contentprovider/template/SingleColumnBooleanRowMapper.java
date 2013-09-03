package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnBooleanRowMapper implements RowMapper<Boolean> {
	@Override
	public Boolean mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getNullableBoolean(cursor, 0);
	}
}


