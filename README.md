# DDMusicLibrary是一个Android平台上开源的音频封装类库

#（此类库仅供学习使用）

1. 准备工作
在使用DDMusicLibrary之前，需要先进行相关的配置及初始化工作


    1.1 配置AndroidManifest.xml
        <service
            android:name="com.dd.music.audio.AudioService"
            android:process=":remote" >
            <intent-filter>
                <action android:name="com.dd.music.audio.AudioService" />
            </intent-filter>
        </service>
        
    1.2 初始化音频服务
    一般可以放在Application或者首个Activity的onCreate()中

		DDAudioManager.getInstance(mContext).connectService(
				new IOnServiceConnectComplete() {
					@Override
					public void onServiceConnectComplete(IAudioService service) {
						// TODO Auto-generated method stub
						Log.i("DDMusicLibrary","**onServiceConnectComplete**");
					}
				});

2.音频操作相关API
   
   2.1 播放
   
    DDAudioManager.getInstance(context).playById(id);
    
    DDAudioManager.getInstance(context).playByPosition(position);
    
   2.2 暂停
   
   DDAudioManager.getInstance(context).pause();
   
   2.3 刷新音频列表
   
   DDAudioManager.getInstance(context).refreshMusicList(musicList);
 
   2.4 上一首
   
   DDAudioManager.getInstance(context).previous();
 
   2.5 下一首
   
   DDAudioManager.getInstance(context).next();
   
   2.6 定位音频播放位置
   
   DDAudioManager.getInstance(context).seekTo(position);
   
   2.7 设置是否连续播放
   
   DDAudioManager.getInstance(context).setGoAhead(true);
   
3.获取状态信息 （V2.0时会增加缓冲进度的获取）

   3.1 音频服务是否连接成功
   
      DDAudioManager.getInstance(context).isConnectService();
   
   3.2 获取当前正在播放的音频
   
   DDAudioManager.getInstance(context).getCurrentMusic();
   
   3.2 获取当前正在第几首音频
   
   DDAudioManager.getInstance(context).getCurrentMusicPosition();  
   
   3.3 获取当前音频播放列表
   
   DDAudioManager.getInstance(context).getMusicList();  
   
   3.4 获取当前音频的ID
   
   DDAudioManager.getInstance(context).getCurrentMusicId();  
   
   3.5 获取当前音频的总时长
   
   DDAudioManager.getInstance(context).getSeekingDurtion();  
   
   3.6 获取播放进度
   
   DDAudioManager.getInstance(context).getSeekingPosition();  
   
   3.7 是否是连续播放
   
   DDAudioManager.getInstance(context).isGoAhead();  
   
   3.8 是否正在播放
   
   DDAudioManager.getInstance(context).isPlaying();  
   
   3.8 是否准备完毕
   
   DDAudioManager.getInstance(context).isPrepared();  
  
4.音频操作回调监听

   	private class Receiver extends DDSingleAudioBroadCastReceiver {
		@Override
		public void onPlay(int position, Music currentMusic) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onResume(int position, Music currentMusic) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStop(int position, Music currentMusic) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onComplete(int position, Music currentMusic) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onNext(int position, Music currentMusic, boolean isManual) {
			// TODO Auto-generated method stub
		}
	}
	
   5.定时任务，如更新播放进度条，缓冲进度条
   
   AudioTimer.getInstance().putHandler(handler);
   
   AudioTimer.getInstance().startTimer();


