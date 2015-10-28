package com.kiki.android.Logic.Managers.socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.kiki.android.Listener.INetException;
import com.kiki.android.Listener.IServerResponse;
import com.kiki.android.Utils.KLog;


/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : SocketManager
 * @Description : 클라이언트 Socket 통신 클래스
 * @since 2015. 1. 6.
 */
public class SocketManager implements Runnable {

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
    public SocketManager(String msg, IServerResponse listener, INetException exceptListener) {
        m_msg = msg;
        m_listener = listener;
        m_exceptionListenr = exceptListener;
    }

    @Override
    public void run() {
        try {
            KLog.d(this.getClass().getSimpleName(), "C: Connecting...");
            m_clientSocket = new Socket(m_serverIp, m_serverPort);
            try {
                Log.d("TCP", "C: Sending: '" + m_msg + "'");
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        m_clientSocket.getOutputStream())), true);
                out.println(m_msg);
                Log.d("TCP", "C: Sent.");
                Log.d("TCP", "C: Done.");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(m_clientSocket.getInputStream()));
                m_serverMsg = in.readLine();
                Log.d("TCP", "C: Server send to me this message -->" + m_serverMsg);
                m_listener.getServerResponse(m_serverMsg);
            } catch (Exception e) {
                Log.d("TCP", e.toString());
            } finally {
                m_clientSocket.close();
            }
        } catch (Exception e) {
            m_exceptionListenr.getExceptionString(e.toString());
        }

    }

    public String getServerResponse() {
        return m_serverMsg;
    }
}
