package com.coalmine.contentprovider.template;

public interface TransactionCallback {
	void doInTransaction(SQLiteDatabaseClientTemplate template);
}
