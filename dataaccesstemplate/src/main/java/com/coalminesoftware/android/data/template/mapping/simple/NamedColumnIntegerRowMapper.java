package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class NamedColumnIntegerRowMapper implements RowMapper<Integer> {
	private String mColumnName;

	public NamedColumnIntegerRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, mColumnName);
	}
}
