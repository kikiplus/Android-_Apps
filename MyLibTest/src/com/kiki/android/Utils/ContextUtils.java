package com.kiki.android.Utils;

/***
 * @author grapegirl
 * @version 1.0
 * @Class Name : ContextUtils
 * @Description : 앱 관리 정보
 * @since 2015-08-21.
 */
public class ContextUtils {

    /**
     * 운영 배포시 false
     * 개발 로그 true시 보임
     */
    public static boolean VIEW_LOG = true;

    /**
     * 프리퍼런스 이름
     */
    public static final String KEY_PREFER_NAME = "APP_PRF_NAME";

    /**
     * 프리퍼런스 ( 언어 )
     */
    public static final String KEY_LANGAUGE = "KEY_LANGAUGE";

    /**
     * GCM 프로젝트 아이디
     */
    public static String KEY_PROJECT_ID = "";

    /**
     * GCM REGID
     */
    public static final String KEY_GCM_REGID = "KEY_GCM_REGID";
}