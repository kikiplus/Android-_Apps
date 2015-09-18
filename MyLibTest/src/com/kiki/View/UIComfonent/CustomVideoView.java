package com.kiki.View.UIComfonent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.VideoView;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : CustomVideoView
 * @Description : 비디오뷰 플레이 커스텀 클래스
 * VideoView를 사용하여 동영상을 재생해보면 동영상 비율에 따라서 자동으로 View의 사이즈가 정해지는 것을 볼 수 있습니다.
 * 이러한 경우 VideoView를 상속받는 Class를 하나 만든 뒤에 onMeasure() 함수를 Override하여 직접 구현함
 * @since 2015-09-18
 */
public class CustomVideoView extends VideoView {

    /**
     * 생성자
     *
     * @param context 컨텍스트
     */
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            setMeasuredDimension(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
    }
}
