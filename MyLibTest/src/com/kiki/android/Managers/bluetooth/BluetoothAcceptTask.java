package com.kiki.android.Managers.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

import com.kiki.android.Listener.IBLConnectLinstener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BluetoothConnectTask
 * @Description : 블루투스 기기 등록 요청 수락 클래스
 * @since 2015-06-26.
 */
public class BluetoothAcceptTask extends AsyncTask<Void, Void, Void> {

    /**
     * 서버소켓
     */
    private final BluetoothServerSocket mServerSocket;

    /**
     * 이름
     */
    private static final String SERVER_NAME = "BluetoothChat";

    /**
     * UUID
     */
    private static final UUID SERVER_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    /**
     * 블루투스 통신 이벤트 리스너
     */
    private IBLConnectLinstener mBlListener = null;


    /**
     * 블루투스 연결 Task
     */
    private BluetoothConnectedTask mConnectedThread;
    /**
     * 상태
     */
    private int mState = -1;

    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    /**
     * 생성자
     *
     * @param bluetoothAdapter
     * @param mBlConnectionListener
     */
    public BluetoothAcceptTask(BluetoothAdapter bluetoothAdapter, IBLConnectLinstener mBlConnectionListener) {
        System.out.println("@@@ BluetoothAcceptTask 생성자 ");
        mBlListener = mBlConnectionListener;
        BluetoothServerSocket serverSocket = null;
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVER_NAME, SERVER_UUID);
        } catch (IOException e) {
            System.out.println("@@ BluetoothAcceptTask 생성자 IOException");
        }
        mServerSocket = serverSocket;
        mState = STATE_NONE;
        System.out.println("@@@ BluetoothAcceptTask 생성자 끝");
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BluetoothAcceptTask doInBackground 시작 ");
        BluetoothSocket socket = null;

        // Listen to the server socket if we're not connected
//        while (mState != STATE_CONNECTED) {
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                socket = mServerSocket.accept();
            } catch (IOException e) {
                System.out.println("@@@ BluetoothAcceptTask doInBackground  IOException");
//                break;
            }

            // If a connection was accepted
//            if (socket != null) {
//                switch (mState) {
//                    case STATE_LISTEN:
//                    case STATE_CONNECTING:
//                        // Situation normal. Start the connected thread.
                        connected(socket, socket.getRemoteDevice());
//                        break;
//                    case STATE_NONE:
//                    case STATE_CONNECTED:
//                        // Either not ready or already connected. Terminate new socket.
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            System.out.println("@@@ BluetoothAcceptTask doInBackground  IOException 2");
//                        }
//                        break;
//                }
//            }
//        }
        System.out.println("@@@ BluetoothAcceptTask doInBackground 끝 ");
        return null;
    }


    private void connected(BluetoothSocket socket, BluetoothDevice
            device) {
        mConnectedThread = new BluetoothConnectedTask(socket, mBlListener);
        mState = STATE_CONNECTED;

    }


    /**
     * 클라이언트 소켓 close 메소드
     */
    public void cancle() {
        try {
            mServerSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }

    /**
     * 상태 변경 메소드
     * @param sts 상태
     */
    public void setStats(int sts){
        mState = sts;
    }
}
