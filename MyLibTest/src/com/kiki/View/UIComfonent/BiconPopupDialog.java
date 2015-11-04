package com.kiki.View.UIComfonent;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiki.View.R;
import com.kiki.android.Listener.UIEvent.OnPopupEventListener;


/***
 * @author mh kim
 * @version 1.0
 * @Class Name : 이태원 방문 스타일 선택하는 Dialog
 * @Description : 다이얼로그
 * @since 2015. 9. 22.
 */
public class BiconPopupDialog extends CustomDialog {

    private Context mContext;

    private String mTitle = "알림";
    private String mContent;
    private String mImgURL;
    private String mLocationURL;

    private String mMessage;

    /**
     * 생성자 - 다이얼로그 생성
     *
     * @param context            컨텍스트
     * @param contentView        레이아웃 리소스 아이디
     * @param popupEventListener 팝업 이벤트 리스너
     * @param popId              팝업 구분 값
     */
    public BiconPopupDialog(Context context, String content, String imgUrl, String locationURl, String message, int contentView, OnPopupEventListener popupEventListener, int popId) {
        super(context, "", contentView, popupEventListener, true, popId);
        mContext = context;

        //비콘 알림
        mContent = content;
        //mTitle = content;
        mImgURL = imgUrl;
        mLocationURL = locationURl;

        //이태원스타일
        mMessage = message;
        setData();
    }

    @Override
    protected void initDialog() {
        mDialogView.findViewById(R.id.popup_location_btn).setOnClickListener(this);
        mDialogView.findViewById(R.id.popup_close_btn).setOnClickListener(this);
    }

    private void setData() {
        //날짜
//        ((TextView) mDialogView.findViewById(R.id.basic_date_textview)).setText(AppUtils.getDateFormatString(new Date()));

        //비콘 알림
        if (mMessage == null || mMessage.equals("")) {
            ((TextView) mDialogView.findViewById(R.id.basic_body_textView)).setText(mContent);
            ((TextView) mDialogView.findViewById(R.id.basic_title_textview)).setText(mTitle);
        } else {
            ((TextView) mDialogView.findViewById(R.id.basic_body_textView)).setText(mMessage);
            ((TextView) mDialogView.findViewById(R.id.basic_title_textview)).setText(mTitle);
        }


        if (mImgURL == null || mImgURL.equals("")) {
            mDialogView.findViewById(R.id.basic_body_imageView).setVisibility(View.GONE);
        } else {
            ImageView imgView = (ImageView) mDialogView.findViewById(R.id.basic_body_imageView);
//            new ImageLoaderTask(imgView, mImgURL).execute();
        }

        if (mLocationURL == null || mLocationURL.equals("")) {
            mDialogView.findViewById(R.id.poup_bicon_bottom).setVisibility(View.GONE);
        } else {
            mDialogView.findViewById(R.id.poup_bicon_bottom).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void destroyDialog() {
        mDialog = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_location_btn:
                mPopupEventListener.onPopupAction(mPopId, mPopupEventListener.POPUP_BTN_CLOSEE, mLocationURL);
                break;
            case R.id.popup_close_btn:
                mPopupEventListener.onPopupAction(mPopId, mPopupEventListener.POPUP_BTN_OK, mDialogView);
                break;
        }
    }
}
