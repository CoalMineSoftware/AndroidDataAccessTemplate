package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnLongRowMapper implements RowMapper<Long> {
	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, 0);
	}
}
