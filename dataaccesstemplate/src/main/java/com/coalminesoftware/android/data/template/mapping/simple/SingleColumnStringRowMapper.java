package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class SingleColumnStringRowMapper implements RowMapper<String> {
	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, 0);
	}
}
