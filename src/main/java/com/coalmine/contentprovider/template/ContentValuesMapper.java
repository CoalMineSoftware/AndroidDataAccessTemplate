package com.coalmine.contentprovider.template;

import android.content.ContentValues;

public interface ContentValuesMapper<RowModel> {
	ContentValues mapContentValues(RowModel rowModel);
}


