package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnFloatRowMapper implements RowMapper<Float> {
	@Override
	public Float mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getFloat(cursor, 0);
	}
}
