package com.twitter.yamba;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class TimelineFragment extends ListFragment {
	private static final String[] FROM = { StatusContract.Column.USER, StatusContract.Column.MESSAGE };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Cursor cursor = getActivity().getContentResolver().query(
				StatusContract.CONTENT_URI, null, null, null,
				StatusContract.DEFAULT_SORT);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, cursor, FROM, TO, 0);
		
		setListAdapter(adapter);
	}
}
