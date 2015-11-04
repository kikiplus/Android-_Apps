package com.kiki.View.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kiki.View.asynctask.PopupAlarmTask;

/**
 * Created by cs on 2015-10-23.
 */
public class KService extends Service {

    public KService(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PopupAlarmTask(this).execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
