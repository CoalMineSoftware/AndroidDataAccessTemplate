package com.coalmine.contentprovider.template;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CancellationSignal;

public class SQLiteDatabaseClientTemplate extends BaseClientTemplate {
	private DatabaseRetriever databaseRetriever;

	/**
	 * Constructs a SQLiteDatabaseClientTemplate that operates on the database provided by the given
	 * {@link SQLiteOpenHelper}.  For projects that do not manage their databases with a SQLiteOpenHelper, please see
	 * {@link #SQLiteDatabaseClientTemplate(DatabaseRetriever)}.
	 */
	public SQLiteDatabaseClientTemplate(SQLiteOpenHelper openHelper) {
		this(new SQLiteOpenHelperDatabaseRetriever(openHelper));
	}

	/**
	 * Constructs a SQLiteDatabaseClientTemplate that operates on the database provided by the given
	 * {@link DatabaseRetriever}.  For projects that use {@link SQLiteOpenHelper}, please see
	 * {@link #SQLiteDatabaseClientTemplate(SQLiteOpenHelper)}.
	 */
	public SQLiteDatabaseClientTemplate(DatabaseRetriever databaseRetriever) {
		this.databaseRetriever = databaseRetriever;
	}

	public <RowModel> List<RowModel> queryForList(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			RowMapper<RowModel> rowMapper) {
		return queryForList(distinct, table, columns,
				selection, selectionArgs,
				groupBy, having, orderBy, limit,
				null, rowMapper);
	}

	public <RowModel> List<RowModel> queryForList(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = databaseRetriever.getReadableDatabase().query(distinct, table, columns,
					selection, selectionArgs,
					groupBy, having, orderBy, limit,
					cancellationSignal);

			return mapRows(cursor, rowMapper);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public <RowModel> RowModel query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy,String having, String orderBy, String limit,
			RowMapper<RowModel> rowMapper) {
		return query(distinct, table, columns,
				selection, selectionArgs,
				groupBy, having, orderBy, limit,
				null, rowMapper);
	}

	public <RowModel> RowModel query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = databaseRetriever.getReadableDatabase().query(distinct, table, columns,
					selection, selectionArgs,
					groupBy, having, orderBy, limit,
					cancellationSignal);

			return mapRow(cursor, rowMapper);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public void query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			RowCallbackHandler callbackHandler) {
		query(distinct, table, columns,
				selection, selectionArgs,
				groupBy, having, orderBy, limit,
				null, callbackHandler);
	}

	public void query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowCallbackHandler callbackHandler) {
		Cursor cursor = null;
		try {
			cursor = databaseRetriever.getReadableDatabase().query(distinct, table, columns,
					selection, selectionArgs,
					groupBy, having, orderBy, limit,
					cancellationSignal);

			processRows(cursor, callbackHandler);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public <RowModel> long insert(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper) {
		return databaseRetriever.getWritableDatabase().insertOrThrow(
				table,
				null,
				mapper.mapContentValues(rowObject));
	}

	public <RowModel> long insert(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			ConflictAlgorithm conflictAlgorithm) {
		return databaseRetriever.getWritableDatabase().insertWithOnConflict(
				table,
				null,
				mapper.mapContentValues(rowObject),
				conflictAlgorithm.getConstant());
	}

	public <RowModel> int update(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			String whereClause, String[] selectionArguments) {
		return databaseRetriever.getWritableDatabase().update(
				table,
				mapper.mapContentValues(rowObject),
				whereClause,
				selectionArguments);
	}

	public <RowModel> int update(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			String whereClause, String[] selectionArguments, ConflictAlgorithm conflictAlgorithm) {
		return databaseRetriever.getWritableDatabase().updateWithOnConflict(
				table,
				mapper.mapContentValues(rowObject),
				whereClause,
				selectionArguments,
				conflictAlgorithm.getConstant());
	}

	public int delete(String table, String whereClause, String[] selectionArguments) {
		return databaseRetriever.getWritableDatabase().delete(
				table,
				whereClause,
				selectionArguments);
	}

	public enum ConflictAlgorithm {
		ABORT(SQLiteDatabase.CONFLICT_ABORT),
		FAIL(SQLiteDatabase.CONFLICT_FAIL),
		IGNORE(SQLiteDatabase.CONFLICT_IGNORE),
		NONE(SQLiteDatabase.CONFLICT_NONE),
		REPLACE(SQLiteDatabase.CONFLICT_REPLACE),
		ROLLBACK(SQLiteDatabase.CONFLICT_ROLLBACK);

		private final int constant;

		private ConflictAlgorithm(int constant) {
			this.constant = constant;
		}

		public int getConstant() {
			return constant;
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


