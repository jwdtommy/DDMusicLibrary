package com.dd.music.utils;

import java.util.ArrayList;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class CommonUtils {

	public static boolean isServiceWorked(Context context, Class<?> mClass) {
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals(mClass.getName())) {
				return true;
			}
		}
		return false;
	}
}
