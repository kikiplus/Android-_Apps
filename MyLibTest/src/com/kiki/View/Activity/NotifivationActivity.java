package com.kiki.View.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.kiki.View.R;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-07-06.
 */

public class NotifivationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main_layout);
    }

    public void notify(View view) {
        new NotifiacationTask().execute();
    }


    private void executeTask() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("01012345678"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("전화")
                        .setContentText("전화가 왔습니다.")
                        .setSmallIcon(android.R.drawable.ic_menu_call)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[0])
                        .addAction(android.R.drawable.ic_menu_call, "전화", pendingIntent)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setVisibility(Notification.VISIBILITY_PRIVATE)
                        .setCategory(Notification.CATEGORY_CALL);

        notificationManager.notify(0, mBuilder.build());
    }

    /**
     * 노티피케이션 Task
     */
    public class NotifiacationTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(3000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            executeTask();
        }
    }
}
