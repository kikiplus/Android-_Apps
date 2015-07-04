package Managers.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.test.mihye.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import Event.BluetoothEventListener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-06-25.
 */
public class BluetoothDataManager {

    /**
     * 블루투스 어뎁터
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * 등록된 기기 리스트
     */
    private ArrayList<String> mArrayList = null;
    /**
     * 검색 후 리스트
     */
    private ArrayList<String> mSearchArrayList = null;

    /**
     * 컨텍스트
     */
    private Context mContext = null;

    /**
     * 블루투스 이벤트 리스너
     */
    private BluetoothEventListener mBluetoothEventListener = null;


    /**
     * 생성자
     */
    public BluetoothDataManager(Context context, BluetoothEventListener eventListener) {

        mContext = context;
        mBluetoothEventListener = eventListener;
        mArrayList = new ArrayList<String>();
        mSearchArrayList = new ArrayList<String>();


        // 블루투스 어뎁터
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, mContext.getString(R.string.blutooth_main_notsupport), Toast.LENGTH_LONG).show();
        } else {
            Log.d(conf.Log.LOG_NAME, this.getClass() + mContext.getString(R.string.blutooth_main_support));
        }

        // 브로드캐스트 리시버 등록
        IntentFilter Filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, Filter);
        Filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mContext.registerReceiver(mReceiver, Filter);

        Filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mReceiver, Filter);
        Filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(mReceiver, Filter);
    }

    /**
     * 블루투스 주변기기 검색 리시버
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("@@ onReceive action : " + action);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    String data = device.getName() + "\n"
                            + device.getAddress();
                    //검색 후 주변 디바이스가 리스트에 없는 기기이면 추가
                    if (!checkAddedListItem(data)) {
                        mSearchArrayList.add(data);
                        mBluetoothEventListener.onAction(mBluetoothEventListener.SEARCH_DEVICES_LIST, data);
                    }
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                if ((state == BluetoothAdapter.STATE_OFF)) {
                    mBluetoothEventListener.onAction(mBluetoothEventListener.BLUETOOTH_OFF, null);
                } else if ((state == BluetoothAdapter.STATE_ON)) {
                    mBluetoothEventListener.onAction(mBluetoothEventListener.BLUETOOTH_ON, null);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //검색 후 완료되면 주변 디바이스 정보 반환
                mBluetoothEventListener.onAction(mBluetoothEventListener.COMPLE_SEARCH_DEVICES, true);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {

                int bondState = intent.getIntExtra(
                        BluetoothDevice.EXTRA_BOND_STATE, -1);

                if (bondState == BluetoothDevice.BOND_NONE) {
                    System.out.println("연결안됨");
                } else if (bondState == BluetoothDevice.BOND_BONDED){
                    System.out.println("연결됨");
                    mBluetoothEventListener.onAction(mBluetoothEventListener.SET_ADDED_IST, getAddedList());
                }
            }
        }
    };

    /**
     * 블루투스 어뎁터 상태 반환 메소드
     *
     * @return 블루투스 어뎁터 상태
     */
    public boolean getBlutoothAdpaterStatus() {
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 블루투스 어뎁터 검색 상태 반환 메소드
     *
     * @return 블루투스 어뎁터 검색 상태 반환
     */
    public boolean getBlutoothAdpaterDiscoveringStatus() {
        return mBluetoothAdapter.isDiscovering();
    }

    /**
     * 블루투스 어뎁터 상태 설정 메소드
     *
     * @param stats 어뎁터 사용 여부
     */
    public void setBluetoothStatus(boolean stats) {
        if (stats) {
            mBluetoothAdapter.enable();
        } else {
            mBluetoothAdapter.disable();
        }
    }

    /**
     * 블루투스 검색 설정 메소드
     *
     * @param stats 주변 기기 검색 사용 여부
     */
    public void setBluetoothSearchStatus(boolean stats) {
        if (stats) {
            mBluetoothAdapter.startDiscovery();
        } else {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    /**
     * 리스트에 추가되었는지 여부 반환 메소드
     *
     * @param newItem 기기명과 맥주소
     * @return 리스트에 추가되었는지 여부
     */
    private boolean checkAddedListItem(String newItem) {
        for (int i = 0; i < mSearchArrayList.size(); i++) {
            String item = mSearchArrayList.get(i);
            if (item.equals(newItem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 등록된 리스트 반환 메소드(초기)
     *
     * @return 등록된 기기 리스트
     */
    public ArrayList<String> getAddedList() {
        if(mArrayList != null){
            mArrayList.clear();
        }
        if (mBluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            System.out.println("페어링된 기기 리스트 : " + pairedDevices.size());
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    mArrayList.add(device.getName() + "\n" + device.getAddress());
                    System.out.println("@@ 페어링된 기기 리스트 : " + device.getName() + "," + device.getAddress());
                }
            }
            return mArrayList;
        } else {
            return null;
        }
    }

    /**
     * 디바이스 UUID 반환 메소드
     *
     * @param index 주변 기기 리스트의 인덱스
     * @return 디바이스 UUID
     */
    public String getDeviceUUID(int index) {


        return null;
    }

    /**
     * 블루투스 데이타 매니저 리소스 해제 메소드
     */
    public void close() {
        // 리시버 해제
        mContext.unregisterReceiver(mReceiver);
        if (mArrayList != null) {
            mArrayList.clear();
            mArrayList = null;
        }
        if (mSearchArrayList != null) {
            mSearchArrayList.clear();
            mSearchArrayList = null;
        }
    }

    /**
     * 블루투스 페어링 해제 메소드
     *
     * @param currentMac 페어링 해제할 맥주소
     * @return 페어링 해제 여부
     */
    public boolean setUnpairDevice(String currentMac) {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        try {
            Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
            Method removeBondMethod = btDeviceInstance.getMethod("removeBond");
            boolean cleared = false;
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                String mac = bluetoothDevice.getAddress();
                if (mac.equals(currentMac)) {
                    removeBondMethod.invoke(bluetoothDevice);
                    System.out.println("Cleared Pairing");
                    cleared = true;
                    break;
                }
            }
            if (!cleared) {
                System.out.println("Not Paired");
                return false;
            }
        } catch (Throwable th) {
            System.out.println("Error pairing");
            return false;
        }
        return true;
    }

    public boolean setPairingDevice(String currentMac) {
        try {
            Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
            Method createBondMethod = btDeviceInstance.getMethod("createBond");
            boolean cleared = false;
            BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(currentMac);
            createBondMethod.invoke(bluetoothDevice);
            cleared = true;
            System.out.println("OK Paired");
            if (!cleared) {
                System.out.println("Not Paired");
                return false;
            }
        } catch (Throwable th) {
            System.out.println("Not Pairing");
            return false;
        }
        return true;
    }
}