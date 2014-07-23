package com.coalmine.contentprovider.template;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.MatrixCursor;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ContentProviderClientTemplateTest {
	private static final String AUTHORITY = "com.company.project.package";


	@Before
    public void setup() {
        ContentProvider provider = null;
        ContentResolver resolver = Robolectric.application.getContentResolver();
        ShadowContentResolver shadowResolver = Robolectric.shadowOf(resolver);
        provider.onCreate();
        ShadowContentResolver.registerProvider(AUTHORITY, provider);
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


