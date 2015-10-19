package com.kiki.View.Activity.TestA;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.kiki.View.R;
import com.kiki.android.Utils.MemoryUtils;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : TutorialActivity
 * @Description : 뷰 플리퍼를 이용하여 튜토리얼
 * @since 2015-06-30.
 */
public class TutorialActivity extends Activity implements View.OnTouchListener {

    /**
     * 화면 전환 객체
     */
    private ViewFlipper mViewFlipper;

    /**
     * 화면 전환을 위한 터치 X값
     */
    private int mPreTouchPosX = 0;

    /**
     * 현재 페이지
     */
    private int mCurrentPage = 0;

    /**
     * 최대 페이지
     */
    private int mMacPage = 0;

    /**
     * 라디오 아이템
     */
//    private int[] mCheckBox = {R.id.tutorial_main_checkbox1, R.id.tutorial_main_checkbox2, R.id.tutorial_main_checkbox3, R.id.tutorial_main_checkbox4, R.id.tutorial_main_checkbox5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tutorial_main_layout);

//        MemoryUtils.printMemory(this);
//        mViewFlipper = (ViewFlipper) findViewById(R.id.tutorial_main_viewFlipper);
//        mViewFlipper.setOnTouchListener(this);
//        mCurrentPage = 0;
//        mMacPage = 5;
//        setViewImgData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewFlipper.removeAllViews();
        MemoryUtils.printMemory(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPreTouchPosX = (int) event.getX();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int nTouchPosX = (int) event.getX();

            if (nTouchPosX < mPreTouchPosX) {
                MoveNextView();
            } else if (nTouchPosX > mPreTouchPosX) {
                MovePreviousView();
            }
            mPreTouchPosX = nTouchPosX;
        }
        return true;
    }

    /**
     * 다음 화면 호출 메소드
     */
    private void MoveNextView() {
        if (mCurrentPage + 1 < mMacPage) {
            mCurrentPage++;
//            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.apper_from_right));
//            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.disapper_from_left));
            mViewFlipper.showNext();
            setPageCheck();
        }
    }

    /**
     * 이전 화면 호출 메소드
     */
    private void MovePreviousView() {
        if (mCurrentPage - 1 >= 0) {
            mCurrentPage--;
//            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.apper_from_left));
//            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.disapper_from_right));
            mViewFlipper.showPrevious();
            setPageCheck();
        }
    }

    /**
     * 현재 페이지 라디오 버튼 설정 메소드
     */
    private void setPageCheck(){
        for(int i=0; i<mMacPage; i++){
//            CheckBox checkBox = (CheckBox)findViewById(mCheckBox[i]);
//            if(i == mCurrentPage){
//                checkBox.setChecked(true);
//            }else{
//                checkBox.setChecked(false);
//            }
        }
    }
    /**
     * 화면 설정 메소드
     */
    public void setViewImgData() {
        for (int i = 0; i < mMacPage; i++) {
            int resource = getImgResource(i);
            ImageView img = new ImageView(this);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inSampleSize = 1;
                Bitmap src = BitmapFactory.decodeResource(getResources(), resource, options);
                Bitmap resize = Bitmap.createScaledBitmap(src, options.outWidth, options.outHeight, true);
                img.setImageBitmap(resize);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mViewFlipper.addView(img);
        }
        setPageCheck();
    }

    /**
     * 이미지 리소스 반환 메소드
     *
     * @return 이미지 리소스 id
     */
    public int getImgResource(int index) {
        int resId = -1;
        switch (index) {
            case 0:
                resId = R.drawable.tutorial01;
                break;
            case 1:
                resId = R.drawable.tutorial01;
                break;
            case 2:
                resId = R.drawable.tutorial01;
                break;
            case 3:
                resId = R.drawable.tutorial01;
                break;
            case 4:
                resId = R.drawable.tutorial01;
                break;
        }
        return resId;
    }
}
