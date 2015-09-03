package com.kiki.View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.kiki.View.R;
import com.kiki.View.UIComfonent.BasicPoup;
import com.kiki.android.Listener.UIEvent.OnPopupEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Class Name : PopupTestActivity
 * @Description : 팝업 테스트 클래스
 * @since 2015. 6. 19.
 * @version 1.0
 * @author grapegirl
 */
public class PopupActivity extends Activity implements OnPopupEventListener, android.view.View.OnClickListener {

    private BasicPoup mBasicPoup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_test);
        findViewById(R.id.button1).setOnClickListener(this);
        
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("HHmmss").format(date));
        System.out.println("@@ today : " + today);
    }

    @Override
    public void onPopupAction(int what, Object obj) {
        switch (what) {
        // 확인 버튼
        case POPUP_BTN_OK:
            // 취소 버튼
        case POPUP_BTN_CLOSEE:
            if (mBasicPoup.isShowing()) {
                mBasicPoup.closePopup();
                mBasicPoup = null;
            }
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.com.kiki.android.com.kiki.View.OnClickListener#onClick(android.view.com.kiki.android.com.kiki.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button1:
            if (mBasicPoup == null) {
                mBasicPoup = new BasicPoup(this, R.layout.popup_layout, this);
                mBasicPoup
                        .setPopupData("test");
            }
            mBasicPoup.setOpnePopup();
            break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBasicPoup = null;
    }

}