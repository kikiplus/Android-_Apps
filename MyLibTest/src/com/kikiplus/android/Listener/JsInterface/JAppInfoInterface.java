package com.kikiplus.android.Listener.JsInterface;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.kikiplus.android.andUtils.AppUtils;

/***
 * @author grape girl
 * @version 1.0
 * @Class Name : JMenuInterface
 * @Description : 앱 정보 인터페이스 클래스
 * @since 2015-08-21.
 */
public class JAppInfoInterface {


    /**
     * 스크립트 콜백 리스너
     */
    private IScriptReceive mIScriptReceive;

    /**
     * 컨텍스트
     */
    private Context mContext;

    /**
     * 생성자
     *
     * @param scriptReceive 자바스크립트 콜백 리스너
     */
    public JAppInfoInterface(Context context, IScriptReceive scriptReceive) {
        mContext = context;
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    /**
     * 앱 버전 코드 반환 메소드(int형)
     */
    public int getAppVersionCode() {
        return AppUtils.getVersionCode(mContext);
    }

    @JavascriptInterface
    /**
     * 앱 버전 네임 반환 메소드(String형)
     */
    public String getAppVersionName() {
        return AppUtils.getVersionName(mContext);
    }

    @JavascriptInterface
    /**
     * 사용자단말기 설정되어 있는 언어 설정값 반환
     */
    public String getUserPhoneLanguage() {
        return AppUtils.getUserPhoneLanuage(mContext);
    }
}
