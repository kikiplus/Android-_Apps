package Managers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BluetoothConnectTask
 * @Description : 블루투스 기기 등록 요청 클래스(Client)
 * @since 2015-06-26.
 */
public class BluetoothClientConnectTask extends AsyncTask {

    /**
     * 연결하려는 UUID 가진 단말
     */
    private final BluetoothServerSocket mServerSocket;


    public BluetoothClientConnectTask(BluetoothSocket socket, BluetoothAdapter bluetoothAdapter, String name, String uuid) {
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, UUID.fromString(uuid));
        } catch (IOException e) {
        }
        mServerSocket = tmp;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        BluetoothSocket socket = null;

        while(true){
            try{
                socket = mServerSocket.accept();
                System.out.print("BluetoothClientConnectTask doInbackground socket");
            }catch(IOException e){
                System.out.print("BluetoothClientConnectTask doInbackground IOException");
                return  false;
            }

            if(socket != null){
//                manageConnectedSocket(socket);
                try{
                    mServerSocket.close();
                }catch (IOException IoExcption){
                    return  false;
                }
                break;
            }
        }
        return true;
    }

    /**
     * 클라이언트 소켓 close 메소드
     */
    public void cancle(){
        try{
            mServerSocket.close();
        }catch (IOException e){
            System.out.print("BluetoothClientConnectTask cancle IOException");
        }
    }
}
