package com.kiki.android.Logic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : AlramReceiver
 * @Description : 알람 수신 받는 리시버 클래스
 * @since 2015-06-29.
 */
public class AlramReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, "AlramReceiver onReceive");
        Toast.makeText(context, "알람 실행", Toast.LENGTH_LONG).show();
    }
}
