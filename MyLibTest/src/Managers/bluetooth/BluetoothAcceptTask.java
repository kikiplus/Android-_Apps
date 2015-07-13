package Managers.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

import Interface.IBLConnectLinstener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BluetoothConnectTask
 * @Description : ������� ��� ��� ��û ���� Ŭ����
 * @since 2015-06-26.
 */
public class BluetoothAcceptTask extends AsyncTask<Void, Void, Void> {

    /**
     * ��������
     */
    private final BluetoothServerSocket mServerSocket;

    /**
     * �̸�
     */
    private static final String SERVER_NAME = "BluetoothChat";

    /**
     * UUID
     */
    private static final UUID SERVER_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    /**
     * ������� ��� �̺�Ʈ ������
     */
    private IBLConnectLinstener mBlListener = null;


    /**
     * ������� ���� Task
     */
    private BluetoothConnectedTask mConnectedThread;
    /**
     * ����
     */
    private int mState = -1;

    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    /**
     * ������
     *
     * @param bluetoothAdapter
     * @param mBlConnectionListener
     */
    public BluetoothAcceptTask(BluetoothAdapter bluetoothAdapter, IBLConnectLinstener mBlConnectionListener) {
        System.out.println("@@@ BluetoothAcceptTask ������ ");
        mBlListener = mBlConnectionListener;
        BluetoothServerSocket serverSocket = null;
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVER_NAME, SERVER_UUID);
        } catch (IOException e) {
            System.out.println("@@ BluetoothAcceptTask ������ IOException");
        }
        mServerSocket = serverSocket;
        mState = STATE_NONE;
        System.out.println("@@@ BluetoothAcceptTask ������ ��");
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BluetoothAcceptTask doInBackground ���� ");
        BluetoothSocket socket = null;

        // Listen to the server socket if we're not connected
        while (mState != STATE_CONNECTED) {
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                socket = mServerSocket.accept();
            } catch (IOException e) {
                System.out.println("@@@ BluetoothAcceptTask doInBackground  IOException");
                break;
            }

            // If a connection was accepted
            if (socket != null) {
                switch (mState) {
                    case STATE_LISTEN:
                    case STATE_CONNECTING:
                        // Situation normal. Start the connected thread.
                        connected(socket, socket.getRemoteDevice());
                        break;
                    case STATE_NONE:
                    case STATE_CONNECTED:
                        // Either not ready or already connected. Terminate new socket.
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("@@@ BluetoothAcceptTask doInBackground  IOException 2");
                        }
                        break;
                }
            }
        }
        System.out.println("@@@ BluetoothAcceptTask doInBackground �� ");
        return null;
    }


    private void connected(BluetoothSocket socket, BluetoothDevice
            device) {

        mConnectedThread = new BluetoothConnectedTask(socket, mBlListener);
        mState = STATE_CONNECTED;
    }


    /**
     * Ŭ���̾�Ʈ ���� close �޼ҵ�
     */
    public void cancle() {
        try {
            mServerSocket.close();
        } catch (IOException e) {
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }

    /**
     * ���� ���� �޼ҵ�
     * @param sts ����
     */
    public void setStats(int sts){
        mState = sts;
    }
}
