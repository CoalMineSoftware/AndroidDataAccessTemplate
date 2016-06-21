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

	/**
	 * Executes the given callback's {@link TransactionCallback#doInTransaction(SQLiteDatabaseClientTemplate)} method
	 * within a transaction with a writable database.
	 */
	public void performTransaction(TransactionCallback callback) {
		SQLiteDatabase database = getWritableDatabase();

		database.beginTransaction();
		try {
			callback.doInTransaction(this);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public <RowModel> RowModel rawQuery(String query, List<String> selectionArgs, RowMapper<RowModel> rowMapper) {
		return rawQuery(query, toArray(selectionArgs), rowMapper);
	}

	public <RowModel> RowModel rawQuery(String query, String[] selectionArgs, RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().rawQuery(query, selectionArgs);

			return mapRow(cursor, rowMapper);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public <RowModel> List<RowModel> rawQueryForList(String query, List<String> selectionArgs, RowMapper<RowModel> rowMapper) {
		return rawQueryForList(query, toArray(selectionArgs), rowMapper);
	}

	public <RowModel> List<RowModel> rawQueryForList(String query, String[] selectionArgs, RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().rawQuery(query, selectionArgs);

			return mapRows(cursor, rowMapper);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public void rawQuery(String query, List<String> selectionArgs, RowCallbackHandler callbackHandler) {
		rawQuery(query, toArray(selectionArgs), callbackHandler);
	}

	public  void rawQuery(String query, String[] selectionArgs, RowCallbackHandler callbackHandler) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().rawQuery(query, selectionArgs);

			processRows(cursor, callbackHandler);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public <RowModel> List<RowModel> queryForList(boolean distinct, String table, List<String> columns,
			String selection, List<String> selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			RowMapper<RowModel> rowMapper) {
		return queryForList(distinct, table, toArray(columns),
				selection, toArray(selectionArgs),
				groupBy, having, orderBy, limit,
				rowMapper);
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

	public <RowModel> List<RowModel> queryForList(boolean distinct, String table, List<String> columns,
			String selection, List<String> selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowMapper<RowModel> rowMapper) {
		return queryForList(distinct, table, toArray(columns),
				selection, toArray(selectionArgs),
				groupBy, having, orderBy, limit,
				cancellationSignal,
				rowMapper);
	}

	public <RowModel> List<RowModel> queryForList(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(distinct, table, columns,
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

	public <RowModel> RowModel query(boolean distinct, String table, List<String> columns,
			String selection, List<String> selectionArgs,
			String groupBy,String having, String orderBy, String limit,
			RowMapper<RowModel> rowMapper) {
		return query(distinct, table, toArray(columns),
				selection, toArray(selectionArgs),
				groupBy, having, orderBy, limit,
				rowMapper);
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

	public <RowModel> RowModel query(boolean distinct, String table, List<String> columns,
			String selection, List<String> selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowMapper<RowModel> rowMapper) {
		return query(distinct, table, toArray(columns),
				selection, toArray(selectionArgs),
				groupBy, having, orderBy, limit,
				cancellationSignal,
				rowMapper);
	}

	public <RowModel> RowModel query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowMapper<RowModel> rowMapper) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(distinct, table, columns,
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

	public void query(boolean distinct, String table, List<String> columns,
			String selection, List<String> selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			RowCallbackHandler callbackHandler) {
		query(distinct, table, toArray(columns),
				selection, toArray(selectionArgs),
				groupBy, having, orderBy, limit,
				callbackHandler);
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

	public void query(boolean distinct, String table, List<String> columns,
			String selection, List<String> selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowCallbackHandler callbackHandler) {
		query(distinct, table, toArray(columns),
				selection, toArray(selectionArgs),
				groupBy, having, orderBy, limit,
				cancellationSignal,
				callbackHandler);
	}

	public void query(boolean distinct, String table, String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit,
			CancellationSignal cancellationSignal,
			RowCallbackHandler callbackHandler) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(distinct, table, columns,
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
		return getWritableDatabase().insertOrThrow(
				table,
				null,
				mapper.mapContentValues(rowObject));
	}

	public <RowModel> long insert(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			ConflictAlgorithm conflictAlgorithm) {
		return getWritableDatabase().insertWithOnConflict(
				table,
				null,
				mapper.mapContentValues(rowObject),
				conflictAlgorithm.getConstant());
	}

	public <RowModel> int update(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			String whereClause, List<String> selectionArguments) {
		return update(table, rowObject, mapper,
				whereClause, toArray(selectionArguments));
	}

	public <RowModel> int update(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			String whereClause, String[] selectionArguments) {
		return getWritableDatabase().update(
				table,
				mapper.mapContentValues(rowObject),
				whereClause,
				selectionArguments);
	}

	public <RowModel> int update(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			String whereClause, List<String> selectionArguments, ConflictAlgorithm conflictAlgorithm) {
		return update(table, rowObject, mapper,
				whereClause, toArray(selectionArguments), conflictAlgorithm);
	}

	public <RowModel> int update(String table, RowModel rowObject, ContentValuesMapper<RowModel> mapper,
			String whereClause, String[] selectionArguments, ConflictAlgorithm conflictAlgorithm) {
		return getWritableDatabase().updateWithOnConflict(
				table,
				mapper.mapContentValues(rowObject),
				whereClause,
				selectionArguments,
				conflictAlgorithm.getConstant());
	}

	public int delete(String table, String whereClause, List<String> selectionArguments) {
		return delete(table, whereClause, toArray(selectionArguments));
	}

	public int delete(String table, String whereClause, String[] selectionArguments) {
		return getWritableDatabase().delete(
				table,
				whereClause,
				selectionArguments);
	}

	protected SQLiteDatabase getReadableDatabase() {
		return databaseRetriever.getReadableDatabase();
	}

	protected SQLiteDatabase getWritableDatabase() {
		return databaseRetriever.getWritableDatabase();
	}

	/* Enum wrapper for the "CONFLICT_" integer constants defined by SQLiteDatabase. */
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

	/**
	 * Abstraction for obtaining a SQLiteDatabase. This is intended to be implemented by API users
	 * when using a SQLitDatabase that's not managed by a SQLiteOpenHelper.
	 */
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


