package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnBooleanRowMapper implements RowMapper<Boolean> {
	@Override
	public Boolean mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getBoolean(cursor, 0);
	}
}
