package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.kiki.View.R;
import com.kiki.View.UIComfonent.BiconPopupDialog;
import com.kiki.android.Listener.UIEvent.OnPopupEventListener;
import com.kiki.android.Logic.Managers.bluetooth.BluetoothDataManager;
import com.kiki.android.Utils.KLog;

/**
 * Created by cs on 2015-10-08.
 */
public class TestActivity2 extends Activity implements OnPopupEventListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_layout);

        KLog.d(this.getClass().getSimpleName(), "@@ TestActivity2 onCreate");
        BiconPopupDialog  bpdl =   new BiconPopupDialog(this, "", "", "", "TESTSSSSSSSSS", R.layout.popupview_bicon_layout, this, 10000);            //contenxtView , popid
        bpdl.showDialog();

    }

    @Override
    public void onPopupAction(int popId, int what, Object obj) {

    }
}
