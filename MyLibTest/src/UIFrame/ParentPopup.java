package UIFrame;

import Event.PopupEventListener;

import com.test.mihye.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/***
 * @Class Name : ParentPopup
 * @Description : 최상위 팝업
 * @since 2015. 5. 18.
 * @version 1.0
 * @author mh kim
 */
public abstract class ParentPopup implements android.content.DialogInterface.OnKeyListener {

    /** 컨텍스트 */
    protected Context m_Context = null;
    /** 리소스 */
    protected int m_Res = 0;
    /** 팝업 뷰 */
    protected View m_CurrentView = null;
    /** 팝업 창 */
    protected Dialog m_Dialog = null;
    /** 팝업 창 빌드 */
    protected AlertDialog.Builder m_DialogBuilder = null;
    /** 팝업 이벤트 리스너 */
    public PopupEventListener m_PopupListener = null;

    /** 핸들러 */
    private Handler m_Handler = null;
    /** 취소여부 (true- cancle, false = no cancle */
    private boolean m_bCancelable = false;
    /** 기본 팝업 가로 사이즈 */
    private int m_nDefaultWidth = 656;
    /** 기본 팝업 세로 사이즈 */
    private int m_nDefaultHeight = 500;

    /** 팝업 구분 - 기본 0 */
    public static final int TYPE_BASIC_POPUP = 0;
    /** 팝업 구분 - 리스트 1 */
    public static final int TYPE_LIST_POPUP = 1;
    /** 팝업 구분 - 라디오 버튼 2 */
    public static final int TYPE_RADIO_POPUP = 2;

    /** 클릭 이벤트 */
    protected OnClickListener m_ClickListener = null;
    /** 다이얼로그 클릭 이벤트 */
    protected android.content.DialogInterface.OnClickListener m_DialogClickListener = null;
    /** 라디오버튼 클릭 이벤트 */
    protected OnMultiChoiceClickListener m_MultiChoiceClickListener;

    /***
     * 생성자
     * 
     * @param context
     *            컨텍스트
     * @param res
     *            리소스 이름
     */
    public ParentPopup(Context context, int res, PopupEventListener l) {
        m_Context = context;
        m_Res = res;
        m_PopupListener = l;
        createPopupUI();
        init();
    }

    /***
     * 팝업 창 UI 설정 메소드
     */
    private void createPopupUI() {
        LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_CurrentView = inflater.inflate(m_Res, null, false);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) m_Context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        if (m_nDefaultWidth == -1) {
            m_nDefaultWidth = metrics.widthPixels;
        }
        if (m_nDefaultHeight == -1) {
            m_nDefaultHeight = metrics.heightPixels / 2;
        }

        m_Dialog = new Dialog(m_Context);
        m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        m_Dialog.setCanceledOnTouchOutside(false);
        setContentView(0, null);
        m_Dialog.setContentView(m_CurrentView, new LinearLayout.LayoutParams(m_nDefaultWidth, 194 + m_nDefaultHeight));
    }

    /***
     * 팝업 타이틀 설정 메소드
     * 
     * @param title
     *            타이틀
     */
    protected void setTitle(String title) {
        RelativeLayout titleView = (RelativeLayout) m_CurrentView.findViewById(R.id.popup_view_title);
        TextView textView = new TextView(m_Context);
        textView.setText(title);
        textView.setPadding(50, 0, 0, 0);
        textView.setTextColor(Color.BLACK);
        titleView.addView(textView);
        titleView.setBackgroundColor(Color.GRAY);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(m_nDefaultWidth, 97));
    }

    protected void setTitle(int drawbale, String title) {
        LinearLayout titleView = (LinearLayout) m_CurrentView.findViewById(R.id.popup_view_title);
        ImageView img = new ImageView(m_Context);
        img.setBackgroundResource(drawbale);
        img.setLayoutParams(new LayoutParams(200, 150));
        img.setPadding(50, 0, 0, 0);

        titleView.addView(img);
        TextView textView = new TextView(m_Context);
        textView.setText(title);
        textView.setPadding(250, 30, 0, 0);
        textView.setTextColor(Color.WHITE);
        titleView.addView(textView);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(m_nDefaultWidth, 97));
    }

    /**
     * 컨텐츠 뷰 설정 메소드
     * 
     * @param flag
     *            구분 값
     * @param height
     *            컨텐츠 뷰 사이즈
     * @param obj
     *            데이타
     */
    protected void setContentView(int flag, Object obj) {
        switch (flag) {
        case TYPE_BASIC_POPUP:
            String msg = (String) obj;
            RelativeLayout contentView = (RelativeLayout) m_CurrentView.findViewById(R.id.popup_view_content);
            ScrollView scrollview = new ScrollView(m_Context);
            scrollview.setMinimumWidth(m_nDefaultWidth - 100);
            scrollview.setMinimumHeight(m_nDefaultHeight - 100);
            scrollview.setPadding(50, 50, 100, 50);
            TextView textView = new TextView(m_Context);
            textView.setText(msg);
            textView.setTextColor(Color.BLACK);
            scrollview.addView(textView);
            contentView.addView(scrollview);
            contentView.setLayoutParams(new LinearLayout.LayoutParams(m_nDefaultWidth, m_nDefaultHeight));
            contentView.setBackgroundColor(Color.WHITE);
            break;
        case TYPE_LIST_POPUP:
            break;
        case TYPE_RADIO_POPUP:
            break;
        }
        m_Dialog.setContentView(m_CurrentView, new LayoutParams(m_nDefaultWidth, 194 + m_nDefaultHeight));
    }

    /***
     * 팝업 버튼 설정 메소드
     * 
     * @param Btnsize
     *            버튼 사이즈
     * @param btnNames
     * @param btnIDs
     */

    protected void setButton(String[] btnNames, int[] btnIDs, OnClickListener l) {
        m_ClickListener = l;
        LinearLayout buttonView = (LinearLayout) m_CurrentView.findViewById(R.id.popup_view_button);
        buttonView.setBackgroundColor(Color.WHITE);
        int btnSize = btnNames.length;
        int btnWidth = m_nDefaultWidth / btnSize;
        int btnHeight = m_nDefaultHeight / btnSize;
        for (int i = 0; i < btnSize; i++) {
            Button button = new Button(m_Context);
            button.setWidth(btnWidth);
            button.setHeight(btnHeight);
            button.setId(btnIDs[i]);
            button.setOnClickListener((OnClickListener) m_ClickListener);
            button.setText(btnNames[i]);
            buttonView.addView(button);
        }
    }

    /***
     * 팝업 초기화 메소드
     */
    protected abstract void init();

    /**
     * 팝업 초기화 메소드
     * 
     * @param obj
     */
    public void initPopup(Object obj) {
    }

    /**
     * 팝업 데이타 변경 메소드
     * 
     * @param obj
     */
    public void setPopupData(Object obj) {
    }

    /***
     * 팝업 열기 메소드
     */
    public void setOpnePopup() {
        if (m_Dialog != null) {
            m_Dialog.show();
        }
    }

    /***
     * 팝업 취소 여부 설정 메소드
     * 
     * @param isCancleable
     *            팝업 취소 - true, 팝업 취소 불가 - false
     */
    public void setCancleable(boolean isCancleable) {
        m_bCancelable = isCancleable;
        m_Dialog.setCancelable(m_bCancelable);
    }

    /***
     * 팝업 닫기 메소드
     */
    public void closePopup() {
        if (m_Dialog != null && m_Dialog.isShowing()) {
            System.out.println("@@ closePopup");
            m_Dialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (m_Dialog != null) {
            return m_Dialog.isShowing();
        } else {
            return false;
        }

    }

    /**
     * 팝업 뒤로가기 버튼 선택시 호출 메소드
     */
    public void onPopupBackPressed() {
        closePopup();
    }

    /***
     * 팝업 메모리 정리 메소드
     */
    protected void dispose() {
        if (m_CurrentView != null) {
            m_CurrentView = null;
        }
        m_Context = null;
        m_Res = 0;
        if (m_Dialog != null) {
            m_Dialog.dismiss();
            m_Dialog = null;
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            onPopupBackPressed();
        }
        return true;
    }

    /***
     * 팝업 사이즈 조정 메소드
     * 
     * @param width
     *            가로 사이즈
     * @param height
     *            세로 사이즈
     */
    public void setPopupSize(int width, int height) {
        m_nDefaultWidth = width;
        m_nDefaultHeight = height;
    }

    /***
     * 팝업 배경 이미지 설정 메소드
     */
    public void setPopupBacgroundImg(int id) {
        m_CurrentView.findViewById(R.id.popup_bg).setBackgroundResource(id);
    }

}
