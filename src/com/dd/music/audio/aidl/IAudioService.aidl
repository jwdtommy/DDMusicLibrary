package com.dd.music.audio.aidl;
import  com.dd.music.entry.Music;
import android.graphics.Bitmap;

interface IAudioService {
    boolean play(int pos);
    boolean playById(String id);
    boolean rePlay();
	boolean pause();
	boolean prev();
	boolean seekTo(int progress);
	boolean next(boolean isManual);

    void refreshMusicList(in List<Music> musicList);
    List<Music> getMusicList();
    int getCurMusicPosition();
    Music getCurMusic();
    String getCurMusicId();
    int getSeekDuration();
    int getSeekPosition();
    
    void updateNotification(in Bitmap bitmap, String title, String name);
    void cancelNotification();

    void SetGoAhead(boolean isGoAhead);
    boolean isGoAhead();
    boolean isPlaying();
    boolean isPrepared();    
}