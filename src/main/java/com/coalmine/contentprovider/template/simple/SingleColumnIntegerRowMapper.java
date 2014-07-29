package com.coalmine.contentprovider.template.simple;

import android.database.Cursor;

import com.coalmine.contentprovider.template.CursorUtils;
import com.coalmine.contentprovider.template.RowMapper;

public class SingleColumnIntegerRowMapper implements RowMapper<Integer> {
	@Override
	public Integer mapRow(Cursor cursor, int rowNumber) {
		return CursorUtils.getInteger(cursor, 0);
	}
}


