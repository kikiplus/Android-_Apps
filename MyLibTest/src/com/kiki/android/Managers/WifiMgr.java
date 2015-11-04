/**
 *
 */
package com.kiki.android.Managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.util.List;

import com.kiki.View.Bean.WiFiAP;
import com.kiki.android.Listener.IWiFiScanLinstener;
import com.kiki.android.Utils.KLog;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : WifiManger.java
 * @Description : 와이파이 스캐너
 * @since 2014.08.01
 */
public class WifiMgr {

    /**
     * Wifi 매니저
     */
    private WifiManager mWifiManger = null;

    /**
     * Wifi 결과 전송 리스너
     */
    private IWiFiScanLinstener mScanListener = null;
    /**
     * 컨텍스트
     */
    private Context mContext = null;

    /**
     * wifi 스캔 여부
     */
    private boolean isScanning = false;

    /**
     * 생성자
     */
    public WifiMgr(Context context, IWiFiScanLinstener scanListener) {
        mContext = context;
        mScanListener = scanListener;
        mWifiManger = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }


    /**
     * 스캔 시작 메소드
     */
    public void startScan() {
        if (Build.VERSION.SDK_INT >= 18) {
            //4.3버전 이상에서 항상 검색 허용인 경우
            // 바로 스캔
            if (mWifiManger.isScanAlwaysAvailable()) {
                mWifiManger.startScan();
                isScanning = true;
                mContext.registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                KLog.d(this.getClass().getSimpleName(), this.getClass() + " startScan");
                return;
            }
        }
        if (mWifiManger.getWifiState() == WifiManager.WIFI_STATE_DISABLED) {
            mWifiManger.setWifiEnabled(true);
        }
        mWifiManger.startScan();
        isScanning = true;
        mContext.registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " startScan");
    }


    /**
     * 스캔 중지 메소드
     */

    public void stopScan() {
        isScanning = false;
        mContext.unregisterReceiver(mWifiReceiver);
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " stopScan");
    }

    /**
     * wifi 스캔 응답 결과 콜백 리시버
     */
    private BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            stopScan();
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                KLog.d(this.getClass().getSimpleName(),  this.getClass() + " wifiReciever ");
                List<ScanResult> scanLists = mWifiManger.getScanResults();
                mScanListener.onReceiveAction(scanLists);
            }
        }
    };

    /**
     * 스캔 정보 반환 메소드
     *
     * @return 스캔 여부
     */
    public boolean getIsScanning() {
        return isScanning;
    }


    /**
     * 특정 AP 접속 메소드
     *
     * @param ap   AP정보
     * @param pass 비밀번호
     * @return 연결 후 결과
     */
    public boolean connectAp(WiFiAP ap, String pass) {
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " connectAp ");
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " ssid :  " + ap.getSSID());
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " bssid :  " + ap.getMac());
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " getCapability :  " + ap.getCapability());
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " getDecibel :  " + ap.getDecibel());
        KLog.d(this.getClass().getSimpleName(),  this.getClass() + " pass :  " + pass);

        //접속할 AP에대한 환경설정 하긴
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ap.getSSID());
        if (ap.getCapability().contains("WEP")) {
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        } else if (ap.getCapability().contains("WPA")) {
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.preSharedKey = "\"".concat(pass).concat("\"");
        } else {//개방형
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedAuthAlgorithms.clear();
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        }

        int netId = mWifiManger.addNetwork(wifiConfig);
        mWifiManger.disconnect();
        mWifiManger.enableNetwork(netId, true);
        boolean isResult = mWifiManger.reconnect();
        return isResult;
    }

    /**
     * 연결 해제 메소드
     *
     * @return 연결 해제 결과
     */
    public boolean disconnectAP() {
        boolean isResult = mWifiManger.disconnect();
        return isResult;
    }
}
