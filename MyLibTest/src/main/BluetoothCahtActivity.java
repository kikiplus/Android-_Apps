package main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.test.mihye.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class BluetoothCahtActivity extends Activity implements AdapterView.OnItemClickListener {

    static final int ACTION_ENABLE_BT = 101;

    private TextView mTextMsg;

    private EditText mEditData;

    private BluetoothAdapter mBA;

    private ListView mListDevice;

    private ArrayList<String> mArDevice; // ���� ����̽� ���

    static final String BLUE_NAME = "BluetoothEx";  // ���ӽ� ����ϴ� �̸�

    // ���ӽ� ����ϴ� ���� ID

    static final UUID BLUE_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    private ClientThread mCThread = null; // Ŭ���̾�Ʈ ���� ���� ������

    private ServerThread mSThread = null; // ���� ���� ���� ������

    private SocketThread mSocketThread = null; // ������ �ۼ��� ������


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_chat_main);
        mTextMsg = (TextView) findViewById(R.id.textMessage);
        mEditData = (EditText) findViewById(R.id.editData);

        // ListView �ʱ�ȭ
        initListView();

        // ������� ��� ���ɻ��� �Ǵ�
        boolean isBlue = canUseBluetooth();
        if (isBlue)
            // ���� ���� ����̽� ��� ���ϱ�
            getParedDevice();
    }


    // ������� ��� ���ɻ��� �Ǵ�
    public boolean canUseBluetooth() {
        // ������� ����͸� ���Ѵ�
        mBA = BluetoothAdapter.getDefaultAdapter();
        // ������� ����Ͱ� null �̸� ������� ��� �������� �ʴ´�.
        if (mBA == null) {
            mTextMsg.setText("Device not found");
            return false;
        }
        mTextMsg.setText("Device is exist");
        // ������� Ȱ��ȭ ���¶�� �Լ� Ż��
        if (mBA.isEnabled()) {
            mTextMsg.append("\nDevice can use");
            return true;
        }
        // ����ڿ��� ������� Ȱ��ȭ�� ��û�Ѵ�
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, ACTION_ENABLE_BT);
        return false;
    }

    // ������� Ȱ��ȭ ��û ��� ����
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_ENABLE_BT) {
            // ����ڰ� ������� Ȱ��ȭ ����������
            if (resultCode == RESULT_OK) {
                mTextMsg.append("\nDevice can use");
                // ���� ���� ����̽� ��� ���ϱ�
                getParedDevice();
            }
            // ����ڰ� ������� Ȱ��ȭ ���������
            else {
                mTextMsg.append("\nDevice can not use");
            }
        }
    }

    // ���� ����̽� �˻� ����
    public void startFindDevice() {
        // ���� ����̽� �˻� ����
        stopFindDevice();
        // ����̽� �˻� ����
        mBA.startDiscovery();
        // ���� ����̽� �˻� �̺�Ʈ ���ù� ���
        registerReceiver(mBlueRecv, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    }


    // ����̽� �˻� ����
    public void stopFindDevice() {
        // ���� ����̽� �˻� ���̶�� ����Ѵ�
        if (mBA.isDiscovering()) {
            mBA.cancelDiscovery();
            // ��ε�ĳ��Ʈ ���ù��� ��� �����Ѵ�
            unregisterReceiver(mBlueRecv);
        }
    }

    // ���� ����̽� �˻� �̺�Ʈ ����
    BroadcastReceiver mBlueRecv = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == BluetoothDevice.ACTION_FOUND) {
                // ����Ʈ���� ����̽� ���� ����
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // ���� ����̽��� �ƴ϶��
                if (device.getBondState() != BluetoothDevice.BOND_BONDED)
                    // ����̽��� ��Ͽ� �߰�
                    addDeviceToList(device.getName(), device.getAddress());
            }
        }
    };

    // ����̽��� ListView �� �߰�
    public void addDeviceToList(String name, String address) {
        // ListView �� ����� ArrayList �� ���ο� �׸��� �߰�
        String deviceInfo = name + " - " + address;
        Log.d("tag1", "Device Find: " + deviceInfo);
        mArDevice.add(deviceInfo);
        // ȭ���� �����Ѵ�
        ArrayAdapter adapter = (ArrayAdapter) mListDevice.getAdapter();
        adapter.notifyDataSetChanged();
    }

    // ListView �ʱ�ȭ
    public void initListView() {
        // ����� ����
        mArDevice = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mArDevice);
        // ListView �� ����Ϳ� �̺�Ʈ �����ʸ� ����
        mListDevice = (ListView) findViewById(R.id.listDevice);
        mListDevice.setAdapter(adapter);
        mListDevice.setOnItemClickListener(this);
    }

    // �ٸ� ����̽����� �ڽ��� �˻� ���
    public void setDiscoverable() {
        // ���� �˻� ��� ���¶�� �Լ� Ż��
        if (mBA.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
            return;

        // �ٸ� ����̽����� �ڽ��� �˻� ��� ����
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(intent);
    }

    // ���� ���� ����̽� ��� ���ϱ�
    public void getParedDevice() {
        if (mSThread != null) return;
        // ���� ���� ������ ���� ������ ���� & ����
        mSThread = new ServerThread();
        mSThread.start();

        // ������� ����Ϳ��� ���� ���� ����̽� ����� ���Ѵ�
        Set<BluetoothDevice> devices = mBA.getBondedDevices();
        // ����̽� ��Ͽ��� �ϳ��� ����
        for (BluetoothDevice device : devices) {
            // ����̽��� ��Ͽ� �߰�
            addDeviceToList(device.getName(), device.getAddress());
        }
        // ���� ����̽� �˻� ����
        startFindDevice();
        // �ٸ� ����̽��� �ڽ��� ����
        setDiscoverable();
    }

    // ListView �׸� ���� �̺�Ʈ �Լ�
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        // ����ڰ� ������ �׸��� ������ ���Ѵ�
        String strItem = mArDevice.get(position);
        // ����ڰ� ������ ����̽��� �ּҸ� ���Ѵ�
        int pos = strItem.indexOf(" - ");
        if (pos <= 0) return;
        String address = strItem.substring(pos + 3);
        mTextMsg.setText("Sel Device: " + address);

        // ����̽� �˻� ����
        stopFindDevice();
        // ���� ���� ������ ����
        mSThread.cancel();
        mSThread = null;

        if (mCThread != null) return;
        // ���� ����̽��� ���Ѵ�
        BluetoothDevice device = mBA.getRemoteDevice(address);
        // Ŭ���̾�Ʈ ���� ������ ���� & ����
        mCThread = new ClientThread(device);
        mCThread.start();
    }


    // Ŭ���̾�Ʈ ���� ������ ���� ������
    private class ClientThread extends Thread {
        private BluetoothSocket mmCSocket;

        // ���� ����̽��� ������ ���� Ŭ���̾�Ʈ ���� ����
        public ClientThread(BluetoothDevice device) {
            try {
                mmCSocket = device.createInsecureRfcommSocketToServiceRecord(BLUE_UUID);
            } catch (IOException e) {
                showMessage("Create Client Socket error");
                return;
            }
        }

        public void run() {
            // ���� ����̽��� ���� �õ�
            try {
                mmCSocket.connect();
            } catch (IOException e) {
                showMessage("Connect to server error");
                // ������ ���������� ������ �ݴ´�
                try {
                    mmCSocket.close();
                } catch (IOException e2) {
                    showMessage("Client Socket close error");
                }
                return;
            }
            // ���� ����̽��� ���ӵǾ����� ������ �ۼ��� �����带 ����
            onConnected(mmCSocket);
        }

        // Ŭ���̾�Ʈ ���� ����
        public void cancel() {
            try {
                mmCSocket.close();
            } catch (IOException e) {
                showMessage("Client Socket close error");
            }
        }
    }

    // ���� ������ �����ؼ� ������ ������ Ŭ���̾�Ʈ ������ �����ϴ� ������
    private class ServerThread extends Thread {
        private BluetoothServerSocket mmSSocket;

        // ���� ���� ����
        public ServerThread() {
            try {
                mmSSocket = mBA.listenUsingInsecureRfcommWithServiceRecord(BLUE_NAME, BLUE_UUID);
            } catch (IOException e) {
                showMessage("Get Server Socket Error");
            }
        }

        public void run() {
            BluetoothSocket cSocket = null;
            // ���� ����̽����� ������ ��û�� ������ ��ٸ���
            try {
                cSocket = mmSSocket.accept();
            } catch (IOException e) {
                showMessage("Socket Accept Error");
                return;
            }

            // ���� ����̽��� ���ӵǾ����� ������ �ۼ��� �����带 ����
            onConnected(cSocket);

        }

        // ���� ���� ����
        public void cancel() {
            try {
                mmSSocket.close();
            } catch (IOException e) {
                showMessage("Server Socket close error");
            }
        }
    }


    // �޽����� ȭ�鿡 ǥ��

    public void showMessage(String strMsg) {
        // �޽��� �ؽ�Ʈ�� �ڵ鷯�� ����
        Message msg = Message.obtain(mHandler, 0, strMsg);
        mHandler.sendMessage(msg);
        Log.d("tag1", strMsg);
    }


    // �޽��� ȭ�� ����� ���� �ڵ鷯

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String strMsg = (String) msg.obj;
                mTextMsg.setText(strMsg);

            }

        }

    };


    // ���� ����̽��� ���ӵǾ����� ������ �ۼ��� �����带 ����

    public void onConnected(BluetoothSocket socket) {

        showMessage("Socket connected");

        // ������ �ۼ��� �����尡 �����Ǿ� �ִٸ� �����Ѵ�
        if (mSocketThread != null)
            mSocketThread = null;

        // ������ �ۼ��� �����带 ����
        mSocketThread = new SocketThread(socket);
        mSocketThread.start();

    }


    // ������ �ۼ��� ������

    private class SocketThread extends Thread {
        private final BluetoothSocket mmSocket; // Ŭ���̾�Ʈ ����
        private InputStream mmInStream; // �Է� ��Ʈ��
        private OutputStream mmOutStream; // ��� ��Ʈ��

        public SocketThread(BluetoothSocket socket) {
            mmSocket = socket;
            // �Է� ��Ʈ���� ��� ��Ʈ���� ���Ѵ�
            try {
                mmInStream = socket.getInputStream();
                mmOutStream = socket.getOutputStream();
            } catch (IOException e) {
                showMessage("Get Stream error");
            }
        }


        // ���Ͽ��� ���ŵ� �����͸� ȭ�鿡 ǥ���Ѵ�
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    // �Է� ��Ʈ������ �����͸� �д´�
                    bytes = mmInStream.read(buffer);
                    String strBuf = new String(buffer, 0, bytes);
                    showMessage("Receive: " + strBuf);
                    SystemClock.sleep(1);
                } catch (IOException e) {
                    showMessage("Socket disconneted");
                    break;
                }
            }
        }

        // �����͸� �������� �����Ѵ�
        public void write(String strBuf) {
            try {
                // ��� ��Ʈ���� �����͸� �����Ѵ�
                byte[] buffer = strBuf.getBytes();
                mmOutStream.write(buffer);
                showMessage("Send: " + strBuf);
            } catch (IOException e) {
                showMessage("Socket write error");
            }
        }
    }


    // ��ư Ŭ�� �̺�Ʈ �Լ�
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend: {
                // ������ �ۼ��� �����尡 �������� �ʾҴٸ� �Լ� Ż��
                if (mSocketThread == null) return;
                // ����ڰ� �Է��� �ؽ�Ʈ�� �������� �����Ѵ�
                String strBuf = mEditData.getText().toString();
                if (strBuf.length() < 1) return;
                mEditData.setText("");
                mSocketThread.write(strBuf);
                break;
            }
        }
    }


    // ���� ����� �� ����̽� �˻� ����

    public void onDestroy() {
        super.onDestroy();

        // ����̽� �˻� ����
        stopFindDevice();

        // �����带 ����
        if (mCThread != null) {
            mCThread.cancel();
            mCThread = null;
        }

        if (mSThread != null) {
            mSThread.cancel();
            mSThread = null;
        }
        if (mSocketThread != null)
            mSocketThread = null;

    }
}