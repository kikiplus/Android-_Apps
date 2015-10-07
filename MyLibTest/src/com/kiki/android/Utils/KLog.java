package com.kiki.android.Utils;

/***
 * @author grapegirl
 * @version 1.0
 * @Class Name : KLog.java
 * @Description : Log 클래스
 * @since 2015.10.07
 */
public class KLog {

    /**
     * 로그 debug
     *
     * @param tag 로그 출력 클래스명
     * @param msg 로그 내용
     */
    public static void d(String tag, String msg) {
        if (!ContextUtils.VIEW_LOG)
            return;

        android.util.Log.d(tag, msg);
    }

    /**
     * 로그 error
     *
     * @param tag 로그 출력 클래스명
     * @param msg 로그 내용
     */
    public static void e(String tag, String msg) {
        if (!ContextUtils.VIEW_LOG)
            return;

        android.util.Log.e(tag, msg);
    }

    /**
     * 로그 warning
     *
     * @param tag 로그 출력 클래스명
     * @param msg 로그 내용
     */
    public static void w(String tag, String msg) {
        if (!ContextUtils.VIEW_LOG)
            return;

        android.util.Log.w(tag, msg);
    }

    /**
     * 로그 info
     *
     * @param tag 로그 출력 클래스명
     * @param msg 로그 내용
     */
    public static void i(String tag, String msg) {
        if (!ContextUtils.VIEW_LOG)
            return;

        android.util.Log.i(tag, msg);
    }

    /**
     * 로그 verbose
     *
     * @param tag 로그 출력 클래스명
     * @param msg 로그 내용
     */
    public static void v(String tag, String msg) {
        if (!ContextUtils.VIEW_LOG)
            return;

        android.util.Log.v(tag, msg);
    }

}
