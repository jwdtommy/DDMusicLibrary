/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\MyWorkspace\\DDMusicLibrary\\src\\com\\dd\\music\\audio\\aidl\\IAudioService.aidl
 */
package com.dd.music.audio.aidl;
public interface IAudioService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.dd.music.audio.aidl.IAudioService
{
private static final java.lang.String DESCRIPTOR = "com.dd.music.audio.aidl.IAudioService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.dd.music.audio.aidl.IAudioService interface,
 * generating a proxy if needed.
 */
public static com.dd.music.audio.aidl.IAudioService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.dd.music.audio.aidl.IAudioService))) {
return ((com.dd.music.audio.aidl.IAudioService)iin);
}
return new com.dd.music.audio.aidl.IAudioService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_play:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.play(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_playById:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.playById(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_rePlay:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.rePlay();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.pause();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_prev:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.prev();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_seekTo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.seekTo(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_next:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.next(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_refreshMusicList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.dd.music.entry.Music> _arg0;
_arg0 = data.createTypedArrayList(com.dd.music.entry.Music.CREATOR);
this.refreshMusicList(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getMusicList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.dd.music.entry.Music> _result = this.getMusicList();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getCurMusicPosition:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurMusicPosition();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCurMusic:
{
data.enforceInterface(DESCRIPTOR);
com.dd.music.entry.Music _result = this.getCurMusic();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getCurMusicId:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getCurMusicId();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getSeekDuration:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getSeekDuration();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getSeekPosition:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getSeekPosition();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateNotification:
{
data.enforceInterface(DESCRIPTOR);
android.graphics.Bitmap _arg0;
if ((0!=data.readInt())) {
_arg0 = android.graphics.Bitmap.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
this.updateNotification(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_cancelNotification:
{
data.enforceInterface(DESCRIPTOR);
this.cancelNotification();
reply.writeNoException();
return true;
}
case TRANSACTION_SetGoAhead:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.SetGoAhead(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isGoAhead:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isGoAhead();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isPlaying:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPlaying();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isPrepared:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPrepared();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.dd.music.audio.aidl.IAudioService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public boolean play(int pos) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pos);
mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean playById(java.lang.String id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(id);
mRemote.transact(Stub.TRANSACTION_playById, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean rePlay() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_rePlay, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean prev() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_prev, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean seekTo(int progress) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progress);
mRemote.transact(Stub.TRANSACTION_seekTo, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean next(boolean isManual) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isManual)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_next, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void refreshMusicList(java.util.List<com.dd.music.entry.Music> musicList) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(musicList);
mRemote.transact(Stub.TRANSACTION_refreshMusicList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<com.dd.music.entry.Music> getMusicList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.dd.music.entry.Music> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMusicList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.dd.music.entry.Music.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getCurMusicPosition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurMusicPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.dd.music.entry.Music getCurMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.dd.music.entry.Music _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurMusic, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.dd.music.entry.Music.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getCurMusicId() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurMusicId, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getSeekDuration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSeekDuration, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getSeekPosition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSeekPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void updateNotification(android.graphics.Bitmap bitmap, java.lang.String title, java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((bitmap!=null)) {
_data.writeInt(1);
bitmap.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeString(title);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_updateNotification, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void cancelNotification() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_cancelNotification, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void SetGoAhead(boolean isGoAhead) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isGoAhead)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SetGoAhead, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isGoAhead() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isGoAhead, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isPlaying() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPlaying, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isPrepared() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPrepared, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_playById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_rePlay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_prev = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_seekTo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_next = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_refreshMusicList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getMusicList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getCurMusicPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getCurMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getCurMusicId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getSeekDuration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getSeekPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_updateNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_cancelNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_SetGoAhead = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_isGoAhead = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_isPlaying = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_isPrepared = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
}
public boolean play(int pos) throws android.os.RemoteException;
public boolean playById(java.lang.String id) throws android.os.RemoteException;
public boolean rePlay() throws android.os.RemoteException;
public boolean pause() throws android.os.RemoteException;
public boolean prev() throws android.os.RemoteException;
public boolean seekTo(int progress) throws android.os.RemoteException;
public boolean next(boolean isManual) throws android.os.RemoteException;
public void refreshMusicList(java.util.List<com.dd.music.entry.Music> musicList) throws android.os.RemoteException;
public java.util.List<com.dd.music.entry.Music> getMusicList() throws android.os.RemoteException;
public int getCurMusicPosition() throws android.os.RemoteException;
public com.dd.music.entry.Music getCurMusic() throws android.os.RemoteException;
public java.lang.String getCurMusicId() throws android.os.RemoteException;
public int getSeekDuration() throws android.os.RemoteException;
public int getSeekPosition() throws android.os.RemoteException;
public void updateNotification(android.graphics.Bitmap bitmap, java.lang.String title, java.lang.String name) throws android.os.RemoteException;
public void cancelNotification() throws android.os.RemoteException;
public void SetGoAhead(boolean isGoAhead) throws android.os.RemoteException;
public boolean isGoAhead() throws android.os.RemoteException;
public boolean isPlaying() throws android.os.RemoteException;
public boolean isPrepared() throws android.os.RemoteException;
}
