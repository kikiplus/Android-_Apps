package Listener.JsInterface;

import android.webkit.JavascriptInterface;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JLoadUrlInterface
 * @Description : 자바스크립트 페이지 이동 인터페이스
 * @since 2015-08-03.
 */
public class JLoadUrlInterface {

    /** 스크립트 콜백 리스너*/
    private IScriptReceive mIScriptReceive;

    /**
     * 생성자
     * @param scriptReceive 자바스크립트 콜백 리스너
     */
    public JLoadUrlInterface(IScriptReceive scriptReceive){
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    /**
     * 페이지 이동
     */
    public void loadURL() { // must be final
        mIScriptReceive.onScriptReceive(IScriptReceive.LOCATION_CONF_DISPLAY, null );
    }

    @JavascriptInterface
    /**
     * 페이지 이동
     * @param url 이동할 url 주소
     */
    public void loadHomePageURL(String url) { // must be final
        mIScriptReceive.onScriptReceive(IScriptReceive.LOAD_PAGE_URL, url );
    }
}
