package com.coalmine.contentprovider.template.contentvalue;

import android.content.ContentValues;

public interface ContentValuesMapper<RowModel> {
	ContentValues mapContentValues(RowModel rowModel);
}


