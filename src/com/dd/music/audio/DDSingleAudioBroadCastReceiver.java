package com.dd.music.audio;

import com.dd.music.entry.Music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* 音频广播
 * @author J.Tommy 2015.1.4
 */
public abstract class DDSingleAudioBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		int position = intent.getIntExtra(AudioService.INTENT_DATA_POSITION, 0);
		Music currentMusic = (Music) intent.getExtras().get(
				AudioService.INTENT_DATA_CURRENT_MUSIC);
		switch (intent.getIntExtra(AudioService.INTENT_COMMEND_KEY, 0)) {
		case AudioService.INTENT_COMMEND_RESUME:
			onResume(position, currentMusic);
			break;
		case AudioService.INTENT_COMMEND_PLAY:
			onPlay(position, currentMusic);
			break;
		case AudioService.INTENT_COMMEND_STOP:
			onStop(position, currentMusic);
			break;
		case AudioService.INTENT_COMMEND_COMPLETE:
			onComplete(position, currentMusic);
			break;
		case AudioService.INTENT_COMMEND_NEXT:
			boolean isManual = intent.getBooleanExtra(
					AudioService.INTENT_DATA_IS_MANUAL, false);
			onNext(position, currentMusic, isManual);
			break;
		default:
			break;
		}
	}

	public abstract void onPlay(int position, Music currentMusic);

	public abstract void onResume(int position, Music currentMusic);

	public abstract void onStop(int position, Music currentMusic);

	public abstract void onComplete(int position, Music currentMusic);

	public abstract void onNext(int position, Music currentMusic,
			boolean isManual);
}
