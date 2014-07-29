package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class SingleColumnDoubleRowMapper implements RowMapper<Double> {
	@Override
	public Double mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getDouble(cursor, 0);
	}
}


