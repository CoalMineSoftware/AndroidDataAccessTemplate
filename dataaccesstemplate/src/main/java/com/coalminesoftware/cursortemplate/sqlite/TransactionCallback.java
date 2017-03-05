package com.coalminesoftware.cursortemplate.sqlite;

/**
 * @see SQLiteDatabaseClientTemplate#performTransaction(TransactionCallback)
 */
public interface TransactionCallback {
	void doInTransaction(SQLiteDatabaseClientTemplate template);
}
