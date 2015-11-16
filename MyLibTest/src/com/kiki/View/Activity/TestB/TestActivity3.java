package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.kiki.View.R;
import com.kiki.android.Utils.DisplayUtils;

import org.w3c.dom.Text;

/**
 * Created by cs on 2015-11-16.
 */
public class TestActivity3 extends Activity {

    private String dpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test3_layout);


        ((TextView) findViewById(R.id.test3_textview)).setText(DisplayUtils.getDisplayDpi(getApplicationContext())+"");

    }
}
