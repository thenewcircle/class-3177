package com.twitter.yamba;

import org.apache.http.client.utils.URIUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {
	private static final String TAG = StatusProvider.class.getSimpleName();
	private DbHelper dbHelper;
	
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
		Uri ret=null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insertWithOnConflict(StatusContract.TABLE, null, values,
				SQLiteDatabase.CONFLICT_REPLACE);
		
		// Was insert successful?
		if(rowId!=-1) {
			String idString = values.getAsString( StatusContract.Column.ID );
			ret = Uri.withAppendedPath(uri, idString);
		}
		
		Log.d(TAG, "inserted uri: "+ret);
		return ret;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.d(TAG, "updated");
		return 0;
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d(TAG, "deleted");
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "queried");
		return null;
	}


}
