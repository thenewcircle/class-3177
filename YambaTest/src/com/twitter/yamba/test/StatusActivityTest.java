package com.twitter.yamba.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.yamba.StatusActivity;

public class StatusActivityTest extends
		ActivityInstrumentationTestCase2<StatusActivity> {
	private EditText editStatus;
	private TextView textCount;
	private Button buttonTweet;

	public StatusActivityTest() {
		super(StatusActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		editStatus = (EditText) getActivity().findViewById(
				com.twitter.yamba.R.id.status_text);
		textCount = (TextView) getActivity().findViewById(
				com.twitter.yamba.R.id.status_text_count);
		buttonTweet = (Button) getActivity().findViewById(
				com.twitter.yamba.R.id.status_button_tweet);
	}

	public void testExists() {
		Assert.assertNotNull(editStatus);
	}

	@UiThreadTest
	public void testCounter() {
		String someText = "Hello from JUnit!";
		editStatus.setText(someText);

		int expected = 140 - someText.length();
		int actual = Integer.parseInt(textCount.getText().toString());

		Assert.assertEquals(expected, actual);
	}

	public void testTweet() throws Throwable {
		final String someText = "JUnit @"+System.currentTimeMillis();
		
		// Run part of the test on the UI thread
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				editStatus.setText(someText);
			}
		});

		Assert.assertEquals(someText, editStatus.getText().toString());

		TouchUtils.clickView(this, buttonTweet);
	}

}
