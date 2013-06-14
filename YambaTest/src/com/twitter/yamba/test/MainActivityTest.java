package com.twitter.yamba.test;

import junit.framework.Assert;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.twitter.yamba.MainActivity;
import com.twitter.yamba.StatusContract;
import com.twitter.yamba.TimelineFragment;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private TimelineFragment timelineFragment;
	private ListView listView;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		timelineFragment = (TimelineFragment) getActivity()
				.getFragmentManager().findFragmentById(
						com.twitter.yamba.R.id.fragment_timeline);
		listView = timelineFragment.getListView();

		// Insert dummy record
		ContentValues values = new ContentValues();
		values.put(StatusContract.Column.ID, 123123L);
		values.put(StatusContract.Column.USER, "Bob");
		values.put(StatusContract.Column.MESSAGE, "Test");
		values.put(StatusContract.Column.CREATED_AT, System.currentTimeMillis());
		getActivity().getContentResolver().insert(StatusContract.CONTENT_URI,
				values);
	}

	@Override
	protected void tearDown() throws Exception {		
		// Remove dummy data
		Uri uri = ContentUris.withAppendedId(StatusContract.CONTENT_URI,
				123123L);
		getActivity().getContentResolver().delete(uri, null, null);
		
		super.tearDown();
	}

	public void testExists() {
		Assert.assertNotNull(timelineFragment);
		Assert.assertNotNull(listView);
	}

	public void testHasData() {
		getInstrumentation().waitForIdleSync();
		int count = listView.getCount();
		Assert.assertTrue(count > 0);
	}

}
