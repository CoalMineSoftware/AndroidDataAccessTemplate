package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class NamedColumnLongRowMapper implements RowMapper<Long> {
	private String mColumnName;

	public NamedColumnLongRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Long mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getLong(cursor, mColumnName);
	}
}
