package com.kikiplus.View.Activity.TestA;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kikiplus.android.Managers.net.UrlManager;
import com.kikiplus.android.Managers.net.IHttpReceive;
import com.kikiplus.android.Utils.StringUtils;

import java.util.HashMap;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : HTTPUrlConnectionActivity
 * @Description : HTTP 통신
 * @since 2015-06-30.
 */
public class HTTPUrlConnectionActivity extends Activity implements View.OnClickListener, IHttpReceive, Handler.Callback {

    /**
     * 정보 불러오기 버튼
     */
    private Button mButton = null;

    /**
     * 텍스트뷰
     */
    private TextView mTextView = null;

    /**
     * 핸들러
     */
    private Handler mHandler = null;

    /**
     * 프로그래스바
     */
    private ProgressBar mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.http_main_layout);
//
//        //핸들러 및 프로그래스바 설정
//        mHandler = new Handler(this);
//        mProgressDialog = new ProgressBar(this);
//        // 버튼, 텍스트 뷰 설정
//        mButton = (Button) findViewById(R.id.http_main_button);
//        mButton.setOnClickListener(this);
//        mTextView = (TextView) findViewById(R.id.http_main_textview);

    }

    @Override
    public void onClick(View v) {
        mTextView.setText(null);
        mProgressDialog.setDataLoadingDialog(true, "데이타를 불러오고 있습니다");

//        UrlManager manager = new UrlManager("https://github.com", false, this);
//        manager.execute();
        UrlManager manager = new UrlManager("http://210.220.248.236:8080/MemoServer/jsp/login.jsp", true, this , 0);

        HashMap<String, Object> data = new HashMap<String,Object>();
        data.put("name","mihye");
        data.put("title","title");
        data.put("content","content");
        manager.execute(StringUtils.getHTTPPostSendData(data));
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0://데이타 설정
                String data = (String) msg.obj;
                mTextView.setText(data);
                break;
        }
        return false;
    }

    @Override
    public void onHttpReceive(int type, int actionId, Object obj) {
        switch (type){
            case HTTP_OK:
                mProgressDialog.setDataLoadingDialog(false, null);
                String data = (String) obj;
                mHandler.sendMessage(mHandler.obtainMessage(0, data));
                break;
        }
    }
}
