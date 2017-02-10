package com.kikiplus.View.UIComfonent;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.kikiplus.android.Listener.UIEvent.OnPopupEventListener;


/***
 * @author grape gir
 * @version 1.0
 * @Class Name : Dialog
 * @Description : 다이얼로그
 * @since 2015. 1. 5.
 */
public abstract class CustomDialog implements View.OnClickListener, DialogInterface.OnKeyListener
{

    /**
     * 다이얼로그
     */
    protected static Dialog mDialog = null;

    /**
     * 컨텍스트
     */
    protected Context mContext = null;

    /**
     * 다이얼로그 뷰
     */
    protected View mDialogView = null;

    /**
     * 다이얼로그 버튼리스너
     */
    protected OnPopupEventListener mPopupEventListener = null;

    /**
     * 다이얼로그 구분 값
     */
    protected int mPopId;

    /***
     * 팝업창 취소 여부
     */
    protected boolean mCancleable = false;


    /**
     * 생성자 - 다이얼로그 생성
     *
     * @param context
     * @param title
     * @param contentView
     */
    public CustomDialog(Context context, String title, int contentView, OnPopupEventListener popupEventListener, boolean isCancle, int popId)
    {
        mContext = context;
        mPopupEventListener = popupEventListener;
        mPopId = popId;
        mCancleable = isCancle;

        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(mCancleable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(this);
        //뷰 생성
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        mDialogView = inflater.inflate(contentView, null);
        mDialog.setContentView(mDialogView);
        initDialog();
    }

    /**
     * 다이얼로그 초기화 메소드
     */
    protected abstract void initDialog();

    /**
     * 다이얼로그 해제 메소드
     */
    protected abstract void destroyDialog();

    public static Dialog getDialog()
    {
        return mDialog;
    }

    /**
     * 다이얼로그 열기
     */
    public void showDialog()
    {
        if (mDialog != null)
        {
            mDialog.show();
        }
    }

    /**
     * 다이얼로그 닫기
     */
    public void closeDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
            destroyDialog();
        }
    }

    /**
     * 주변 터치 영역 선택시 닫기 설정 메소드
     *
     * @param flag 선택 허용-true, 선택 불가-false
     */
    public void setCanceledOnTouchOutside(boolean flag)
    {
        mDialog.setCanceledOnTouchOutside(flag);
    }

    /**
     * 백 키 선택 메소드
     */
    public void onPopupBackPressed()
    {
        if (mCancleable)
        {
            mPopupEventListener.onPopupAction(mPopId, mPopupEventListener.POPUP_BACK, null);
        } else
        {
            mPopupEventListener.onPopupAction(mPopId, mPopupEventListener.POPUP_BTN_OK, null);
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
    {
        if (keyCode == event.KEYCODE_BACK)
        {
            onPopupBackPressed();
        }
        return false;
    }

}
