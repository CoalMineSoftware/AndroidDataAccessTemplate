package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class NamedColumnStringRowMapper implements RowMapper<String> {
	private String mColumnName;

	public NamedColumnStringRowMapper(String columnName) {
		mColumnName = columnName;
	}

	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, mColumnName);
	}
}
