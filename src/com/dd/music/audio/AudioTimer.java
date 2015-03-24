package com.dd.music.audio;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Message;

/*
 * @author J.Tommy 2015.1.3
 * 音频的定时任务，如更新进度条
 */
public class AudioTimer {

	public final static int REFRESH_PROGRESS_EVENT = 0x100;

	private static final int INTERVAL_TIME = 1000;// 刷新频率
	private ArrayList<Handler> mHandler;
	private Timer mTimer;
	private TimerTask mTimerTask;

	private int what;
	private boolean mTimerStart = false;

	private static AudioTimer audioTimer;

	public static AudioTimer getInstance() {
		if (audioTimer == null) {
			audioTimer = new AudioTimer();
		}
		return audioTimer;
	}

	public AudioTimer(Handler handler) {
		this.what = REFRESH_PROGRESS_EVENT;

		mTimer = new Timer();
	}

	public AudioTimer() {
		this.what = REFRESH_PROGRESS_EVENT;
		mTimer = new Timer();
		mHandler = new ArrayList<Handler>();
	}

	public void putHandler(Handler handler) {
		mHandler.add(handler);
	}

	public void startTimer() {
		// return;
		if (mHandler == null || mTimerStart) {
			return;
		}
		mTimerTask = new MyTimerTask();
		mTimer.schedule(mTimerTask, INTERVAL_TIME, INTERVAL_TIME);
		mTimerStart = true;
	}

	public void stopTimer() {
		if (!mTimerStart) {
			return;
		}
		mTimerStart = false;
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			if (mHandler != null) {
				for (Handler handler : mHandler) {
					Message msg = handler.obtainMessage(what);
					msg.sendToTarget();
				}
			}
		}
	}
}
