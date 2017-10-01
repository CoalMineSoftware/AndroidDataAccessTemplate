package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnDoubleRowMapper implements RowMapper<Double> {
	@Override
	public Double mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getDouble(cursor, 0);
	}
}
