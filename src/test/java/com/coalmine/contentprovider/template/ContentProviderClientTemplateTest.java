package com.coalmine.contentprovider.template;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowContentResolver;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.MatrixCursor;

public class ContentProviderClientTemplateTest {
    @BeforeClass
    public void setupOnce() {
        ContentProvider provider = null;
        ContentResolver resolver = Robolectric.application.getContentResolver();
        ShadowContentResolver shadowResolver = Robolectric.shadowOf(resolver);
        provider.onCreate();
        ShadowContentResolver.registerProvider("com.auth", provider);
    }


	@Test
	public void testQueryWithRowMapper_withSingleCursorRow() {
		int testValue = 100;

		MatrixCursor cursor = new MatrixCursor(new String[]{"x"});
		cursor.addRow(new Integer[]{testValue});

		// TODO Mock a ContentProvider in Robolectric?
	}

	@Test(expected=IllegalArgumentException.class)
	public void testQueryWithRowMapper_cursorWithMultipleRows() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForListWithRowMapper() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWithRowCallbackHandler() {
		fail("Not yet implemented");
	}
}


