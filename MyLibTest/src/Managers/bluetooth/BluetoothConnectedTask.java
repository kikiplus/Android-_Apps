package Managers.bluetooth;

import android.accounts.OnAccountsUpdateListener;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Interface.IBLConnectLinstener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BluetoothConnectTask
 * @Description : 블루투스 기기 등록 요청 수락 클래스
 * @since 2015-06-26.
 */
public class BluetoothConnectedTask extends AsyncTask<Void, Void, Void> {

    /**
     * 소켓
     */
    private final BluetoothSocket mSocket;

    /**
     * 블루투스 통신 이벤트 리스너
     */
    private IBLConnectLinstener mBlListener = null;

    /**
     * 블루투스 인풋스트림
     */
    private final InputStream mInStream;

    /**
     * 블루투스 아웃풋 스트림
     */
    private final OutputStream mOutStream;


    /**
     * 생성자
     *
     * @param bluetoothSocket
     * @param mBlConnectionListener
     */
    public BluetoothConnectedTask(BluetoothSocket bluetoothSocket, IBLConnectLinstener mBlConnectionListener) {
        System.out.println("@@@ BluetoothConnectedTask 생성자 ");
        mSocket = bluetoothSocket;
        mBlListener = mBlConnectionListener;

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("@@ BluetoothConnectedTask 생성자 IOException");
        }
        mInStream = inputStream;
        mOutStream = outputStream;
        System.out.println("@@@ BluetoothConnectedTask 생성자 끝");
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BluetoothConnectedTask doInBackground 시작");
        byte[] buffer = new byte[1024];
        int bytes;

        // Keep listening to the InputStream while connected
        while (true) {
            try {
                // Read from the InputStream
                bytes = mInStream.read(buffer);

                // Send the obtained bytes to the UI Activity
            } catch (IOException e) {
                System.out.println("@@@ BluetoothConnectedTask doInBackground IOException");
                break;
            }
        }
        mBlListener.onReceiveAction(mBlListener.RECEIVE_OK, null);
        System.out.println("@@@ BluetoothConnectedTask doInBackground 끝");
        return null;
    }


    /**
     * 연결된 스트림으로 쓰기
     */
    public void write(byte[] buffer) {
        OutputStream outputStream;
        try {
            outputStream = mSocket.getOutputStream();
            System.out.println("@@@ BluetoothConnectedTask write buffer : " + buffer);
            System.out.println("@@@ BluetoothConnectedTask write mOutStream : " + outputStream);

            buffer[buffer.length-1] = 0;
            System.out.println("@@@ BluetoothConnectedTask write buffer 2: " + buffer.length);


            outputStream.write(buffer);
            System.out.println("@@@ BluetoothConnectedTask write mOutStream 3: " + outputStream);
            // Share the sent message back to the UI Activity
            mBlListener.onReceiveAction(mBlListener.SEND_OK, "전송완료");
        } catch (IOException e) {
            System.out.println("@@@ BluetoothConnectedTask write IOException");
        }
    }

    /**
     * 아웃풋스트림 존재 여부 반환
     * @return 상태값
     */
    public  boolean getOutputStream(){
        return mOutStream != null ? true : false;
    }

    /**
     * 인풋스트림 존재 여부 반환
     * @return 상태값
     */
    public boolean getInputStream(){
        return mInStream != null ? true : false;
    }
    /**
     * 클라이언트 소켓 close 메소드
     */
    public void cancle() {
        try {
            mSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }
}
