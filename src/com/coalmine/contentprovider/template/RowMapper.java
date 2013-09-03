package com.coalmine.contentprovider.template;

import android.database.Cursor;

public interface RowMapper<T> {
	T mapRow(Cursor cursor, int rowNumber);
}


