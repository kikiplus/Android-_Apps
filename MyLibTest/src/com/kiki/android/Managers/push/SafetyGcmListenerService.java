package com.kiki.android.Managers.push;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.kiki.android.Utils.ContextUtils;
import com.kiki.android.Utils.KLog;

/***
 * @author grape girl
 * @version 1.0
 * @Class Name : SafetyGcmListenerService
 * @Description : GCM 메시지 수신 서비스
 * @since 2015. 6. 8.
 */
public class SafetyGcmListenerService extends GcmListenerService {

    private static SafetyGcmListenerService INSTANCE;
    private JSONObject mjsonObject = null;
    private Context mContext = null;

    public static SafetyGcmListenerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SafetyGcmListenerService();
        }
        return INSTANCE;
    }

    public SafetyGcmListenerService() {
        KLog.d(this.getClass().getSimpleName(), "@@ GCMIntentService()");
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        KLog.d(this.getClass().getSimpleName(), "@@ From: " + from);
        KLog.d(this.getClass().getSimpleName(), "@@ Message: " + data.toString());

        if (data.getString("msg") == null) {
            return;
        }
        String jsonString = data.getString("msg");
        KLog.d(this.getClass().getSimpleName(), "@@ jsonString Message: " + jsonString);

        try {
            String message = URLDecoder.decode(jsonString, "UTF-8");
            KLog.d(this.getClass().getSimpleName(), "@@ message Message: " + message);
            KLog.d(this.getClass().getSimpleName(), "@@ GCMIntentService 메시지가 왔습니다 :" + message != null ? message : "");

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }
}
