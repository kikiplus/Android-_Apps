package main;

import android.app.Activity;
import android.os.Bundle;

import com.test.mihye.R;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :TransModeTest
 * @Description : 가로모드 테스트
 * @since 2015-06-28.
 */
public class TransModeTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transversemode_main_layout);
    }
}
