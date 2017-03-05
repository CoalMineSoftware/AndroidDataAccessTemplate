package com.coalminesoftware.cursortemplate.sqlite;

public interface TransactionCallback {
	void doInTransaction(SQLiteDatabaseClientTemplate template);
}
