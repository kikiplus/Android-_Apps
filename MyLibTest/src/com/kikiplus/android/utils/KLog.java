package com.kikiplus.android.utils;

import android.util.Log;

/***
 * @author grapegirl
 * @version 1.0
 * @Class Name : KLog.java
 * @Description : Log 클래스
 * @since 2015.10.07
 */
public class KLog {

    /**
     * 운영 배포시 false
     * 개발 로그 true시 보임
     */
    public static boolean VIEW_LOG = true;

    public static void d(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.d(tag, buildLogMsg(msg));
    }

    public static void e(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.e(tag, buildLogMsg(msg));
    }

    public static void w(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.w(tag, buildLogMsg(msg));
    }

    public static void i(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.i(tag, buildLogMsg(msg));
    }

    public static void v(String tag, String msg) {
        if (!VIEW_LOG)
            return;

        Log.v(tag, buildLogMsg(msg));
    }

    public static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);
        return sb.toString();
    }
}
