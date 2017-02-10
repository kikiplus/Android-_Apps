package com.kikiplus.android.Managers.socket;

import android.os.AsyncTask;
import android.util.Log;

import com.kikiplus.android.Listener.INetException;
import com.kikiplus.android.Listener.IServerResponse;
import com.kikiplus.android.andUtils.KLog;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : SocketManager
 * @Description : 클라이언트 Socket 통신 클래스
 * @since 2015. 1. 6.
 */
public class SocketAsynTaskManager extends AsyncTask<Void, Void, Void> {

    /**
     * 서버로 전송할 메시지
     */
    private String m_msg = null;

    /**
     * 서버 아이피(앱 사용시에 아이피 변경해야함)
     */
    private static final String m_serverIp = "127.0.0.1";

    /**
     * 서버 포트
     */
    private static final int m_serverPort = 1000;

    /**
     * 클라이언트 소켓
     */
    private Socket m_clientSocket = null;

    /**
     * 서버로부터 응답 메시지
     */
    private String m_serverMsg = null;

    /**
     * 서버로부터 응답 결과를 받을 리스너
     */
    private IServerResponse m_listener = null;

    /**
     * 네트워크 결과를 받을 리스너
     */
    private INetException m_exceptionListenr = null;

    /**
     * 생성자
     *
     * @param msg            서버에 전송할 메시지
     * @param listener       서버의 응답을 받을 리스너
     * @param exceptListener 네트워크 결과 리스너
     */
    public SocketAsynTaskManager(String msg, IServerResponse listener, INetException exceptListener) {
        m_msg = msg;
        m_listener = listener;
        m_exceptionListenr = exceptListener;
    }

    public String getServerResponse() {
        return m_serverMsg;
    }

    @Override
    /***
     * doInBackground() 작업전 수행해야 되는 메소드
     */
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        KLog.d(this.getClass().getSimpleName(), "C: Connecting...");

        try {
            m_clientSocket = new Socket(m_serverIp, m_serverPort);

            Log.d("TCP", "C: Sending: '" + m_msg + "'");
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    m_clientSocket.getOutputStream())), true);
            out.println(m_msg);
            Log.d("TCP", "C: Sent.");
            Log.d("TCP", "C: Done.");

            InputStream inputstream = m_clientSocket.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

            byte[] buffer = new byte[1024];
            int byteRead ;

            while ((byteRead = inputstream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, byteRead);
                m_serverMsg = byteArrayOutputStream.toString("UTF-8");
                Log.d("TCP", "C: Server send to me this message -->" + m_serverMsg);
                m_listener.getServerResponse(m_serverMsg);
            }
            m_clientSocket.close();
        } catch (Exception e) {
            Log.d("TCP", e.toString());
        }
        return null;
    }

    @Override
    /***
     * doInBackground() 작업 휴 수행해야 되는 메소드
     */
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
