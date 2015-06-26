package Managers;

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
public class BluetoothServerConnectTask extends AsyncTask {

    /**
     * 연결하려는 UUID 가진 단말
     */
    private final BluetoothSocket mSocket;

    private final BluetoothDevice mDevice;

    private BluetoothAdapter mBluetoothAdapter;

    /**
     * 생성자
     * @param device 디바이스
     */
    public BluetoothServerConnectTask(BluetoothDevice device, BluetoothAdapter bluetoothAdapter, String uuid) {
        BluetoothSocket tmp = null;
        mDevice = device;
        mBluetoothAdapter = bluetoothAdapter;
        try {
            // MY_UUID is the app’s UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(uuid));
        } catch (IOException e) {
        }
        mSocket = tmp;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        mBluetoothAdapter.cancelDiscovery();

        try{
            mSocket.connect();
            System.out.print("BluetoothServerConnectTask doInBackground mSocket");
            return  true;
        }catch (IOException connectExption){
            System.out.print("BluetoothServerConnectTask doInBackground IOException");
            try{
                mSocket.close();
            }catch(IOException closeExption){
                System.out.print("BluetoothServerConnectTask doInBackground closeExption");
                return null;
            }
        }
//        manageConnectedSocket(socket);
        return  false;
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
}
