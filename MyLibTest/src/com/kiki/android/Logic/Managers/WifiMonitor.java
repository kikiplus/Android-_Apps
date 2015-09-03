package com.kiki.android.Logic.Managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.kiki.android.Listener.IWiFiStatusListener;


/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : WifiMonitor
 * @Description : 와이파이 상태 모니터 리시버
 * @since 2015-07-09.
 */
public class WifiMonitor extends BroadcastReceiver {

    /**
     * 모니터 상태 리스너 객체
     */
    private IWiFiStatusListener mIWiFiStatusListener;

    /**
     * 생성자
     *
     * @param listener 모니터 리스너
     */
    public WifiMonitor(IWiFiStatusListener listener) {
        mIWiFiStatusListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String strAction = intent.getAction();

        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, this.getClass() + " onReceive action :  " + strAction);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (strAction.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.NETWORK_STATE_CONNECTED, null);
            } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.NETWORK_STATE_DISCONNECTED, null);
            } else if (networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.NETWORK_STATE_CONNECTING, null);
            } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTING) {
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.NETWORK_STATE_DISCONNECTING, null);
            } else if(networkInfo.getState() == NetworkInfo.State.UNKNOWN) {
                Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, this.getClass() + " onReceive  : 111111111 " );
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.NETWORK_STATE_DISCONNECTED, null);
            } else if(networkInfo.getState() == NetworkInfo.State.SUSPENDED) {
                Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, this.getClass() + " onReceive  : 111111111 222222222" );
                mIWiFiStatusListener.OnChangedStatus(mIWiFiStatusListener.NETWORK_STATE_DISCONNECTED, null);
            }
        }else if(strAction.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, this.getClass() + " onReceive  :  " + intent.getStringExtra(ConnectivityManager.EXTRA_EXTRA_INFO));
        }
    }
}
