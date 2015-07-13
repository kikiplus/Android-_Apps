package main;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.test.mihye.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Event.OnBluetoothEventListener;
import Interface.IBLConnectLinstener;
import Managers.bluetooth.BluetoothAcceptTask;
import Managers.bluetooth.BluetoothConnectTask;
import Managers.bluetooth.BluetoothConnectedTask;
import Managers.bluetooth.BluetoothDataManager;


public class BluetoothMainActivity extends Activity implements View.OnClickListener, Handler.Callback, OnBluetoothEventListener, IBLConnectLinstener {

    /**
     * �˻���ư
     */
    private Button mButton = null;

    /**
     * ��۹�ư
     */
    private ToggleButton mToggleButton = null;

    /**
     * ����Ʈ��
     */
    private ListView mListview = null;

    /**
     * ��� �˻� ����Ʈ��
     */
    private ListView mSeartchListview = null;

    /**
     * ��ϵ� ��� ����Ʈ ���
     */
    private ArrayAdapter<String> mArrayAdapter = null;
    /**
     * �˻� �� ����Ʈ ���
     */
    private ArrayAdapter<String> mSearchArrayAdapter = null;

    /**
     * ��ϵ� ��� ����Ʈ
     */
    private ArrayList<String> mArrayList = null;
    /**
     * �˻� �� ����Ʈ
     */
    private ArrayList<String> mSearchArrayList = null;

    /**
     * �ڵ鷯
     */
    private Handler mHandler = null;

    /**
     * �ε� ����
     */
    private boolean mListLodaing = false;

    /**
     * ������� ����Ÿ �Ŵ���
     */
    private BluetoothDataManager mBluetoothMgr = null;

    /**
     * ��� ��ư
     */
    private boolean mToggleStatus = false;

    /**
     * ���� ���� ���
     */
    private String mServerInfo = null;


    private BluetoothAcceptTask mBluetoothAccpetionTask = null;
    private BluetoothConnectedTask mBluetoothConnectedTask = null;
    private BluetoothConnectTask mBluetoothConnectTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_main_layout);

        mBluetoothMgr = new BluetoothDataManager(this, this);
        mHandler = new Handler(this);
        // ��ư �ʱ�ȭ
        mButton = (Button) findViewById(R.id.bluetooth_main_seartchBtn);
        mButton.setOnClickListener(this);
        mToggleButton = (ToggleButton) findViewById(R.id.bluetooth_main_toggleButton);
        mToggleButton.setOnClickListener(this);
        // ����Ʈ�� �ʱ�ȭ
        mListview = (ListView) findViewById(R.id.bluetooth_main_pairedDevices_listview);
        registerForContextMenu(mListview);
        mSeartchListview = (ListView) findViewById(R.id.bluetooth_main_listview);
        registerForContextMenu(mSeartchListview);

        mArrayList = new ArrayList<String>();
        //��ư �ʱ�ȭ
        setButton();

        //������������ ������� ��ٸ���
        mBluetoothAccpetionTask = new BluetoothAcceptTask(mBluetoothMgr.getmBluetoothAdapter(), this);
        mBluetoothAccpetionTask.execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.bluetooth_main_pairedDevices_listview) {
            menu.add(0, CONTEXT_MENU_REMOVE_DEVICE, Menu.NONE, "��� ����");
            menu.add(0, CONTEXT_MENU_SEND_DEVICE, Menu.NONE, "���� ����");
        } else if (v.getId() == R.id.bluetooth_main_listview) {
            menu.add(0, CONTEXT_MENU_PAIRING_DEVICE, Menu.NONE, "���");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_REMOVE_DEVICE://�������
                String mac = mArrayList.get(contextMenuInfo.position).split("\n")[1];
                boolean isUnpair = mBluetoothMgr.setUnpairDevice(mac);
                if (isUnpair) {
                    mArrayList.remove(contextMenuInfo.position);
                    mArrayAdapter.notifyDataSetChanged();
                }
                break;
            case CONTEXT_MENU_PAIRING_DEVICE://���
                mac = mSearchArrayList.get(contextMenuInfo.position).split("\n")[1];
                boolean isPair = mBluetoothMgr.setPairingDevice(mac);
                if (!isPair) {
                    Toast.makeText(getApplicationContext(), "���� +" + mac + " : ��Ͼȵ�", Toast.LENGTH_LONG).show();
                }
                break;
            case CONTEXT_MENU_SEND_DEVICE://���� ����
                mServerInfo = mArrayList.get(contextMenuInfo.position);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GET_FILE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_FILE && resultCode == RESULT_OK) {
            Uri fileUrl = data.getData();
            mHandler.sendMessage(mHandler.obtainMessage(SEND_FILE, fileUrl));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bluetooth_main_seartchBtn: // �ֺ���� �˻� ��ư
                if (!mListLodaing) {  //�˻�
                    startDeviceScan();
                } else {//����
                    stopDeviceScan();
                }
                break;
            case R.id.bluetooth_main_toggleButton:   // ��� ��ư
                if (!mToggleStatus) {//����
                    mToggleStatus = true;
                    mToggleButton.setChecked(mToggleStatus);
                    mBluetoothMgr.setBluetoothStatus(true);
                    findViewById(R.id.bluetooth_layout).setVisibility(View.VISIBLE);
                } else {//����
                    setBluetoothOFF();
                }
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SET_ADDED_IST:// ���� ��� ����Ʈ
                ArrayList<String> dataList = (ArrayList<String>) msg.obj;
                if (dataList != null) {
                    mArrayList = (ArrayList<String>) msg.obj;
                    mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mArrayList);
                    mListview.setAdapter(mArrayAdapter);
                }
                break;
            case SEARCH_DEVICES_LIST:// �ֺ� ��� ����Ʈ �߰�
                String data = (String) msg.obj;
                if (mSearchArrayList != null) {
                    mSearchArrayList.add(data);
                } else {
                    mSearchArrayList = new ArrayList<String>();
                    mSearchArrayList.add(data);
                }
                mSearchArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mSearchArrayList);
                mSearchArrayAdapter.notifyDataSetChanged();
                mSeartchListview.setAdapter(mSearchArrayAdapter);
                break;
            case INIT_LISTVIEW:// ������ ����Ʈ �ʱ�ȭ
                setInitListview();
                break;
            case COMPLE_SEARCH_DEVICES:// �ֺ� �˻� ��� �Ϸ�
                mButton.setText("ã��");
                mListLodaing = false;
                break;
            case BLUETOOTH_OFF:// ������� ���� ����
                setBluetoothOFF();
                break;
            case BLUETOOTH_ON://������� ���� ����
                setButton();
                break;
            case SEND_FILE: // ���� ����
                String name = mServerInfo.split("\n")[0];
                String mac = mServerInfo.split("\n")[1];
                Uri urlData = (Uri) msg.obj;
                System.out.println("@@@ fileUrl : " + urlData.getPath());
                File file = new File(urlData.getPath());
                BluetoothDevice device = mBluetoothMgr.getBluetoothDevice(mac);
                if (mBluetoothConnectTask == null) {
                    System.out.println("@@ BluetoothConnectTask is Null ");
                    mBluetoothConnectTask = new BluetoothConnectTask(device, mBluetoothMgr.getmBluetoothAdapter());
                    mBluetoothConnectTask.execute();
                }else{
                    System.out.println("@@ BluetoothConnectTask is not Null ");
                }

                BluetoothSocket socket = mBluetoothConnectTask.getSocket();
                System.out.println("@@ BluetoothConnectTask socket :  "+ socket);
                if (socket != null) {
                    mBluetoothConnectedTask = new BluetoothConnectedTask(socket, this);
//                    mBluetoothConnectedTask.execute();
                    if (mBluetoothConnectedTask.getOutputStream()) {
                        System.out.println("@@ BluetoothConnectTask ���� ����");
                        String strdata = "TEST �Դϴ� !!!";
                        mBluetoothConnectedTask.write(strdata.getBytes());
                    }else{
                        System.out.println("@@ BluetoothConnectTask getOutputStream false");
                    }
                }
                break;
            case 1000:
                String contents = (String)msg.obj;
                Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothMgr.close();
    }

    /**
     * �ʱ��ư ���� �޼ҵ�
     */
    private void setButton() {
        //���� ������� ���¿� ���� ��۹�ư ����
        boolean isStauts = mBluetoothMgr.getBlutoothAdpaterStatus();
        if (isStauts) {
            mToggleStatus = true;
            findViewById(R.id.bluetooth_layout).setVisibility(View.VISIBLE);
            mHandler.sendMessage(mHandler.obtainMessage(SET_ADDED_IST, mBluetoothMgr.getAddedList()));
        } else {
            mToggleStatus = false;
            findViewById(R.id.bluetooth_layout).setVisibility(View.INVISIBLE);
        }
        mToggleButton.setChecked(mToggleStatus);
    }

    /**
     * ������� ����̽� �˻� ���� �޼ҵ�
     */
    private void startDeviceScan() {
        mListLodaing = true;
        mBluetoothMgr.setBluetoothSearchStatus(true);
        mButton.setText("����");
        mSearchArrayList = new ArrayList<String>();
    }

    /**
     * ������� ����̽� �˻� ���� �޼ҵ�
     */
    private void stopDeviceScan() {
        mBluetoothMgr.setBluetoothSearchStatus(false);
        if (mBluetoothMgr.getBlutoothAdpaterDiscoveringStatus() == false) {
            mListLodaing = false;
        }
        mButton.setText("ã��");
    }

    /**
     * ������� ���� �޼ҵ�
     */
    private void setBluetoothOFF() {
        mToggleStatus = false;
        mToggleButton.setChecked(mToggleStatus);
        mBluetoothMgr.setBluetoothStatus(false);
        mHandler.sendEmptyMessage(INIT_LISTVIEW);
    }

    /**
     * ����Ʈ�� �ʱ�ȭ �޼ҵ�
     */
    private void setInitListview() {
        if (mArrayList != null) {
            mArrayList.clear();
        }
        if (mSearchArrayList != null) {
            mSearchArrayList.clear();
        }
        mButton.setText("ã��");
        mListLodaing = false;
        findViewById(R.id.bluetooth_layout).setVisibility(View.INVISIBLE);
    }

    /***********************************************
     *
     * ���ؽ�Ʈ �޴� ���ǰ�
     *
     *********************************************/

    /**
     * �޴� 1- �� ���
     */
    private static final int CONTEXT_MENU_PAIRING_DEVICE = 1;

    /**
     * �޴� 2- ��� ����
     */
    private static final int CONTEXT_MENU_REMOVE_DEVICE = 2;

    /**
     * �޴� 3- ���� ����
     */
    private static final int CONTEXT_MENU_SEND_DEVICE = 3;

    /**
     * ����Ʈ �� �ʱ�ȭ
     */
    private static final int INIT_LISTVIEW = 4;

    /**
     * ���� ����
     */
    private static final int SEND_FILE = 5;

    /**
     * ���� ��������
     */
    private static final int GET_FILE = 6;

    @Override
    /**
     * �̺�Ʈ �޽��� ó�� �޼ҵ�
     */
    public void onAction(int what, Object obj) {
        switch (what) {
            case SET_ADDED_IST:// ���� ��� ����Ʈ
                mHandler.sendMessage(mHandler.obtainMessage(SET_ADDED_IST, obj));
                break;
            case SEARCH_DEVICES_LIST:// �ֺ� ��� ����Ʈ �߰�
                mHandler.sendMessage(mHandler.obtainMessage(SEARCH_DEVICES_LIST, obj));
                break;
            case COMPLE_SEARCH_DEVICES://�Ϸ�
                mHandler.sendMessage(mHandler.obtainMessage(COMPLE_SEARCH_DEVICES, obj));
                break;
            case BLUETOOTH_OFF://������� OFF
                mHandler.sendEmptyMessage(BLUETOOTH_OFF);
                break;
            case BLUETOOTH_ON://������� ON
                mHandler.sendEmptyMessage(BLUETOOTH_ON);
                break;
        }
    }

    @Override
    public void onReceiveAction(int what, Object obj) {
        switch (what) {
            case RECEIVE_ERROR:
                System.out.println("@@ ������� ����Ÿ ���� ��� Not Okay: " + (String) obj);
                break;

            case RECEIVE_OK:
                byte[] readBuf = (byte[]) obj;
                System.out.println("@@ ������� ����Ÿ ���� ��� ������");
                break;

            case SEND_USER:
                System.out.println("@@ ������� ����Ÿ ������ �߾��");
                if (mBluetoothConnectedTask != null) {
                    if (mBluetoothConnectedTask.getInputStream()) {
                        mBluetoothConnectedTask.execute();
                    }else{
                        mHandler.sendMessage(mHandler.obtainMessage(1000,"mBluetoothConnectedTask inputStream is Null"));
                    }
                } else {
                    mHandler.sendMessage(mHandler.obtainMessage(1000,"mBluetoothConnectedTask is Null"));
                }
                mHandler.sendMessage(mHandler.obtainMessage(1000,"mBluetoothConnectedTask ���۹��� "));
                break;
        }
    }
}