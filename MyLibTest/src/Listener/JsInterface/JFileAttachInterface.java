package Listener.JsInterface;

import android.webkit.JavascriptInterface;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JFileAttachInterface
 * @Description : 자바스크립트 파일 첨부 인터페이스
 * @since 2015-08-03.
 */
public class JFileAttachInterface {

    /** 스크립트 콜백 리스너*/
    private IScriptReceive mIScriptReceive;

    /**
     * 생성자
     * @param scriptReceive 자바스크립트 콜백 리스너
     */
    public JFileAttachInterface(IScriptReceive scriptReceive){
        mIScriptReceive = scriptReceive;
    }

    @JavascriptInterface
    /**
     * 파일 선택
     */
    public void sendFile() {
        mIScriptReceive.onScriptReceive(mIScriptReceive.SEND_FILE_ATTACKED, null);
    }
}