package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnShortRowMapper implements RowMapper<Short> {
	@Override
	public Short mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getShort(cursor, 0);
	}
}
