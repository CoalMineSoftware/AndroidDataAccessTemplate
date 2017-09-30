package com.coalminesoftware.android.data;

import android.content.ContentValues;

public interface ContentValuesMapper<RowModel> {
	ContentValues mapContentValues(RowModel rowModel);
}
