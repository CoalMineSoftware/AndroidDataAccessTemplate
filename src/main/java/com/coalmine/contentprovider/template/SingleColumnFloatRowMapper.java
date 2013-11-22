package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnFloatRowMapper implements RowMapper<Float> {
	@Override
	public Float mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getFloat(cursor, 0);
	}
}


