package com.kiki.View.Activity.TestA;

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

import com.kiki.View.R;
import com.kiki.android.Listener.IBLConnectLinstener;
import com.kiki.android.Listener.UIEvent.OnBluetoothEventListener;
import com.kiki.android.Managers.bluetooth.BluetoothAcceptTask;
import com.kiki.android.Managers.bluetooth.BluetoothConnectTask;
import com.kiki.android.Managers.bluetooth.BluetoothConnectedTask;
import com.kiki.android.Managers.bluetooth.BluetoothDataManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class BluetoothMainActivity extends Activity implements View.OnClickListener, Handler.Callback, OnBluetoothEventListener, IBLConnectLinstener {

    /**
     * 검색버튼
     */
    private Button mButton = null;

    /**
     * 토글버튼
     */
    private ToggleButton mToggleButton = null;

    /**
     * 리스트뷰
     */
    private ListView mListview = null;

    /**
     * 기기 검색 리스트뷰
     */
    private ListView mSeartchListview = null;

    /**
     * 등록된 기기 리스트 어뎁터
     */
    private ArrayAdapter<String> mArrayAdapter = null;
    /**
     * 검색 후 리스트 어뎁터
     */
    private ArrayAdapter<String> mSearchArrayAdapter = null;

    /**
     * 등록된 기기 리스트
     */
    private ArrayList<String> mArrayList = null;
    /**
     * 검색 후 리스트
     */
    private ArrayList<String> mSearchArrayList = null;

    /**
     * 핸들러
     */
    private Handler mHandler = null;

    /**
     * 로딩 여부
     */
    private boolean mListLodaing = false;

    /**
     * 블루투스 데이타 매니저
     */
    private BluetoothDataManager mBluetoothMgr = null;

    /**
     * 토글 버튼
     */
    private boolean mToggleStatus = false;

    /**
     * 파일 전송 상대
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
        // 버튼 초기화
        mButton = (Button) findViewById(R.id.bluetooth_main_seartchBtn);
        mButton.setOnClickListener(this);
        mToggleButton = (ToggleButton) findViewById(R.id.bluetooth_main_toggleButton);
        mToggleButton.setOnClickListener(this);
        // 리스트뷰 초기화
        mListview = (ListView) findViewById(R.id.bluetooth_main_pairedDevices_listview);
        registerForContextMenu(mListview);
        mSeartchListview = (ListView) findViewById(R.id.bluetooth_main_listview);
        registerForContextMenu(mSeartchListview);

        mArrayList = new ArrayList<String>();
        //버튼 초기화
        setButton();

        //서버소켓으로 열어놓고 기다리기
//        mBluetoothAccpetionTask = new BluetoothAcceptTask(mBluetoothMgr.getmBluetoothAdapter(), this);
//        mBluetoothAccpetionTask.execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.bluetooth_main_pairedDevices_listview) {
            menu.add(0, CONTEXT_MENU_REMOVE_DEVICE, Menu.NONE, "등록 해제");
            menu.add(0, CONTEXT_MENU_SEND_DEVICE, Menu.NONE, "파일 전송");
        } else if (v.getId() == R.id.bluetooth_main_listview) {
            menu.add(0, CONTEXT_MENU_PAIRING_DEVICE, Menu.NONE, "등록");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_REMOVE_DEVICE://등록해제
                String mac = mArrayList.get(contextMenuInfo.position).split("\n")[1];
                boolean isUnpair = mBluetoothMgr.setUnpairDevice(mac);
                if (isUnpair) {
                    mArrayList.remove(contextMenuInfo.position);
                    mArrayAdapter.notifyDataSetChanged();
                }
                break;
            case CONTEXT_MENU_PAIRING_DEVICE://등록
                mac = mSearchArrayList.get(contextMenuInfo.position).split("\n")[1];
                boolean isPair = mBluetoothMgr.setPairingDevice(mac);
                if (!isPair) {
                    Toast.makeText(getApplicationContext(), "페어링한 +" + mac + " : 등록안됨", Toast.LENGTH_LONG).show();
                }
                break;
            case CONTEXT_MENU_SEND_DEVICE://파일 전송
                mServerInfo = mArrayList.get(contextMenuInfo.position);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, GET_FILE);
                mHandler.sendMessage(mHandler.obtainMessage(SEND_FILE, null));
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
            case R.id.bluetooth_main_seartchBtn: // 주변기기 검색 버튼
                if (!mListLodaing) {  //검색
                    startDeviceScan();
                } else {//중지
                    stopDeviceScan();
                }
                break;
            case R.id.bluetooth_main_toggleButton:   // 토글 버튼
                if (!mToggleStatus) {//켜짐
                    mToggleStatus = true;
                    mToggleButton.setChecked(mToggleStatus);
                    mBluetoothMgr.setBluetoothStatus(true);
                    findViewById(R.id.bluetooth_layout).setVisibility(View.VISIBLE);
                } else {//꺼짐
                    setBluetoothOFF();
                }
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SET_ADDED_IST:// 페어링된 기기 리스트
                ArrayList<String> dataList = (ArrayList<String>) msg.obj;
                if (dataList != null) {
                    mArrayList = (ArrayList<String>) msg.obj;
                    mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mArrayList);
                    mListview.setAdapter(mArrayAdapter);
                }
                break;
            case SEARCH_DEVICES_LIST:// 주변 기기 리스트 추가
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
            case INIT_LISTVIEW:// 꺼짐시 리스트 초기화
                setInitListview();
                break;
            case COMPLE_SEARCH_DEVICES:// 주변 검색 기기 완료
                mButton.setText("찾기");
                mListLodaing = false;
                break;
            case BLUETOOTH_OFF:// 블루투스 상태 꺼짐
                setBluetoothOFF();
                break;
            case BLUETOOTH_ON://블루투스 상태 켜짐
                setButton();
                break;
            case SEND_FILE: // 파일 전송
                String name = mServerInfo.split("\n")[0];
                String mac = mServerInfo.split("\n")[1];
//                Uri urlData = (Uri) msg.obj;
//                System.out.println("@@@ fileUrl : " + urlData.getPath());
//                File file = new File(urlData.getPath());
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
                        System.out.println("@@ BluetoothConnectTask 파일 전송");
                        String strdata = "TEST 입니다 !!!";

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
     * 초기버튼 설정 메소드
     */
    private void setButton() {
        //현재 블루투스 상태에 따라 토글버튼 설정
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
     * 블루투스 디바이스 검색 시작 메소드
     */
    private void startDeviceScan() {
        mListLodaing = true;
        mBluetoothMgr.setBluetoothSearchStatus(true);
        mButton.setText("중지");
        mSearchArrayList = new ArrayList<String>();
    }

    /**
     * 블루투스 디바이스 검색 중지 메소드
     */
    private void stopDeviceScan() {
        mBluetoothMgr.setBluetoothSearchStatus(false);
        if (mBluetoothMgr.getBlutoothAdpaterDiscoveringStatus() == false) {
            mListLodaing = false;
        }
        mButton.setText("찾기");
    }

    /**
     * 블루투스 꺼짐 메소드
     */
    private void setBluetoothOFF() {
        mToggleStatus = false;
        mToggleButton.setChecked(mToggleStatus);
        mBluetoothMgr.setBluetoothStatus(false);
        mHandler.sendEmptyMessage(INIT_LISTVIEW);
    }

    /**
     * 리스트뷰 초기화 메소드
     */
    private void setInitListview() {
        if (mArrayList != null) {
            mArrayList.clear();
        }
        if (mSearchArrayList != null) {
            mSearchArrayList.clear();
        }
        mButton.setText("찾기");
        mListLodaing = false;
        findViewById(R.id.bluetooth_layout).setVisibility(View.INVISIBLE);
    }

    /***********************************************
     *
     * 컨텍스트 메뉴 정의값
     *
     *********************************************/

    /**
     * 메뉴 1- 페어링 등록
     */
    private static final int CONTEXT_MENU_PAIRING_DEVICE = 1;

    /**
     * 메뉴 2- 등록 해제
     */
    private static final int CONTEXT_MENU_REMOVE_DEVICE = 2;

    /**
     * 메뉴 3- 파일 전송
     */
    private static final int CONTEXT_MENU_SEND_DEVICE = 3;

    /**
     * 리스트 뷰 초기화
     */
    private static final int INIT_LISTVIEW = 4;

    /**
     * 파일 전송
     */
    private static final int SEND_FILE = 5;

    /**
     * 파일 가져오기
     */
    private static final int GET_FILE = 6;

    @Override
    /**
     * 이벤트 메시지 처리 메소드
     */
    public void onAction(int what, Object obj) {
        switch (what) {
            case SET_ADDED_IST:// 페어링된 기기 리스트
                mHandler.sendMessage(mHandler.obtainMessage(SET_ADDED_IST, obj));
                break;
            case SEARCH_DEVICES_LIST:// 주변 기기 리스트 추가
                mHandler.sendMessage(mHandler.obtainMessage(SEARCH_DEVICES_LIST, obj));
                break;
            case COMPLE_SEARCH_DEVICES://완료
                mHandler.sendMessage(mHandler.obtainMessage(COMPLE_SEARCH_DEVICES, obj));
                break;
            case BLUETOOTH_OFF://블루투스 OFF
                mHandler.sendEmptyMessage(BLUETOOTH_OFF);
                break;
            case BLUETOOTH_ON://블루투스 ON
                mHandler.sendEmptyMessage(BLUETOOTH_ON);
                break;
        }
    }

    @Override
    public void onReceiveAction(int what, Object obj) {
        switch (what) {
            case RECEIVE_ERROR:
                System.out.println("@@ 블루투스 데이타 전송 결과 Not Okay: " + (String) obj);
                break;

            case RECEIVE_OK:
                byte[] readBuf = (byte[]) obj;
                System.out.println("@@ 블루투스 데이타 전송 결과 오케이");
                break;

            case SEND_OK:
                System.out.println("@@ 블루투스 데이타 전송은 했어요");
                if (mBluetoothConnectedTask != null) {
                    if (mBluetoothConnectedTask.getInputStream()) {
                        mBluetoothConnectedTask.execute();
                    }else{
                        mHandler.sendMessage(mHandler.obtainMessage(1000,"mBluetoothConnectedTask inputStream is Null"));
                    }
                } else {
                    mHandler.sendMessage(mHandler.obtainMessage(1000,"mBluetoothConnectedTask is Null"));
                }
                mHandler.sendMessage(mHandler.obtainMessage(1000,"mBluetoothConnectedTask 전송받음 "));
                break;
        }
    }
}