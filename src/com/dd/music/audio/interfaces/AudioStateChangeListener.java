package com.dd.music.audio.interfaces;

/*
 * @author J.Tommy
 * 音频状态切换接口
 */
public interface AudioStateChangeListener {
	public void onPlay(int position);

	public void onSeek(int seekPosition, int seekMax);

	public void onStopAudio();

	public void onResume(int position);

	public void onComplete(int position);
}