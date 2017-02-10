package com.kikiplus.View.Activity.TestA;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kikiplus.View.R;
import com.kikiplus.android.Listener.INetException;
import com.kikiplus.android.Listener.IServerResponse;
import com.kikiplus.android.Managers.socket.SocketAsynTaskManager;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : MainActivity
 * @Description : 메인 화면 클래스
 * @since 2015. 1. 6.
 */
public class SocketTestMainActivity extends Activity implements OnClickListener, Handler.Callback
        , IServerResponse, INetException {

    /**
     * 서버로부터 전송할 내용 입력 박스
     */
    private EditText mEditText = null;

    /**
     * 전송 버튼
     */
    private Button mSendBtn = null;

    /**
     * 서버와의 통신 내역
     */
    private TextView mTextView = null;

    /**
     * 통신 내역 저장 변수
     */
    private String mTextString = "";

    /**
     * 클라이언트 변수
     */
    private SocketAsynTaskManager mClient = null;

    /**
     * 핸들러
     */
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_main_layout);
        mEditText = (EditText) findViewById(R.id.editText1);
        mTextView = (TextView) findViewById(R.id.textView1);
        mSendBtn = (Button) findViewById(R.id.button1);
        mSendBtn.setOnClickListener(this);
        mHandler = new Handler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:// 전송 버튼 선택 시
                String str = mEditText.getText().toString();
                mTextString += "C: " + str + "\n";
                mEditText.setText(null);
                mTextView.setText(mTextString);

                mClient = new SocketAsynTaskManager(str, this, this);
                mClient.execute();
                break;
        }
    }

    @Override
    public void getServerResponse(String str) {
        mHandler.sendMessage(mHandler.obtainMessage(0, str));
    }

    @Override
    public void getExceptionString(String str) {
        mHandler.sendMessage(mHandler.obtainMessage(0, str));
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:// UI 업데이트
                String str1 = (String) msg.obj;
                mTextString += "S: " + str1 + "\n";
                mTextView.setText(mTextString);
                break;
        }
        return false;
    }
}
