package com.dd.music.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.dd.music.Constants.Constants;
import com.dd.music.audio.aidl.IAudioService;
import com.dd.music.entry.Music;

/**
 * 音频服务类 2014.9.26
 * 
 * @author J.Tommy
 */
public class AudioService extends Service {
	private boolean isprepared = false;
	// private boolean isPreparing = false;
	/* 定于一个多媒体对象 */
	private MediaPlayer mMediaPlayer = null;
	// 是否循环播放列表
	private boolean isLoop = false;
	// 是否继续播放
	private boolean isGoAhead = true;
	private String currentMusicId = "";

	private int currentMusicPos;
	private Music currentMusic;
	private List<Music> musicList;
	/**
	 * 发送INTENT_COMMEND_PLAY命令时需传递INTENT_DATA_MUSICLIST，INTENT_DATA_POSITION,
	 * INTENT_DATA_NEWSITEM
	 */
	public static final String INTENT_COMMEND_KEY = "commend";
	/******** 开始播放 ********/
	public static final int INTENT_COMMEND_PLAY = 1000;
	public static final String INTENT_DATA_MUSICLIST = "music_list";
	public static final String INTENT_DATA_POSITION = "position";
	public static final String INTENT_DATA_CURRENT_MUSIC = "current_music";
	public static final String INTENT_DATA_ID = "id";

	public static final String INTENT_DATA_IS_MANUAL = "is_manual";

	/******** 开始播放 ********/

	public static final int INTENT_COMMEND_STOP = 1001;// 暂停
	public static final int INTENT_COMMEND_RESUME = 1002;// 恢复
	public static final int INTENT_COMMEND_NEXT = 1003;// 下一首
	public static final int INTENT_COMMEND_PREVIOUS = 1004;// 上一首
	public static final int INTENT_COMMEND_SEEKING = 1005;// 进度更改
	public static final int INTENT_COMMEND_COMPLETE = 1006;// 播放完毕
	public static final int INTENT_COMMEND_SET_GO_AHEAD = 1007;// 设置为连续播放
	public static final int INTENT_COMMEND_SET_NOT_GO_AHEAD = 1008;// 设置为非连续播放
	public static final int INTENT_COMMEND_SHATDOWN = 1009;// 关闭服务
	/**
	 * 发送INTENT_COMMEND_SEEKING命令时需传递INTENT_DATA_SEEKING_POSITION
	 */
	public static final String INTENT_DATA_SEEKING_POSITION = "seeking_position";
	public static final String INTENT_DATA_SEEKING_MAX = "seeking_max";

	public static final long INTERVAL_TIME = 1000;// 操作触发间隔

	private final IBinder mBinder = new ServerStub();
	private AudioService service;
	private AudioManager manager;
	private FocusChangeListener focusChangeListener;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		manager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
		service = this;
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();
		/* 监听播放是否完成 */
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnCompletionListener(new CompleteListener());
		mMediaPlayer.setOnBufferingUpdateListener(new BufferListener());
		mMediaPlayer.setOnPreparedListener(new PrepareListener(0));
		mMediaPlayer.setOnErrorListener(new ErrorListener());

		focusChangeListener = new FocusChangeListener();
		setFoucs();
		// thread = new Thread(this);
		// thread.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	private final class PrepareListener implements OnPreparedListener {
		private int position;

		public PrepareListener(int position) {
			this.position = position;
		}

		public void onPrepared(MediaPlayer mp) {
			if (position > 0) {
				mMediaPlayer.seekTo(position);
			}
			isprepared = true;
			mMediaPlayer.setOnCompletionListener(new CompleteListener());
			mMediaPlayer.start();

			broadcastPlay();
			AudioTimer.getInstance().startTimer();
		}
	}

	private final class ErrorListener implements OnErrorListener {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			isprepared = false;
			if (what != -38) {
				pause(true);
				Toast.makeText(AudioService.this, "请稍后重试", Toast.LENGTH_SHORT)
						.show();
			}
			return false;
		}
	}

	private final class BufferListener implements OnBufferingUpdateListener {
		@Override
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
		}
	}

	private final class CompleteListener implements OnCompletionListener {
		@Override
		public void onCompletion(MediaPlayer mp) {
			if (musicList != null) {
				if (isGoAhead) {
					next(false);
				} else {
					isprepared = false;
					broadcastComplete(currentMusicPos);
					AudioTimer.getInstance().stopTimer();
				}
			}
		}
	}

	private void setNextMusicPosition() {
		int tempPosition = currentMusicPos + 1;
		if (tempPosition <= musicList.size() - 1) {
			currentMusicPos++;
		} else {
			currentMusicPos = 0;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		currentMusicId = "";
		// currentMusicPos = 0;
		// sleepTime = SLEEP_TIME_HIGH;
		currentMusic = null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public void resume() {

		if (currentMusic == null) {
			return;
		}

		if (mMediaPlayer != null) {
			isprepared = true;
			setFoucs();
			mMediaPlayer.start();
		}

		broadcastResume();
		AudioTimer.getInstance().startTimer();
	}

	private void pause(boolean abandonFoucs) {
		if (mMediaPlayer.isPlaying()) {// 正在播放
			mMediaPlayer.pause();// 暂停
		}
		broadcastStop();
		AudioTimer.getInstance().stopTimer();
		if (abandonFoucs) {
			abandonFoucs();
		}
	}

	private void play(List<Music> musicList, int pos) {

		Log.i("jwd", "play() musiclist=" + musicList.toString());
		if (musicList == null || musicList.size() <= 0) {
			return;
		}
		String url = musicList.get(pos).getUrl();
		String id = musicList.get(pos).getMusicId();
		if (TextUtils.isEmpty(url)) {
			return;
		}
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnBufferingUpdateListener(new BufferListener());
		}

		if (id.equals(currentMusicId)) {
			currentMusic = musicList.get(pos);
			currentMusicId = id;
			currentMusicPos = pos;
			resume();
			return;
		}

		currentMusic = musicList.get(pos);
		currentMusicId = id;
		currentMusicPos = pos;
		mMediaPlayer.reset();
		setFoucs();
		try {
			Log.i("jwd", "url=" + url);
			mMediaPlayer.setDataSource(url);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// setFoucs();
		mMediaPlayer.setOnCompletionListener(null);
		try {
			mMediaPlayer.prepareAsync();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 是否允许循环
	 * 
	 * @param isManual
	 *            true=手动播下一首（强制末尾循环），false=播完自动播下一首（不强制末尾循环）
	 */
	private synchronized void next(boolean isManual) {
		isprepared = false;
		// TODO Auto-generated method stub
		if (musicList == null) {
			return;
		}

		int tempPosition = currentMusicPos + 1;
		if (tempPosition <= musicList.size() - 1) {
			currentMusicPos++;
			play(musicList, currentMusicPos);
			if (!isManual) {
			}
			broadcastNext(isManual);
		} else {
			if (isManual) {
				currentMusicPos = 0;
				play(musicList, currentMusicPos);
				broadcastNext(isManual);
			} else {
				if (isLoop) {
					currentMusicPos = 0;
					play(musicList, currentMusicPos);
					broadcastNext(isManual);
				} else {
					currentMusicPos = 0;
					broadcastComplete(0);
					AudioTimer.getInstance().stopTimer();
				}
			}
		}
	}

	private synchronized void previous() {
		// TODO Auto-generated method stub
		if (musicList == null) {
			return;
		}
		int tempPosition = currentMusicPos - 1;
		if (tempPosition >= 0) {
			currentMusicPos--;
			play(musicList, currentMusicPos);
		} else {
			currentMusicPos = musicList.size() - 1;
			play(musicList, currentMusicPos);
		}
	}

	private void seekTo(int pos) {
		if (mMediaPlayer != null) {
			mMediaPlayer.seekTo(pos);
		}
	}

	private void broadcastPlay() {
		Intent intent = new Intent();
		intent.setAction(Constants.RECIEVER_ACTION_MUSIC_UPDATE);
		intent.putExtra(INTENT_COMMEND_KEY, INTENT_COMMEND_PLAY);
		intent.putExtra(INTENT_DATA_CURRENT_MUSIC, currentMusic);
		intent.putExtra(INTENT_DATA_POSITION, currentMusicPos);
		this.sendBroadcast(intent);
		intent = null;
	}

	private void broadcastResume() {
		Intent intent = new Intent();
		intent.setAction(Constants.RECIEVER_ACTION_MUSIC_UPDATE);
		intent.putExtra(INTENT_COMMEND_KEY, INTENT_COMMEND_RESUME);
		intent.putExtra(INTENT_DATA_POSITION, currentMusicPos);
		intent.putExtra(INTENT_DATA_CURRENT_MUSIC, currentMusic);
		this.sendBroadcast(intent);
		intent = null;
	}

	private void broadcastNext(boolean isManual) {
		Intent intent = new Intent();
		intent.setAction(Constants.RECIEVER_ACTION_MUSIC_UPDATE);
		intent.putExtra(INTENT_COMMEND_KEY, INTENT_COMMEND_NEXT);
		intent.putExtra(INTENT_DATA_POSITION, currentMusicPos);
		intent.putExtra(INTENT_DATA_CURRENT_MUSIC, currentMusic);
		intent.putExtra(INTENT_DATA_IS_MANUAL, isManual);
		this.sendBroadcast(intent);
		intent = null;
	}

	private void broadcastStop() {
		Intent intent = new Intent();
		intent.putExtra(INTENT_COMMEND_KEY, INTENT_COMMEND_STOP);
		intent.setAction(Constants.RECIEVER_ACTION_MUSIC_UPDATE);
		intent.putExtra(INTENT_DATA_POSITION, currentMusicPos);
		intent.putExtra(INTENT_DATA_CURRENT_MUSIC, currentMusic);
		this.sendBroadcast(intent);
		intent = null;
	}

	/**
	 * 当不是连续播放时，听完当前歌曲后执行
	 * 
	 * @defaultPosition 播完后还原列表到第几个位置（如果处在非连续播放状态下则还原到当前位置，如果处在连续播放状态下还原到0）
	 */
	private void broadcastComplete(int defaultPosition) {
		stopSelf();
		Intent intent = new Intent();
		intent.putExtra(INTENT_COMMEND_KEY, INTENT_COMMEND_COMPLETE);
		intent.setAction(Constants.RECIEVER_ACTION_MUSIC_UPDATE);
		intent.putExtra(INTENT_DATA_POSITION, defaultPosition);// 全部播完后归零
		intent.putExtra(INTENT_DATA_CURRENT_MUSIC, currentMusic);
		this.sendBroadcast(intent);
		intent = null;
	}

	private boolean isPlaying() {
		if (mMediaPlayer == null) {
			return false;
		}
		try {
			if (mMediaPlayer.isPlaying()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public void setFoucs() {
		if (manager != null && focusChangeListener != null) {
			manager.abandonAudioFocus(focusChangeListener);
			try {
				int result = manager
						.requestAudioFocus(focusChangeListener,
								AudioManager.STREAM_MUSIC,
								AudioManager.AUDIOFOCUS_GAIN);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void abandonFoucs() {
		if (manager != null && focusChangeListener != null) {
			manager.abandonAudioFocus(focusChangeListener);
		}
	}

	private int lastAudioFocus = -1000;

	private class FocusChangeListener implements OnAudioFocusChangeListener {

		@Override
		public void onAudioFocusChange(int focusChange) {
			// TODO Auto-generated method stub
			switch (focusChange) {
			case AudioManager.AUDIOFOCUS_GAIN:
				// 获得音频焦点
				if (!isPlaying()
						&& lastAudioFocus != AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
					resume();
				}
				// // 还原音量
				// if (mMediaPlayer != null) {
				// mMediaPlayer.setVolume(1.0f, 1.0f);
				// }
				break;

			case AudioManager.AUDIOFOCUS_LOSS:
				// 长久的失去音频焦点，释放MediaPlayer
				if (isPlaying()) {
					pause(true);
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				// 展示失去音频焦点，暂停播放等待重新获得音频焦点
				if (isPlaying())
					pause(false);
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				// 失去音频焦点，无需停止播放，降低声音即可
				// if (isPlaying() && mMediaPlayer != null) {
				// mMediaPlayer.setVolume(0.1f, 0.1f);
				// }
				break;
			case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
				break;
			// case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:

			case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
				// 获得音频焦点
				if (!isPlaying()) {
					resume();
				}
				// 还原音量
				if (mMediaPlayer != null) {
					mMediaPlayer.setVolume(1.0f, 1.0f);
				}
				break;

			case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
				// 失去音频焦点，无需停止播放，降低声音即可
				if (isPlaying() && mMediaPlayer != null) {
					mMediaPlayer.setVolume(0.1f, 0.1f);
				}
				break;
			}
			lastAudioFocus = focusChange;
		}
	}

	private class ServerStub extends IAudioService.Stub {
		@Override
		public boolean play(int pos) throws RemoteException {
			// TODO Auto-generated method stub
			service.play(musicList, pos);
			return true;
		}

		@Override
		public boolean playById(String id) throws RemoteException {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean rePlay() throws RemoteException {
			// TODO Auto-generated method stub
			service.resume();
			return true;
		}

		@Override
		public boolean pause() throws RemoteException {
			// TODO Auto-generated method stub
			service.pause(true);
			return true;
		}

		@Override
		public boolean next(boolean isManual) throws RemoteException {
			// TODO Auto-generated method stub
			service.next(isManual);
			return true;
		}

		@Override
		public boolean prev() throws RemoteException {
			// TODO Auto-generated method stub
			service.previous();
			return true;
		}

		@Override
		public boolean seekTo(int progress) throws RemoteException {
			// TODO Auto-generated method stub
			service.seekTo(progress);
			return true;
		}

		@Override
		public void refreshMusicList(List<Music> list) throws RemoteException {
			// TODO Auto-generated method stub
			if (musicList != null) {
				musicList.clear();
			} else {
				musicList = new ArrayList<Music>();
			}
			if (list != null) {
				musicList.addAll(list);
			}
		}

		@Override
		public List<Music> getMusicList() throws RemoteException {
			// TODO Auto-generated method stub
			return musicList;
		}

		@Override
		public String getCurMusicId() throws RemoteException {
			// TODO Auto-generated method stub
			return currentMusicId;
		}

		@Override
		public Music getCurMusic() throws RemoteException {
			// TODO Auto-generated method stub
			return currentMusic;
		}

		// 预留接口2015.1.4
		@Override
		public void updateNotification(Bitmap bitmap, String title, String name)
				throws RemoteException {
			// TODO Auto-generated method stub
		}

		// 预留接口2015.1.4
		@Override
		public void cancelNotification() throws RemoteException {
			// TODO Auto-generated method stub
		}

		@Override
		public int getSeekDuration() throws RemoteException {
			// TODO Auto-generated method stub
			if (mMediaPlayer != null && isprepared && isPlaying()) {
				return mMediaPlayer.getDuration();
			}
			return 0;
		}

		@Override
		public int getSeekPosition() throws RemoteException {
			// TODO Auto-generated method stub
			if (mMediaPlayer != null && isprepared && isPlaying()) {
				return mMediaPlayer.getCurrentPosition();
			}
			return 0;
		}

		@Override
		public void SetGoAhead(boolean isGoAhead) throws RemoteException {
			// TODO Auto-generated method stub
			service.isGoAhead = isGoAhead;
		}

		@Override
		public boolean isGoAhead() throws RemoteException {
			// TODO Auto-generated method stub
			return service.isGoAhead;
		}

		@Override
		public boolean isPlaying() throws RemoteException {
			// TODO Auto-generated method stub
			return service.isPlaying();
		}

		@Override
		public boolean isPrepared() throws RemoteException {
			// TODO Auto-generated method stub
			return service.isprepared;
		}

		@Override
		public int getCurMusicPosition() throws RemoteException {
			// TODO Auto-generated method stub
			return service.currentMusicPos;
		}
	}
}
