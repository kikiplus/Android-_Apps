package com.kiki.View.Activity.Test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @Class Name : AlramActiivty
 * @Description :  알람 후 실행되는 액티비티
 * @since 2015. 6. 19.
 * @version 1.0
 * @author grapegirl
 */
public class AlramActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("TEST");

        setContentView(textView);

        Toast.makeText(getApplicationContext(),"하하",Toast.LENGTH_LONG).show();
    }
}
