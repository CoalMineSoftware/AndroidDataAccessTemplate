package com.coalmine.contentprovider.template;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

/** Providers a simpler API for developers to interact with {@link ContentProvider}s, eliminating
 * much of the boilerplate code.  The design also encouraging developers to write self-contained,
 * reusable {@link RowMapper}s and {@link RowCallbackHandler}s for building business objects from
 * Cursor rows, and {@link ContentValuesMapper}s for generating {@link ContentValues} from business
 * objects. */
public class ContentProviderClientTemplate {
	private ContentProviderQuerier providerQuerier;


	/** Constructs a template using a {@link ContentResolver}, to which <code>insert()</code>,
	 * <code>query()</code> and <code>update()</code> calls are delegated.  Templates constructed
	 * in this fashion will incur the same performance penalty that developers can expect from
	 * calling those methods themselves on a <code>ContentResolver</code>, compared to acquiring a
	 * {@link ContentProviderClient} from a resolver and calling the client's corresponding methods
	 * instead.  However, templates constructed with a resolver have the advantage of not requiring
	 * any cleanup and not being limited to interacting with a single provider. */
	public ContentProviderClientTemplate(ContentResolver contentResolver) {
		providerQuerier = new ContentResolverQuerier(contentResolver);
	}

	/** Constructs a template using a {@link ContentProviderClient}, to which <code>insert()</code>,
	 * <code>query()</code> and <code>update()</code> calls are delegated.  Because the use of a
	 * client avoids repeated {@link ContentProvider} lookups  and permission checks, developers
	 * can expect to see better performance when constructing a template in this fashion.  However,
	 * a client (and a template constructed with a client) can only insert/query/update URI's
	 * handled by the provider for which the client was acquired. Clients also need to be released
	 * when no longer needed, by calling {@link ContentProviderClient#release()}.  For convenience,
	 * users can also call {@link #releaseClient()} on a ContentProviderClientTemplate constructed
	 * with a ContentProviderClient. */
	public ContentProviderClientTemplate(ContentProviderClient providerClient) {
		providerQuerier = new ContentProviderClientQuerier(providerClient);
	}

	public <RowModel> RowModel query(Uri uri, String[] projection, RowMapper<RowModel> rowMapper) {
		return query(uri, projection, null, null, null, rowMapper);
	}

	public <RowModel> RowModel query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder, RowMapper<RowModel> rowMapper) {
		RowModel queryResult = null;

		Cursor cursor = null;
		try {
			cursor = providerQuerier.query(uri, projection, selectClause, selectionArguments, sortOrder);
			if(cursor.moveToNext()) {
				if(!cursor.isLast()) {
					throw new IllegalArgumentException("Multiple rows returned for the given URI/query.");
				}

				queryResult = rowMapper.mapRow(cursor, 0);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return queryResult;
	}

	public <RowModel> List<RowModel> queryForList(Uri uri, String[] projection, RowMapper<RowModel> rowMapper) {
		return queryForList(uri, projection, null, null, null, rowMapper);
	}

	public <RowModel> List<RowModel> queryForList(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder, RowMapper<RowModel> rowMapper) {
		List<RowModel> queryResults = new ArrayList<RowModel>();

		Cursor cursor = null;
		try {
			cursor = providerQuerier.query(uri, projection, selectClause, selectionArguments, sortOrder);
			int rowNumber = 0;
			while(cursor.moveToNext()) {
				queryResults.add(rowMapper.mapRow(cursor, rowNumber++));
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return queryResults;
	}

	public void query(Uri uri, String[] projection, RowCallbackHandler callbackHandler) {
		query(uri, projection, null, null, null, callbackHandler);
	}

	public void query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder, RowCallbackHandler callbackHandler) {
		Cursor cursor = null;
		try {
			int rowNumber = 0;
			cursor = providerQuerier.query(uri, projection, selectClause, selectionArguments, sortOrder);
			while(cursor.moveToNext()) {
				callbackHandler.processRow(cursor, rowNumber++);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * Inserts a record into the given URI, using the given mapper to generate the ContentValues.
	 * 
	 * @return The URI of the inserted record, as reported by the ContentProvider being inserted into.
	 */
	public <RowModel> Uri insert(Uri uri, RowModel rowObject, ContentValuesMapper<RowModel> mapper) {
		return providerQuerier.insert(uri, mapper.mapContentValues(rowObject));
	}

	/**
	 * Updates the given URI, using the given mapper to generate the ContentValues.
	 * 
	 * @return The number of rows affected by the update.
	 */
	public <RowModel> int update(Uri uri, RowModel rowObject, ContentValuesMapper<RowModel> mapper, String whereClause, String[] selectionArguments) {
		return providerQuerier.update(uri, mapper.mapContentValues(rowObject), whereClause, selectionArguments);
	}

	/**
	 * Updates the given URI, using the given mapper to generate the ContentValues.
	 * 
	 * @return The number of rows affected by the update.
	 */
	public <RowModel> int update(Uri uri, RowModel rowObject, ContentValuesMapper<RowModel> mapper) {
		return update(uri, rowObject, mapper, null, null);
	}

	/** For templates that were constructed with a provider client, this convenience method
	 * releases the client once it is no longer needed, by calling
	 * {@link ContentProviderClient#release()}.
	 * 
	 * @return True if this was released or false if it was already released.
	 *  @throws IllegalStateException Thrown if the template does not wrap a ContentProviderClient.
	 *  @see ContentProviderClientTemplate#ContentProviderClientTemplate(ContentProviderClient) */
	public boolean releaseClient() {
		if(providerQuerier instanceof ContentProviderClientQuerier) {
			return ((ContentProviderClientQuerier)providerQuerier).providerClient.release();
		} else {
			throw new IllegalStateException("Template was not constructed with ContentProviderClient to release.");
		}
	}


	private interface ContentProviderQuerier {
		Uri insert(Uri uri, ContentValues contentValues);
		Cursor query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder);
		int update(Uri uri, ContentValues contentValues, String whereClause, String[] selectionArguments);
	}

	private static class ContentResolverQuerier implements ContentProviderQuerier {
		ContentResolver contentResolver;

		public ContentResolverQuerier(ContentResolver contentResolver) {
			if(contentResolver == null) {
				throw new IllegalArgumentException("A resolver is required.");
			}

			this.contentResolver = contentResolver;
		}

		public Uri insert(Uri uri, ContentValues contentValues) {
			return contentResolver.insert(uri, contentValues);
		}

		public Cursor query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder) {
			return contentResolver.query(uri, projection, selectClause, selectionArguments, sortOrder);
		}

		public int update(Uri uri, ContentValues contentValues, String whereClause, String[] selectionArguments) {
			return contentResolver.update(uri, contentValues, whereClause, selectionArguments);
		}
	}

	private static class ContentProviderClientQuerier implements ContentProviderQuerier {
		ContentProviderClient providerClient;

		public ContentProviderClientQuerier(ContentProviderClient providerClient) {
			if(providerClient == null) {
				throw new IllegalArgumentException("A client is required.");
			}

			this.providerClient = providerClient;
		}

		public Uri insert(Uri uri, ContentValues contentValues) {
			try {
				return providerClient.insert(uri, contentValues);
			} catch (RemoteException e) {
				throw new RuntimeException("ContentProviderClient could not insert values.", e);
			}
		}

		public Cursor query(Uri uri, String[] projection, String selectClause, String[] selectionArguments, String sortOrder) {
			try {
				return providerClient.query(uri, projection, selectClause, selectionArguments, sortOrder);
			} catch (RemoteException e) {
				throw new RuntimeException("ContentProviderClient could not be queried.", e);
			}
		}

		public int update(Uri uri, ContentValues contentValues, String whereClause, String[] selectionArguments) {
			try {
				return providerClient.update(uri, contentValues, whereClause, selectionArguments);
			} catch (RemoteException e) {
				throw new RuntimeException("ContentProviderClient could not update values.", e);
			}
		}
	}
}


