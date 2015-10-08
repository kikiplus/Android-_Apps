package com.kiki.View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.kiki.View.R;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-07-03.
 */
public class CardSubViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.cardview_sub_layout);
    }
}
