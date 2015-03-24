package com.dd.music.entry;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
public class Music implements Parcelable {
	public final static String KEY_MUSIC = "music";
	public static final String KEY_MUSIC_ID = "music_id";
	public static final String KEY_MUSIC_URL = "musci_url";
	public static final String KEY_MUSIC_NAME = "music_name";
	public static final String KEY_MUSIC_DURTION = "music_durtion";
	public static final String KEY_NEWS_ID = "news_id";
	public static final String KEY_NEWS_TYPE = "news_type";
	public static final String KEY_NEWS_TAGID = "news_tagid";

	private String musicId = "";
	private String url = "";
	private String musicName;
	private String durtion;
	private String newsId;
	private String newsType;
	private String newsTagId;

	// public Music(String musicId, String url) {
	// this.musicId = musicId;
	// this.url = url;
	// }
	public Music() {

	}

	public Music(String musicId, String url, String musicName, String durtion,
			String newsId, String newsType, String newsTagId) {
		this.musicId = musicId;
		this.url = url;
		this.musicName = musicName;
		this.durtion = durtion;
		this.newsId = newsId;
		this.newsType = newsType;
		this.newsTagId = newsTagId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getNewsTagId() {
		return newsTagId;
	}

	public void setNewsTagId(String newsTagId) {
		this.newsTagId = newsTagId;
	}

	public String getDurtion() {
		return durtion;
	}

	public void setDurtion(String durtion) {
		this.durtion = durtion;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle bundle = new Bundle();
		bundle.putString(KEY_MUSIC_ID, musicId);
		bundle.putString(KEY_MUSIC_NAME, musicName);
		bundle.putString(KEY_MUSIC_URL, url);
		bundle.putString(KEY_MUSIC_DURTION, durtion);
		bundle.putString(KEY_NEWS_ID, newsId);
		bundle.putString(KEY_NEWS_TYPE, newsType);
		bundle.putString(KEY_NEWS_TAGID, newsTagId);
		dest.writeBundle(bundle);
	}

	public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {

		@Override
		public Music createFromParcel(Parcel source) {
			Music music = new Music();
			Bundle bundle = new Bundle();
			bundle = source.readBundle();
			music.musicId = bundle.getString(KEY_MUSIC_ID);
			music.musicName = bundle.getString(KEY_MUSIC_NAME);
			music.url = bundle.getString(KEY_MUSIC_URL);
			music.durtion = bundle.getString(KEY_MUSIC_DURTION);
			music.newsId = bundle.getString(KEY_NEWS_ID);
			music.newsType = bundle.getString(KEY_NEWS_TYPE);
			music.newsTagId = bundle.getString(KEY_NEWS_TAGID);
			return music;
		}

		@Override
		public Music[] newArray(int size) {
			return new Music[size];
		}
	};

	@Override
	public String toString() {
		return "Music [musicId=" + musicId + ", url=" + url + ", musicName="
				+ musicName + ", durtion=" + durtion + ", newsId=" + newsId
				+ ", newsType=" + newsType + ", newsTagId=" + newsTagId + "]";
	}

}
