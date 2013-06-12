package com.twitter.yamba;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {
	private static final String TAG = StatusProvider.class.getSimpleName();
	private DbHelper dbHelper;

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE,
				StatusContract.STATUS_DIR);
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE
				+ "/#", StatusContract.STATUS_ITEM);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		Log.d(TAG, "onCreated");
		return false;
	}

	@Override
	public String getType(Uri uri) {
		Log.d(TAG, "getType");
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri ret = null;

		// Assert correct uri
		if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR) {
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insertWithOnConflict(StatusContract.TABLE, null,
				values, SQLiteDatabase.CONFLICT_REPLACE);

		// Was insert successful?
		if (rowId != -1) {
			String idString = values.getAsString(StatusContract.Column.ID);
			ret = Uri.withAppendedPath(uri, idString);
		}

		Log.d(TAG, "inserted uri: " + ret);
		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.d(TAG, "updated");
		return 0;
	}

	// Implement Purge feature
	// Use db.delete()
	// DELETE FROM status WHERE id=? AND user='?'
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Assert correct uri
		if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR) {
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.delete(StatusContract.TABLE, selection, selectionArgs);
		
		Log.d(TAG, "deleted records: "+ret);
		return ret;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "queried");
		return null;
	}

}
