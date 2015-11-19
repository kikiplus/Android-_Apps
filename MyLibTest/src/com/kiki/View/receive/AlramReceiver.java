package com.kiki.View.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kiki.android.Utils.KLog;

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
        KLog.d(this.getClass().getSimpleName(), "AlramReceiver onReceive");
        Toast.makeText(context, "알람 실행", Toast.LENGTH_LONG).show();
    }
}
