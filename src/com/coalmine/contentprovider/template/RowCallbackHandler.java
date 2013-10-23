package com.coalmine.contentprovider.template;

import android.database.Cursor;

public interface RowCallbackHandler {
	void processRow(Cursor cursor, int rowNumber);
}


