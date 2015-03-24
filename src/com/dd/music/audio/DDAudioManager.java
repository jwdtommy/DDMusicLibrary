package com.dd.music.audio;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.dd.music.audio.aidl.IAudioService;
import com.dd.music.audio.interfaces.IOnServiceConnectComplete;
import com.dd.music.entry.Music;
import com.dd.music.utils.CommonUtils;

/**
 * @author J.Tommy
 */
public class DDAudioManager {

	private static DDAudioManager manager;
	private static Context mContext;
	private IAudioService mIAudioService;
	private ServiceConnection mConn;

	public static final String SERVICE_ACTION = "com.dd.music.audio.AudioService";

	public static synchronized DDAudioManager getInstance(Context context) {
		mContext = context;
		if (manager == null) {
			manager = new DDAudioManager(context);
		}
		return manager;
	}

	private DDAudioManager(Context context) {
	//	initConn(null);
	}
	
	
	public void connect()
	{
		
	}

//	private void initConn(
//	final IOnServiceConnectComplete mIOnServiceConnectComplete) {
//		Log.i("jwd", "initConn onServiceDisconnected");
//		mConn = new ServiceConnection() {
//			@Override
//			public void onServiceDisconnected(ComponentName name) {
//				Log.i("jwd", "initConn onServiceDisconnected");
//			}
//
//			@Override
//			public void onServiceConnected(ComponentName name, IBinder service) {
//				mIAudioService = IAudioService.Stub.asInterface(service);
//				Log.i("jwd", "initConn onServiceConnected");
//				if (mIAudioService != null
//						&& mIOnServiceConnectComplete != null) {
//					mIOnServiceConnectComplete
//							.onServiceConnectComplete(mIAudioService);
//				}
//			}
//		};
//	}
	public void connectService(
			final IOnServiceConnectComplete mIOnServiceConnectComplete) {
		Intent intent = new Intent(SERVICE_ACTION);
		if (mConn == null) {
			mConn = new ServiceConnection() {
				@Override
				public void onServiceDisconnected(ComponentName name) {
				}
				@Override
				public void onServiceConnected(ComponentName name,
						IBinder service) {
					mIAudioService = IAudioService.Stub.asInterface(service);
					if (mIAudioService != null
							&& mIOnServiceConnectComplete != null) {
						mIOnServiceConnectComplete
								.onServiceConnectComplete(mIAudioService);
					}
				}
			};
		}
		if (!CommonUtils.isServiceWorked(mContext, AudioService.class)) {
			mContext.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
		}
	}

	public void disConnectService() {
		if (mConn != null) {
			mContext.unbindService(mConn);
			mContext.stopService(new Intent(SERVICE_ACTION));
		}
	}

	public boolean isConnectService() {
		return CommonUtils.isServiceWorked(mContext, AudioService.class);
	}

	public void reFreshMusicList(final ArrayList<Music> musicList) {
		if (isConnectService()) {
			try {
				if (mIAudioService == null) {
					return;
				}
				mIAudioService.refreshMusicList(musicList);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			connectService(new IOnServiceConnectComplete() {
				@Override
				public void onServiceConnectComplete(IAudioService service) {
					// TODO Auto-generated method stub
					try {
						if (mIAudioService == null) {
							return;
						}
						mIAudioService.refreshMusicList(musicList);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

	}

	public void rePlay() {
		if (isConnectService()) {
			try {
				if (mIAudioService == null) {
					return;
				}
				mIAudioService.rePlay();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			connectService(new IOnServiceConnectComplete() {

				@Override
				public void onServiceConnectComplete(IAudioService service) {
					// TODO Auto-generated method stub
					try {
						if (mIAudioService == null) {
							return;
						}
						mIAudioService.rePlay();
						AudioTimer.getInstance().startTimer();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

	}

	public void pause() {
		try {
			if (mIAudioService == null) {
				return;
			}
			mIAudioService.pause();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void next(boolean isManual) {
		try {
			if (mIAudioService == null) {
				return;
			}
			mIAudioService.next(isManual);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void previous() {
		try {
			if (mIAudioService == null) {
				return;
			}
			mIAudioService.prev();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void seekTo(int position) {
		try {
			if (mIAudioService == null) {
				return;
			}
			mIAudioService.seekTo(position);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playByPosition(final int position) {
		if (isConnectService()) {
			try {
				if (mIAudioService == null) {
					return;
				}
				mIAudioService.play(position);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			connectService(new IOnServiceConnectComplete() {
				@Override
				public void onServiceConnectComplete(IAudioService service) {
					// TODO Auto-generated method stub
					try {
						if (mIAudioService == null) {
							return;
						}
						mIAudioService.play(position);
						AudioTimer.getInstance().startTimer();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	public void playById(final String id) {
		if (isConnectService()) {
			try {
				if (mIAudioService == null) {
					return;
				}
				mIAudioService.playById(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			connectService(new IOnServiceConnectComplete() {
				@Override
				public void onServiceConnectComplete(IAudioService service) {
					// TODO Auto-generated method stub
					try {
						if (mIAudioService == null) {
							return;
						}
						mIAudioService.playById(id);
						AudioTimer.getInstance().startTimer();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	public Music getCurrentMusic() {
		try {
			if (mIAudioService == null) {
				return null;
			}
			return mIAudioService.getCurMusic();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int getCurrentMusicPosition() {
		try {
			if (mIAudioService == null) {
				return 0;
			}
			return mIAudioService.getCurMusicPosition();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList<Music> getMusicList() {
		try {
			if (mIAudioService == null) {
				return null;
			}
			return (ArrayList<Music>) mIAudioService.getMusicList();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public String getCurrentMusicId() {
		try {
			if (mIAudioService == null) {
				return null;
			}
			return mIAudioService.getCurMusicId();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int getSeekingDurtion() {
		try {
			if (mIAudioService == null) {
				return 0;
			}
			return mIAudioService.getSeekDuration();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int getSeekingPosition() {
		try {
			if (mIAudioService == null) {
				return 0;
			}
			return mIAudioService.getSeekPosition();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public void setGoAhead(boolean isGoAhead) {
		try {
			if (mIAudioService == null) {
				return;
			}
			mIAudioService.SetGoAhead(isGoAhead);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isGoAhead() {
		try {
			if (mIAudioService == null) {
				return false;
			}
			return mIAudioService.isGoAhead();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}

	public boolean isPlaying() {
		try {
			if (mIAudioService == null) {
				return false;
			}
			return mIAudioService.isPlaying();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean isPrepared() {
		try {
			if (mIAudioService == null) {
				return false;
			}
			return mIAudioService.isPrepared();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
