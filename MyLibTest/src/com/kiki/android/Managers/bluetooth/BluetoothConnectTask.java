package com.kiki.android.Logic.Managers.bluetooth;

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
 * @Description : 블루투스 기기 등록 허용 클래스(Server)
 * @since 2015-06-26.
 */
public class BluetoothConnectTask extends AsyncTask<Void, Void, Void> {

    /**
     * 소켓
     */
    private final BluetoothSocket mSocket;

    /**
     * 기기
     */
    private final BluetoothDevice mDevice;

    private BluetoothAdapter mBluetoothAdapter;

    /**
     * 연결하려는 기기 UUID
     */
    private static final UUID SERVER_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    /**
     * 생성자
     * @param device 블루투스 디바이스
     * @param bluetoothAdapter 어뎁터
     */
    public BluetoothConnectTask(BluetoothDevice device, BluetoothAdapter bluetoothAdapter) {
        System.out.println("@@@ BluetoothConnectTask 생성자 " );
        BluetoothSocket tmp = null;
        mDevice = device;
        mBluetoothAdapter = bluetoothAdapter;
        try {
            // MY_UUID is the app’s UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(SERVER_UUID);
        } catch (IOException e) {
            System.out.println("@@@ BluetoothConnectTask 생성자 IOException");
        }
        mSocket = tmp;
        System.out.println("@@@ BluetoothConnectTask 생성자 끝" );
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("@@@ BluetoothConnectTask doInBackground 시작");
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
        System.out.println("@@@ BluetoothConnectTask doInBackground 끝" );
        return null;
    }


    /**
     * 서버 소켓 close 메소드
     */
    public void cancle(){
        try{
            mSocket.close();
        }catch (IOException e){
            System.out.print("BluetoothServerConnectTask cancle IOException");
        }
    }

    /**
     * 소켓 반환 메소드
     * @return 소켓
     */
    public BluetoothSocket getSocket(){
        return mSocket;
    }
}
