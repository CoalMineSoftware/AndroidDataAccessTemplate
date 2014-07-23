package com.coalmine.contentprovider.template;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fest.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ContentProviderClientTemplateTest {
	private TestContentProvider provider;
	private ContentResolver resolver;

	@Before
	public void setup() {
		provider = new TestContentProvider();

		resolver = Robolectric.application.getContentResolver();

		provider.onCreate();
		ShadowContentResolver.registerProvider(TestContentProvider.AUTHORITY, provider);
	}


	@Test
	public void testQueryWithRowMapper_withSingleCursorRow() {
		Integer columnValue = 1;

		provider.setQueryCursor(buildSingleColumnCursor("x", columnValue));

		Integer returned = new ContentProviderClientTemplate(resolver).query(
				TestContentProvider.URI,
				null,
				new SingleColumnIntegerRowMapper());

		assertEquals(columnValue, returned);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testQueryWithRowMapper_cursorWithMultipleRows() {
		Integer columnValue = 1;

		provider.setQueryCursor(buildSingleColumnCursor("x",
				columnValue,
				columnValue));

		new ContentProviderClientTemplate(resolver).query(
				TestContentProvider.URI,
				null,
				new SingleColumnIntegerRowMapper());
	}

	@Test
	public void testQueryForListWithRowMapper() {
		Integer firstRowColumnValue = 1;
		Integer secondRowColumnValue = 2;

		provider.setQueryCursor(buildSingleColumnCursor("x",
				firstRowColumnValue,
				secondRowColumnValue));

		List<Integer> returned = new ContentProviderClientTemplate(resolver).queryForList(
				TestContentProvider.URI,
				null,
				new SingleColumnIntegerRowMapper());

		assertEquals(Lists.newArrayList(firstRowColumnValue, secondRowColumnValue),
				returned);
	}

	@Test
	public void testQueryWithRowCallbackHandler() {
		final Integer firstRowColumnValue = 100;
		final Integer secondRowColumnValue = 200;

		provider.setQueryCursor(buildSingleColumnCursor("x",
				firstRowColumnValue,
				secondRowColumnValue));

		final Set<Integer> processCursorValues = new HashSet<Integer>();

		new ContentProviderClientTemplate(resolver).query(TestContentProvider.URI, null,
				new RowCallbackHandler() {
					@Override
					public void processRow(Cursor cursor, int rowNumber) {
						processCursorValues.add(cursor.getInt(0));
					}
				});

		Set<Integer> expectedValues = new HashSet<Integer>(2);
		expectedValues.add(firstRowColumnValue);
		expectedValues.add(secondRowColumnValue);

		assertEquals(expectedValues,
				processCursorValues);
	}

	private static Cursor buildSingleColumnCursor(String columnName, Object... rowValues) {
		MatrixCursor cursor = new MatrixCursor(new String[]{ columnName });

		for(Object rowValue : rowValues) {
			cursor.addRow(new Object[]{ rowValue });
		}

		return cursor;
	}

	private static class TestContentProvider extends ContentProvider {
		private static final String AUTHORITY = "com.company.project.package";

		public static final Uri URI = new Uri.Builder()
				.scheme("content")
				.authority(AUTHORITY)
				.appendPath("path")
				.build();


		private Cursor queryCursor;

		@Override
		public boolean onCreate() {
			queryCursor = null;

			return true;
		}

		@Override
		public String getType(Uri uri) {
			return null;
		}

		@Override
		public Uri insert(Uri uri, ContentValues values) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
			return queryCursor;
		}

		@Override
		public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int delete(Uri uri, String selection, String[] selectionArgs) {
			return 0;
		}

		public void setQueryCursor(Cursor queryCursor) {
			this.queryCursor = queryCursor;
		}
	}
}


