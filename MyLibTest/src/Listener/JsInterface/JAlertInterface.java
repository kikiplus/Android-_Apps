package Listener.JsInterface;


import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : JAlertInterface
 * @Description : 자바스크립트 팝업 인터페이스
 * @since 2015-08-03.
 */
public class JAlertInterface{

    /** 컨텍스트*/
    private Context mContext;

    /**
     * 생성자
     * @param context 컨텍스트
     */
    public JAlertInterface(Context context){
        mContext = context;
    }

    @JavascriptInterface
    /**
     * 팝업 표시
     */
    public void setMessage(String arg) { // must be final
        Toast.makeText(mContext, arg , Toast.LENGTH_LONG).show();
    }

}
