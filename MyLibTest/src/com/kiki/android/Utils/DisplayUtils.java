package com.kiki.android.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : DisplayUtils
 * @Description : 화면 해상도 관련 유틸 클래스
 * @since 2015-08-03.
 */
public class DisplayUtils {

    /**
     * Dpi 반환 메소드
     *
     * @param context 컨텍스트
     * @return 현재 기기의 맞는 dpi 반환
     */
    public static int getDisplayDpi(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        return metrics.densityDpi;
    }

    /**
     * 160으로 나눈 dpi 반환 메소드
     *
     * @param context 컨텍스트
     * @return 현재 기기의 맞는 dpi/160 반환
     */
    public static float getDisplayScaledDpi(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        return metrics.scaledDensity;
    }
}
