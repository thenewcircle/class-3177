package com.twitter.yambaservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class YambaService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new IYambaServiceImpl( this );
	}

}
