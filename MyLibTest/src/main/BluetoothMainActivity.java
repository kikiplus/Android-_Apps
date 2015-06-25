package main;

import android.app.Activity;
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
import android.widget.ToggleButton;

import com.test.mihye.R;

import java.util.ArrayList;

import Event.BluetoothEventListener;
import Managers.BluetoothDataManager;


public class BluetoothMainActivity extends Activity implements View.OnClickListener, Handler.Callback, AdapterView.OnItemLongClickListener, BluetoothEventListener {

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
        mSeartchListview.setOnItemLongClickListener(this);

        mArrayList = new ArrayList<String>();
        //버튼 초기화
        setButton();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.bluetooth_main_pairedDevices_listview:
                menu.add(0, CONTEXT_MENU_CHNAGE_NAME, Menu.NONE, "이름 변경");
                menu.add(0, CONTEXT_MENU_REMOVE_DEVICE, Menu.NONE, "등록 해제");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        System.out.println("@@ index : " + contextMenuInfo.position);

        switch (item.getItemId()) {
            case CONTEXT_MENU_CHNAGE_NAME:
                System.out.println("@@ 컨텍스트 메뉴 - 이름 변경");
                break;
            case CONTEXT_MENU_REMOVE_DEVICE:
                System.out.println("@@ 컨텍스트 메뉴 - 기기 삭제");
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.bluetooth_main_listview://등록요청
                break;
        }
        return false;
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
        }
        return false;
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
     * 메뉴 1- 이름 변경
     */
    private static final int CONTEXT_MENU_CHNAGE_NAME = 1;
    /**
     * 메뉴 2- 등록 해제
     */
    private static final int CONTEXT_MENU_REMOVE_DEVICE = 2;

    /**
     * 리스트 뷰 초기화
     */
    private static final int INIT_LISTVIEW = 3;

    @Override
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
    public void setAddMac(Object obj) {

    }

    @Override
    public void setChangetMacName(Object obj) {

    }
}
