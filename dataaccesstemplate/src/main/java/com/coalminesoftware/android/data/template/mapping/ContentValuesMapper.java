package com.coalminesoftware.android.data.template.mapping;

import android.content.ContentValues;

public interface ContentValuesMapper<RowModel> {
	ContentValues mapContentValues(RowModel rowModel);
}
