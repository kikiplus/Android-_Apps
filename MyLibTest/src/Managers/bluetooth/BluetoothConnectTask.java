package Managers.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BluetoothServerConnectTask
 * @Description : ������� ��� ��� ��� Ŭ����(Server)
 * @since 2015-06-26.
 */
public class BluetoothConnectTask extends AsyncTask<Void, Void, Void> {

    /**
     * ����
     */
    private final BluetoothSocket mSocket;

    /**
     * ���
     */
    private final BluetoothDevice mDevice;

    private BluetoothAdapter mBluetoothAdapter;

    /**
     * �����Ϸ��� ��� UUID
     */
    private static final UUID SERVER_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    /**
     * ������
     * @param device ������� ����̽�
     * @param bluetoothAdapter ���
     */
    public BluetoothConnectTask(BluetoothDevice device, BluetoothAdapter bluetoothAdapter) {
        System.out.println("@@@ BluetoothConnectTask ������ " );
        BluetoothSocket tmp = null;
        mDevice = device;
        mBluetoothAdapter = bluetoothAdapter;
        try {
            // MY_UUID is the app��s UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(SERVER_UUID);
        } catch (IOException e) {
            System.out.println("@@@ BluetoothConnectTask ������ IOException");
        }
        mSocket = tmp;
        System.out.println("@@@ BluetoothConnectTask ������ ��" );
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BluetoothConnectTask doInBackground ����");
        mBluetoothAdapter.cancelDiscovery();
        try{
            mSocket.connect();
            System.out.print("@@ BluetoothConnectTask doInBackground mSocket");
        }catch (IOException connectExption){
            System.out.print("@@ BluetoothConnectTask doInBackground IOException 1 ");
            try{
                mSocket.close();
            }catch(IOException closeExption){
                System.out.print("@@ BluetoothConnectTask doInBackground IOException 2 ");
                return null;
            }
        }
        System.out.println("@@@ BluetoothConnectTask doInBackground ��" );
        return null;
    }


    /**
     * ���� ���� close �޼ҵ�
     */
    public void cancle(){
        try{
            mSocket.close();
        }catch (IOException e){
            System.out.print("BluetoothServerConnectTask cancle IOException");
        }
    }

    /**
     * ���� ��ȯ �޼ҵ�
     * @return ����
     */
    public BluetoothSocket getSocket(){
        return mSocket;
    }
}
