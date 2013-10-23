package com.coalmine.contentprovider.template;

import android.database.Cursor;

public class SingleColumnDoubleRowMapper implements RowMapper<Double> {
	@Override
	public Double mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getDouble(cursor, 0);
	}
}


