package com.twitter.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
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
		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			Log.d(TAG, "gotType: " + StatusContract.STATUS_TYPE_DIR);
			return StatusContract.STATUS_TYPE_DIR;
		case StatusContract.STATUS_ITEM:
			Log.d(TAG, "gotType: " + StatusContract.STATUS_TYPE_ITEM);
			return StatusContract.STATUS_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
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
		String where;

		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			// so we count updated rows
			where = selection;
			break;
		case StatusContract.STATUS_ITEM:
			long id = ContentUris.parseId(uri);
			where = StatusContract.Column.ID
					+ "="
					+ id
					+ (TextUtils.isEmpty(selection) ? "" : " and ( "
							+ selection + " )");
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.update(StatusContract.TABLE, values, where, selectionArgs);

		Log.d(TAG, "updated records: " + ret);
		return ret;
	}

	// Implement Purge feature
	// Use db.delete()
	// DELETE FROM status WHERE id=? AND user='?'
	// uri: content://com.twitter.yamba.StatusProvider/status/47
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String where;

		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			// so we count deleted rows
			where = (selection == null) ? "1" : selection;
			break;
		case StatusContract.STATUS_ITEM:
			long id = ContentUris.parseId(uri);
			where = StatusContract.Column.ID
					+ "="
					+ id
					+ (TextUtils.isEmpty(selection) ? "" : " and ( "
							+ selection + " )");
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.delete(StatusContract.TABLE, where, selectionArgs);

		Log.d(TAG, "deleted records: " + ret);
		return ret;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "queried");
		return null;
	}

}