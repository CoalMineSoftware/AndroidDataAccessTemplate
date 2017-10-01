package com.coalminesoftware.android.data.template.sqlite;

/**
 * @see SQLiteDatabaseClientTemplate#performTransaction(TransactionCallback)
 */
public interface TransactionCallback {
	void doInTransaction(SQLiteDatabaseClientTemplate template);
}
