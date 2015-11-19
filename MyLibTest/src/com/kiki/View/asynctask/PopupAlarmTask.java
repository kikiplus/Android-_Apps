package com.kiki.View.asynctask;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.kiki.View.Activity.TestB.DpiCheckActivity;
import com.kiki.android.Utils.KLog;

import org.json.JSONObject;

public class PopupAlarmTask extends AsyncTask<Void, Void, JSONObject> {
    private Context mContext;

    public PopupAlarmTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        KLog.d(this.getClass().getSimpleName() , "@@ PopupAlramTask doInBackground");
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        KLog.d(this.getClass().getSimpleName() , "@@ PopupAlramTask onPostExecute");
        super.onPostExecute(result);
        try {
            Intent intent = new Intent(mContext, DpiCheckActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.send();

            KLog.d(this.getClass().getSimpleName(), "@@ PopupAlramTask onPostExecute 2");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
