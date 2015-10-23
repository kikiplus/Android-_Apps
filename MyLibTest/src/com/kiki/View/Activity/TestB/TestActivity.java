package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kiki.View.R;
import com.kiki.View.UIComfonent.BiconPopupDialog;
import com.kiki.View.asynctask.PopupAlarmTask;
import com.kiki.View.service.KService;
import com.kiki.android.Listener.UIEvent.OnPopupEventListener;
import com.kiki.android.Logic.Managers.bluetooth.BluetoothDataManager;
import com.kiki.android.Utils.AppUtils;
import com.kiki.android.Utils.KLog;

/**
 * Created by cs on 2015-10-08.
 */
public class TestActivity extends Activity implements OnPopupEventListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        String wifi_5g = "f4:fd:2b:10:1a:df";
        String wifi_2_5g = BluetoothDataManager.changeMacAddress(wifi_5g, 1);

        KLog.d(this.getClass().getSimpleName(), "@@ wifi 5g mac : " + wifi_5g);
        KLog.d(this.getClass().getSimpleName(), "@@ wifi 2.5g mac : " + wifi_2_5g);

        ((TextView) findViewById(R.id.textView1)).setText(wifi_2_5g);

        Intent intent = new Intent(this, KService.class);
        startService(intent);
    }

    @Override
    public void onPopupAction(int popId, int what, Object obj) {

    }
}
