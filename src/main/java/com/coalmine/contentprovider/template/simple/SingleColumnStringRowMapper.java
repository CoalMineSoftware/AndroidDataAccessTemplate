package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class SingleColumnStringRowMapper implements RowMapper<String> {
	@Override
	public String mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getString(cursor, 0);
	}
}


