package com.twitter.yambaservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;
import com.twitter.yambacommon.IYambaService;

public class IYambaServiceImpl extends IYambaService.Stub {
	private Context context;
	
	public IYambaServiceImpl(Context context) {
		this.context = context;
	}

	@Override
	public void post(String status) throws RemoteException {		
		YambaClient cloud = new YambaClient("student", "password");
		try {
			cloud.postStatus(status);
		} catch (YambaClientException e) {
			e.printStackTrace();
		}
	}

}
