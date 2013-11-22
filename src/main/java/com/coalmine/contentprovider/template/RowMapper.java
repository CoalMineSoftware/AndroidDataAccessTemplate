package com.coalmine.contentprovider.template;

import android.database.Cursor;

public interface RowMapper<RowModel> {
	RowModel mapRow(Cursor cursor, int rowNumber);
}


