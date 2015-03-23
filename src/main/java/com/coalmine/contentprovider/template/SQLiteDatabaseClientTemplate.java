package com.coalmine.contentprovider.template;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CancellationSignal;

public class SQLiteDatabaseClientTemplate extends BaseClientTemplate {
	private DatabaseRetriever databaseRetriever;

	public SQLiteDatabaseClientTemplate(DatabaseRetriever databaseRetriever) {
		this.databaseRetriever = databaseRetriever;
	}

	public SQLiteDatabaseClientTemplate(SQLiteOpenHelper openHelper) {
		databaseRetriever = new SQLiteOpenHelperDatabaseRetriever(openHelper);
	}

	public <RowModel> List<RowModel> queryForList(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal, RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = databaseRetriever.getReadableDatabase().query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
			return mapRows(cursor, rowMapper);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}


	public interface DatabaseRetriever {
		SQLiteDatabase getReadableDatabase();
		SQLiteDatabase getWritableDatabase();
	}

	private static class SQLiteOpenHelperDatabaseRetriever implements DatabaseRetriever {
		private SQLiteOpenHelper openHelper;

		public SQLiteOpenHelperDatabaseRetriever(SQLiteOpenHelper openHelper) {
			this.openHelper = openHelper;
		}

		@Override
		public SQLiteDatabase getReadableDatabase() {
			return openHelper.getReadableDatabase();
		}

		@Override
		public SQLiteDatabase getWritableDatabase() {
			return openHelper.getWritableDatabase();
		}
	}
}


