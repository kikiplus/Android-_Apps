package UIComfonent;

import android.content.Context;
import android.view.View;

import Event.PopupEventListener;
import UIFrame.ParentPopup;

/***
 * @Class Name : BasicPoup
 * @Description : 기본 팝업
 * @since 2015. 5. 19.
 * @version 1.0
 * @author grapegirl
 */
public class BasicPoup extends ParentPopup implements View.OnClickListener {

    /** 팝업 확인버튼 이벤트 */
    private static final int EVENT_BASIC_POPUP_BTN_OK = 100;
    /** 팝업 취소버튼 이벤트 */
    private static final int EVENT_BASIC_POPUP_BTN_CANCLE = 200;
    /** 팝업 버튼 이름 */
    private static final String[] EVENT_BTN_NAME = { "확인", "취소" };
    /** 팝업 버튼 이벤트 ID */
    private static final int[] EVENT_BTN = { EVENT_BASIC_POPUP_BTN_OK, EVENT_BASIC_POPUP_BTN_CANCLE };

    /**
     * 기본 팝업 생성자
     * 
     * @param context
     * @param res
     * @param l
     */
    public BasicPoup(Context context, int res, PopupEventListener l) {
        super(context, res, l);
        m_PopupListener = l;
    }

    @Override
    protected void init() {
        setTitle("팝업");
        setPopupSize(656, 300);
        setButton(EVENT_BTN_NAME, EVENT_BTN, this);

    }

    @Override
    public void setPopupData(Object obj) {
        setContentView(0, obj);
    }

    @Override
    public void onPopupBackPressed() {
        super.onPopupBackPressed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getId();
        switch (tag) {
        case EVENT_BASIC_POPUP_BTN_OK:
            m_PopupListener.onPopupAction(m_PopupListener.POPUP_BTN_OK, null);
            System.out.println("@@ basic ok 버튼 ");
            break;
        case EVENT_BASIC_POPUP_BTN_CANCLE:
            m_PopupListener.onPopupAction(m_PopupListener.POPUP_BTN_CLOSEE, null);
            System.out.println("@@ basic cancle 버튼 ");
            break;
        }
    }
}
