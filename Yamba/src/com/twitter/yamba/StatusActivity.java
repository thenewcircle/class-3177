package com.twitter.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class StatusActivity extends Activity {
	private Button mButtonTweet;
	private EditText mTextStatus;
	private TextView mTextCount;
	private int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        mButtonTweet = (Button) findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) findViewById(R.id.status_text);
        mTextCount = (TextView) findViewById(R.id.status_text_count);
        mTextCount.setText( Integer.toString(140) );
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();
        
        mButtonTweet.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				String status = mTextStatus.getText().toString();
				
				Log.d("StatusActivity", "I got clicked with status: "+status);
			}
        	
        });
        
        mTextStatus.addTextChangedListener( new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				int count = 140 - s.length();
				mTextCount.setText( Integer.toString(count) );
				
				if(count<50) {
					mTextCount.setTextColor(Color.RED);
				} else {
					mTextCount.setTextColor(mDefaultColor);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
        	
        });
    }
    

}
