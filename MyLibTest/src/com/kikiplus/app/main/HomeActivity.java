package com.kikiplus.app.main;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kikiplus.app.R;
import com.kikiplus.app.frame.custom.KProgressBar;
import com.kikiplus.app.main.view.MenuView;
import com.kikiplus.app.main.view.MenuView2;


/**
 * @author grapegirl
 * @version 1.1
 * @Class Name : HomeActivity
 * @since 2017-02-12.
 */
public class HomeActivity extends Activity implements ListView.OnItemClickListener, View.OnClickListener {

    private String[] mDrawerTitles;
    private RelativeLayout mBaseLayout;
    private ListView mDrawerListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private static Context mCotext = null;
    private ViewGroup mRootGroup = null;
    private Button[] mButton = null;
    private KProgressBar mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        mDrawerTitles = getResources().getStringArray(R.array.navigation_menu);
        mBaseLayout = (RelativeLayout) findViewById(R.id.main_content);

        //네비게이션 드로어 설정
        mDrawerListView = (ListView) findViewById(R.id.navigtaion_main_listview);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDrawerTitles));
        mDrawerListView.setOnItemClickListener(this);

        //액션바에 토글 생성
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_main_drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_open_drawer, R.string.navigation_close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mButton = new Button[5];
        mButton[0] = (Button) findViewById(R.id.main_tab1);
        mButton[1] = (Button) findViewById(R.id.main_tab2);
        mButton[2] = (Button) findViewById(R.id.main_tab3);
        mButton[3] = (Button) findViewById(R.id.main_tab4);
        mButton[4] = (Button) findViewById(R.id.main_tab5);

        mButton[0].setOnClickListener(this);
        mButton[1].setOnClickListener(this);
        mButton[2].setOnClickListener(this);
        mButton[3].setOnClickListener(this);
        mButton[4].setOnClickListener(this);
    }

    public View getMenuView(int menuIndex) {
        switch (menuIndex) {
            case 0:
                return new MenuView(getApplicationContext(), R.layout.grid_layout, 0).getCurrentView();
            case 1:
                return new MenuView2(getApplicationContext(), R.layout.activity_popup_test, 1).getCurrentView();
        }
        return null;
    }

    @Override
    /***
     * 네비게이션 드로어 아이템 선택시 호출 메소드
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mBaseLayout.setBackgroundColor(Color.parseColor("#A52A2A"));
                break;
            case 1:
                mBaseLayout.setBackgroundColor(Color.parseColor("#5F9EA0"));
                break;
            case 2:
                mBaseLayout.setBackgroundColor(Color.parseColor("#556B2F"));
                break;
            case 3:
                mBaseLayout.setBackgroundColor(Color.parseColor("#FF8C00"));
                break;
            case 4:
                mBaseLayout.setBackgroundColor(Color.parseColor("#DAA520"));
                break;
        }
        mDrawerLayout.closeDrawer(mDrawerListView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    /**
     * 메뉴 선택 클래스
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1:
                Toast.makeText(this, "MENU 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item2:
                Toast.makeText(this, "MENU 2", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item3:
                Toast.makeText(this, "MENU 3", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item4:
                Toast.makeText(this, "MENU 4", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item5:
                Toast.makeText(this, "MENU 5", Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    /***
     * 메뉴 생성 클래스
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab1:
                Toast.makeText(this, "메인1", Toast.LENGTH_SHORT).show();
                mBaseLayout.removeAllViews();
                mBaseLayout.addView(getMenuView(0));
                break;
            case R.id.main_tab2:
                Toast.makeText(this, "메인2", Toast.LENGTH_SHORT).show();
                mBaseLayout.removeAllViews();
                mBaseLayout.addView(getMenuView(1));
                break;
            case R.id.main_tab3:
                Toast.makeText(this, "메인3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_tab4:
                Toast.makeText(this, "메인4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_tab5:
                Toast.makeText(this, "메인5", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
