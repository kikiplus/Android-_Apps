package com.kiki.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.kiki.View.R;
import com.kiki.android.Utils.AppUtils;
import com.kiki.android.Utils.KLog;

/**
 * Created by cs on 2015-10-08.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        String phone = AppUtils.getUserPhoneNumber(this);

        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        KLog.d(this.getClass().getSimpleName(), "@@ phone : " + phone);
        KLog.d(this.getClass().getSimpleName(), "@@ getLine1Number : " + telephony.getLine1Number());
        KLog.d(this.getClass().getSimpleName(), "@@ getSimOperator : " + telephony.getSimOperator());
        KLog.d(this.getClass().getSimpleName(), "@@ getSimCountryIso : " + telephony.getSimCountryIso());

                ((TextView) findViewById(R.id.textView1)).setText(AppUtils.getUserPhoneNetworkOperationName(this));
    }
}
