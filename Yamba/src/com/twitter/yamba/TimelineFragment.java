package com.twitter.yamba;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String[] FROM = { StatusContract.Column.USER,
			StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT };
	private static final int[] TO = { R.id.list_item_text_user,
			R.id.list_item_text_message, R.id.list_item_text_created_at };
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter mAdapter;

	private static final ViewBinder VIEW_BINDER = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() != R.id.list_item_text_created_at)
				return false;
						
			// Custom binding
			long timestamp = cursor.getLong(columnIndex);
			CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView)view).setText(relTime);
			
			return true;
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
				null, FROM, TO, 0);
		mAdapter.setViewBinder(VIEW_BINDER);

		setListAdapter(mAdapter);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	// --- Loader Callbacks ---

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id != LOADER_ID)
			return null;

		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
				null, null, null, StatusContract.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
