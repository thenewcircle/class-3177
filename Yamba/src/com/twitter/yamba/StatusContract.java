package com.twitter.yamba;

import android.provider.BaseColumns;

public class StatusContract {

	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "status";
	
	class Column {
		public static final String ID = BaseColumns._ID;
		public static final String USER = "user";
		public static final String MESSAGE = "message";
		public static final String CREATED_AT = "created_at";
	}
}
