package com.coalminesoftware.android.data.template.mapping.simple;

import android.database.Cursor;

import com.coalminesoftware.android.data.CursorUtils;
import com.coalminesoftware.android.data.template.mapping.RowMapper;

public class NamedColumnShortRowMapper implements RowMapper<Short> {
	private String mColumnName;

	public NamedColumnShortRowMapper(final String columnName) {
		mColumnName = columnName;
	}

	@Override
	public Short mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getShort(cursor, mColumnName);
	}
}
