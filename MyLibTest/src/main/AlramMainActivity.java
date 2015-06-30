package main;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.mihye.R;


/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : AlramMainActivity
 * @Description : 알람 매니저 이용 클래스
 * @since 2015. 6. 19.
 */
public class AlramMainActivity extends Activity implements View.OnClickListener {

    /**
     * 알람 등록 버튼
     */
    private Button mAddBtn = null;
    /**
     * 알람 취소 버튼
     */
    private Button mCancleBtn = null;
    /**
     * 알람 인텐트 액션명
     */
    private final String INTENT_ACTION = "com.test.mihye.main.alram";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alram_main_layout);
        mAddBtn = (Button) findViewById(R.id.alram_main_add_alram);
        mCancleBtn = (Button) findViewById(R.id.alram_main_cancle_alram);
        mAddBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alram_main_add_alram:
                Toast.makeText(getApplicationContext(), "알람 등록", Toast.LENGTH_LONG).show();
                setAlram(3000);
                break;
            case R.id.alram_main_cancle_alram:
                Toast.makeText(getApplicationContext(), "알람 해지", Toast.LENGTH_LONG).show();
                cancleAlram();
                break;
        }
    }

    /**
     * 알람 등록 메소드
     *
     * @param second 시간(초)
     */
    public void setAlram(long second) {
        AlarmManager alramManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(INTENT_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        alramManager.set(alramManager.RTC, System.currentTimeMillis() + 3000, pendingIntent);

//        브로드캐스트 리시버로 알람 울리기
//        Intent broadcastIntent = new Intent(this, AlramReceiver.class);
//        PendingIntent broadcastPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadcastIntent, 0);
//        alramManager.set(alramManager.RTC, System.currentTimeMillis() + 3000, broadcastPendingIntent);
    }

    /**
     * 알람 취소 메소드
     */
    public void cancleAlram() {
        AlarmManager alramManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(INTENT_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        alramManager.cancel(pendingIntent);
    }
}
