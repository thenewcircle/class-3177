package com.twitter.yambacommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class YambaManager {
	private Context context;
	private static IYambaService service;

	public YambaManager(Context context) {
		this.context = context;

		context.bindService(
				new Intent("com.twitter.yambacommon.IYambaService"),
				CONNECTION, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.unbindService(CONNECTION);
	}

	private static final ServiceConnection CONNECTION = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			service = IYambaService.Stub.asInterface(binder);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			service = null;
		}
	};

	// --- Proxy Calls ---

	public void post(String message) {
		if (service == null)
			return;
		try {
			service.post(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
