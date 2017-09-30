package com.coalminesoftware.android.data.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.RowMapper;

public class NamedColumnDoubleRowMapper implements RowMapper<Double> {
	private String mColumnName;

	public NamedColumnDoubleRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Double mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getDouble(cursor, mColumnName);
	}
}
