package com.kikiplus.android.Managers.net;

import android.os.AsyncTask;

import com.kikiplus.android.Managers.push.SafetyUnRegistrationService;
import com.kikiplus.android.Utils.KLog;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : SocketManger
 * @Description : 클라이언트 Socket 통신 클래스
 * @since 2017-02-11
 */
public class SocketManger extends AsyncTask<Void, Void, Void> {

    private final String TAG = this.getClass().getSimpleName();
    private static final String mServerIp = "127.0.0.1";
    private static final int mServerPort = 1000;
    private String mSendMsg = null;
    private Socket mClientSocket = null;
    private INetReceive mInetReceive = null;

    public SocketManger(String msg, INetReceive listener) {
        mSendMsg = msg;
        mInetReceive = listener;
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
            mClientSocket = new Socket(mServerIp, mServerPort);

            KLog.d("TCP", "C: Sending: '" + mSendMsg + "'");
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    mClientSocket.getOutputStream())), true);
            out.println(mSendMsg);
            KLog.d("TCP", "C: Sent.");
            KLog.d("TCP", "C: Done.");

            InputStream inputstream = mClientSocket.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

            byte[] buffer = new byte[1024];
            int byteRead;

            while ((byteRead = inputstream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, byteRead);
                String serverMsg = byteArrayOutputStream.toString("UTF-8");
                KLog.d("TCP", "C: Server send to me this message -->" + serverMsg);
                mInetReceive.onNetReceive(INetReceive.NET_OK, 0, serverMsg);
            }
            mClientSocket.close();
        } catch (Exception e) {
            KLog.d("TCP", e.toString());
            mInetReceive.onNetReceive(INetReceive.NET_FAIL, 0, e.toString());
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
