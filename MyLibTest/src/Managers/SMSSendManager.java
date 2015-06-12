package Managers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSSendManager {
    private SMSSendManager() {
    }

    public static SMSSendManager getInstance() {
        if (Instance == null) {
            Instance = new SMSSendManager();
        }
        return Instance;
    }

    public void setContext(Context context) {
        m_Context = context;
        m_Context.registerReceiver(m_receiverSendAction, new IntentFilter("SMS_SENT_ACTION"));
        m_Context.registerReceiver(m_receiverDelveredAction, new IntentFilter("SMS_DELIVERED_ACTION"));
    }

    public void sendMessage(String phoneNum, String msg, SMSSendListener l) throws IllegalAccessError {
        if (m_bUsed)
            throw new IllegalAccessError("Sending SMS");
        m_bUsed = true;
        m_smsSendListener = l;
        final PendingIntent sentIntent = PendingIntent.getBroadcast(m_Context, 0, new Intent("SMS_SENT_ACTION"), 0);
        final PendingIntent deliveredIntent = PendingIntent.getBroadcast(m_Context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNum, null, msg, sentIntent, deliveredIntent);

    }

    private static SMSSendManager Instance;
    private Context m_Context;
    private SMSSendListener m_smsSendListener;
    private boolean m_bUsed;

    private BroadcastReceiver m_receiverSendAction = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (m_smsSendListener != null) {
                switch (getResultCode()) {
                case Activity.RESULT_OK:
                    m_smsSendListener.onSMSSendResult(true);
                    break;
                default:
                    m_smsSendListener.onSMSSendResult(false);
                    break;
                }
            }
        }
    };

    private BroadcastReceiver m_receiverDelveredAction = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
            case Activity.RESULT_OK:
                // 도착 완료
                Toast.makeText(m_Context, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                break;
            case Activity.RESULT_CANCELED:
                // 도착 안됨
                Toast.makeText(m_Context, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    };

    public interface SMSSendListener {
        public void onSMSSendResult(boolean flag);
    }
}
