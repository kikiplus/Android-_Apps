package com.kiki.android.Listener.JsInterface;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.kiki.android.Utils.SharedPreferenceUtils;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JSharedPreferInterface
 * @Description : 프레퍼런스 값 공유 인터페이스
 * @since 2015-09-03.
 */
public class JSharedPreferInterface {

    /** 스크립트 콜백 리스너*/
    private IScriptReceive mIScriptReceive;

    /**
     * 컨텍스트
     */
    private Context mContext;

    /**
     * 생성자
     * @param scriptReceive 자바스크립트 콜백 리스너
     */
    public JSharedPreferInterface(Context context, IScriptReceive scriptReceive){
        mContext = context;
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    /**
     * 프레퍼런스 값 설정 메소드
     * @param keyName 프레퍼런스 저장 키 값
     * @param value 프레퍼런스 저장할 값
     */
    public void setPreferenceValue(String keyName, String value) {
        SharedPreferenceUtils.write(mContext, keyName, value);
    }


    @JavascriptInterface
    /**
     * 프레퍼런스 값 반환 메소드
     * @param keyName 프레퍼런스 저장 키 값
     * @return 프레퍼런스 저장한 값
     */
    public String getPreferenceValue(String keyName) {
        return  (String) SharedPreferenceUtils.read(mContext, keyName, SharedPreferenceUtils.SHARED_PREF_VALUE_STRING);
    }
}
