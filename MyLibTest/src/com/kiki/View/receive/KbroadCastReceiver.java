package com.kiki.View.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kiki.View.service.KService;
import com.kiki.android.Utils.KLog;


public class KbroadCastReceiver extends BroadcastReceiver
{
    private String boot_act = "android.intent.action.BOOT_COMPLETED";
    private String time_act = "android.intent.action.TIME_TICK";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        KLog.e(this.getClass().getSimpleName(), "KbroadCastReceiver========" + intent.getAction());
        if(intent.getAction().equals(boot_act))
        {
            Intent i = new Intent(context, KService.class);
            context.startService(i);
        }

        if(intent.getAction().equals(time_act))
        {
            KLog.e(this.getClass().getSimpleName(), "TIME_TICK");
        }
    }
}
