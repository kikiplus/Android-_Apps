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

import Event.OnBluetoothEventListener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-06-25.
 */
public class BluetoothDataManager {

    /**
     * ������� ���
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * ��ϵ� ��� ����Ʈ
     */
    private ArrayList<String> mArrayList = null;
    /**
     * �˻� �� ����Ʈ
     */
    private ArrayList<String> mSearchArrayList = null;

    /**
     * ���ؽ�Ʈ
     */
    private Context mContext = null;

    /**
     * ������� �̺�Ʈ ������
     */
    private OnBluetoothEventListener mBluetoothEventListener = null;


    /**
     * ������
     */
    public BluetoothDataManager(Context context, OnBluetoothEventListener eventListener) {

        mContext = context;
        mBluetoothEventListener = eventListener;
        mArrayList = new ArrayList<String>();
        mSearchArrayList = new ArrayList<String>();


        // ������� ���
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, mContext.getString(R.string.blutooth_main_notsupport), Toast.LENGTH_LONG).show();
        } else {
            Log.d(conf.Log.LOG_NAME, mContext.getString(R.string.blutooth_main_support));
        }

        // ��ε�ĳ��Ʈ ���ù� ���
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
     * ������� �ֺ���� �˻� ���ù�
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
                    //�˻� �� �ֺ� ����̽��� ����Ʈ�� ���� ����̸� �߰�
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
                //�˻� �� �Ϸ�Ǹ� �ֺ� ����̽� ���� ��ȯ
                mBluetoothEventListener.onAction(mBluetoothEventListener.COMPLE_SEARCH_DEVICES, true);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {

                int bondState = intent.getIntExtra(
                        BluetoothDevice.EXTRA_BOND_STATE, -1);

                if (bondState == BluetoothDevice.BOND_NONE) {
                    System.out.println("����ȵ�");
                } else if (bondState == BluetoothDevice.BOND_BONDED) {
                    System.out.println("�����");
                    mBluetoothEventListener.onAction(mBluetoothEventListener.SET_ADDED_IST, getAddedList());
                }
            }
        }
    };

    /**
     * ������� ��� ���� ��ȯ �޼ҵ�
     *
     * @return ������� ��� ����
     */
    public boolean getBlutoothAdpaterStatus() {
        if (mBluetoothAdapter != null){
            return mBluetoothAdapter.isEnabled();
        }else{
            return false;
        }

    }

    /**
     * ������� ��� �˻� ���� ��ȯ �޼ҵ�
     *
     * @return ������� ��� �˻� ���� ��ȯ
     */
    public boolean getBlutoothAdpaterDiscoveringStatus() {
        return mBluetoothAdapter.isDiscovering();
    }

    /**
     * ������� ��� ���� ���� �޼ҵ�
     *
     * @param stats ��� ��� ����
     */
    public void setBluetoothStatus(boolean stats) {
        if (stats) {
            mBluetoothAdapter.enable();
        } else {
            mBluetoothAdapter.disable();
        }
    }

    /**
     * ������� �˻� ���� �޼ҵ�
     *
     * @param stats �ֺ� ��� �˻� ��� ����
     */
    public void setBluetoothSearchStatus(boolean stats) {
        if (stats) {
            mBluetoothAdapter.startDiscovery();
        } else {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    /**
     * ����Ʈ�� �߰��Ǿ����� ���� ��ȯ �޼ҵ�
     *
     * @param newItem ����� ���ּ�
     * @return ����Ʈ�� �߰��Ǿ����� ����
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
     * ��ϵ� ����Ʈ ��ȯ �޼ҵ�(�ʱ�)
     *
     * @return ��ϵ� ��� ����Ʈ
     */
    public ArrayList<String> getAddedList() {
        if (mArrayList != null) {
            mArrayList.clear();
        }
        if (mBluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            System.out.println("���� ��� ����Ʈ : " + pairedDevices.size());
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    mArrayList.add(device.getName() + "\n" + device.getAddress());
                    System.out.println("@@ ���� ��� ����Ʈ : " + device.getName() + "," + device.getAddress());
                }
            }
            return mArrayList;
        } else {
            return null;
        }
    }

    /**
     * ����̽� UUID ��ȯ �޼ҵ�
     *
     * @param index �ֺ� ��� ����Ʈ�� �ε���
     * @return ����̽� UUID
     */
    public String getDeviceUUID(int index) {


        return null;
    }

    /**
     * ������� ����Ÿ �Ŵ��� ���ҽ� ���� �޼ҵ�
     */
    public void close() {
        // ���ù� ����
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
     * ������� �� ���� �޼ҵ�
     *
     * @param currentMac �� ������ ���ּ�
     * @return �� ���� ����
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

    /**
     * �� ��� �޼ҵ�
     *
     * @param currentMac ���� ���ּ�
     * @return �� ��� ����(True - ���, False - ��Ͼȵ�)
     */
    public boolean setPairingDevice(String currentMac) {
        try {
            boolean cleared = false;
            Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
            Method createBondMethod = btDeviceInstance.getMethod("createBond");

            BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(currentMac);
            cleared = (boolean) createBondMethod.invoke(bluetoothDevice);
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


    /**
     * ������� ��� ��ȯ �޼ҵ�
     *
     * @param mac ������� ���ּ�
     * @return ������� ��� ����
     */
    public BluetoothDevice getBluetoothDevice(String mac) {
        return mBluetoothAdapter.getRemoteDevice(mac);
    }


    /**
     * ������� ��� ��ȯ �޼ҵ�
     *
     * @return ������� ���
     */
    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }


}



