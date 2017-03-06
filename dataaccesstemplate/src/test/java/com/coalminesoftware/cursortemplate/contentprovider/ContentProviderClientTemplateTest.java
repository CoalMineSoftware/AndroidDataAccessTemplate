package com.coalminesoftware.cursortemplate.contentprovider;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.coalminesoftware.cursortemplate.RowCallbackHandler;
import com.coalminesoftware.cursortemplate.simple.SingleColumnIntegerRowMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentProviderClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ContentProviderClientTemplateTest {
	private TestContentProvider provider;
	private ContentResolver resolver;

	@Before
	public void setup() {
		ProviderInfo info = new ProviderInfo();
		info.authority = TestContentProvider.AUTHORITY;
		provider = Robolectric.buildContentProvider(TestContentProvider.class).create(info).get();

		resolver = RuntimeEnvironment.application.getContentResolver();

		provider.onCreate();
	}


	@Test
	public void testQueryWithRowMapper_withSingleCursorRow() {
		Integer columnValue = 1;

		provider.setQueryCursor(buildSingleColumnCursor("x", columnValue));

		Integer returned = new ContentProviderClientTemplate(resolver).query(
				TestContentProvider.URI,
				null,
				new SingleColumnIntegerRowMapper());

		assertThat(returned, is(columnValue));
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

		assertThat(returned, is(
				asList(firstRowColumnValue, secondRowColumnValue)));
	}

	@Test
	public void testQueryWithRowCallbackHandler() {
		final Integer firstRowColumnValue = 100;
		final Integer secondRowColumnValue = 200;

		provider.setQueryCursor(buildSingleColumnCursor("x",
				firstRowColumnValue,
				secondRowColumnValue));

		final Set<Integer> processCursorValues = new HashSet<>();

		new ContentProviderClientTemplate(resolver).query(TestContentProvider.URI, null,
				new RowCallbackHandler() {
					@Override
					public void processRow(Cursor cursor, int rowNumber) {
						processCursorValues.add(cursor.getInt(0));
					}
				});

		Set<Integer> expectedValues = new HashSet<>(2);
		expectedValues.add(firstRowColumnValue);
		expectedValues.add(secondRowColumnValue);

		assertThat(processCursorValues, is(expectedValues));
	}

	@Test
	public void testReleaseClient_clientIsReleased() {
		ContentProviderClient client = resolver.acquireContentProviderClient(TestContentProvider.URI);
		ShadowContentProviderClient clientShadow = Shadows.shadowOf(client);

		new ContentProviderClientTemplate(client).closeClient();

		assertThat(clientShadow.isReleased(), is(true));
	}

	@Test(expected=IllegalStateException.class)
	public void testReleaseClient_exceptionThrownWhenConstructedWithResolver() {
		new ContentProviderClientTemplate(resolver).closeClient();
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

		private Cursor mQueryCursor;

		@Override
		public boolean onCreate() {
			mQueryCursor = null;

			return true;
		}

		@Override
		public String getType(@NonNull Uri uri) {
			return null;
		}

		@Override
		public Uri insert(@NonNull Uri uri, ContentValues values) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
			return mQueryCursor;
		}

		@Override
		public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
			return 0;
		}

		public void setQueryCursor(Cursor queryCursor) {
			mQueryCursor = queryCursor;
		}
	}
}
