package com.coalminesoftware.android.data.sqlite;

/**
 * @see SQLiteDatabaseClientTemplate#performTransaction(TransactionCallback)
 */
public interface TransactionCallback {
	void doInTransaction(SQLiteDatabaseClientTemplate template);
}
