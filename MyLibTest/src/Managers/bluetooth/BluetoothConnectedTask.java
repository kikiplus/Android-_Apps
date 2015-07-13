package Managers.bluetooth;

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
 * @Description : ������� ��� ��� ��û ���� Ŭ����
 * @since 2015-06-26.
 */
public class BluetoothConnectedTask extends AsyncTask<Void, Void, Void> {

    /**
     * ����
     */
    private final BluetoothSocket mSocket;

    /**
     * ������� ��� �̺�Ʈ ������
     */
    private IBLConnectLinstener mBlListener = null;

    /**
     * ������� ��ǲ��Ʈ��
     */
    private final InputStream mInStream;

    /**
     * ������� �ƿ�ǲ ��Ʈ��
     */
    private final OutputStream mOutStream;


    /**
     * ������
     *
     * @param bluetoothSocket
     * @param mBlConnectionListener
     */
    public BluetoothConnectedTask(BluetoothSocket bluetoothSocket, IBLConnectLinstener mBlConnectionListener) {
        System.out.println("@@@ BluetoothConnectedTask ������ ");
        mSocket = bluetoothSocket;
        mBlListener = mBlConnectionListener;

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("@@ BluetoothConnectedTask ������ IOException");
        }
        mInStream = inputStream;
        mOutStream = outputStream;
        System.out.println("@@@ BluetoothConnectedTask ������ ��");
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BluetoothConnectedTask doInBackground ����");
        byte[] buffer = new byte[1024];
        int bytes;

        // Keep listening to the InputStream while connected
        while (true) {
            try {
                // Read from the InputStream
                bytes = mInStream.read(buffer);

                // Send the obtained bytes to the UI Activity
                mBlListener.onReceiveAction(mBlListener.RECEIVE_OK, bytes);
            } catch (IOException e) {
                System.out.println("@@@ BluetoothConnectedTask doInBackground IOException");
                break;
            }
        }
        System.out.println("@@@ BluetoothConnectedTask doInBackground ��");
        return null;
    }


    /**
     * ����� ��Ʈ������ ����
     */
    public void write(byte[] buffer) {
        try {
            System.out.println("@@@ BluetoothConnectedTask write buffer : " + buffer);
            System.out.println("@@@ BluetoothConnectedTask write mOutStream : " + mOutStream);
            mOutStream.write(buffer);

            // Share the sent message back to the UI Activity
            mBlListener.onReceiveAction(mBlListener.SEND_USER, buffer);
        } catch (IOException e) {
            System.out.println("@@@ BluetoothConnectedTask write IOException");
        }
    }

    /**
     * �ƿ�ǲ��Ʈ�� ���� ���� ��ȯ
     * @return ���°�
     */
    public  boolean getOutputStream(){
        return mOutStream != null ? true : false;
    }

    /**
     * ��ǲ��Ʈ�� ���� ���� ��ȯ
     * @return ���°�
     */
    public boolean getInputStream(){
        return mInStream != null ? true : false;
    }
    /**
     * Ŭ���̾�Ʈ ���� close �޼ҵ�
     */
    public void cancle() {
        try {
            mSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }
}
