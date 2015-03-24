/**
 */
package com.dd.music.audio.interfaces;

import com.dd.music.audio.aidl.IAudioService;
/**
 * Bind服务绑定成功回调
 * @author J.Tommy
 */
public interface IOnServiceConnectComplete {
	public void onServiceConnectComplete(IAudioService service);
}
