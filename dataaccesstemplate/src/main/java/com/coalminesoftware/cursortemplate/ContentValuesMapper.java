package com.coalminesoftware.cursortemplate;

import android.content.ContentValues;

public interface ContentValuesMapper<RowModel> {
	ContentValues mapContentValues(RowModel rowModel);
}
