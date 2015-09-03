package View.Activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.test.mihye.R;


/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : NavigationDrawerActivity
 * @Description : 네비게이션 드로어와 액션바 테스트 클래스
 * @since 2015-06-26.
 */
public class NavigationDrawerActivity extends ActionBarActivity implements ListView.OnItemClickListener {

    /**
     * 드로어 타이틀
     */
    private String[] mDrawerTitles;
    /**
     * 프레임레이아웃
     */
    private FrameLayout mFrameLayout;
    /**
     * 드로어 리스브뷰
     */
    private ListView mDrawerListView;

    /**
     * 액션바 토글
     */
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * 드로어 레이아웃
     */
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_main_layout);

        mDrawerTitles = getResources().getStringArray(R.array.navigation_menu);
        mFrameLayout = (FrameLayout) findViewById(R.id.navigtaion_fragment_container);

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

        //액션바 셋팅
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.navigation_fragment_toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    /***
     * 네비게이션 드로어 아이템 선택시 호출 메소드
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mFrameLayout.setBackgroundColor(Color.parseColor("#A52A2A"));
                break;
            case 1:
                mFrameLayout.setBackgroundColor(Color.parseColor("#5F9EA0"));
                break;
            case 2:
                mFrameLayout.setBackgroundColor(Color.parseColor("#556B2F"));
                break;
            case 3:
                mFrameLayout.setBackgroundColor(Color.parseColor("#FF8C00"));
                break;
            case 4:
                mFrameLayout.setBackgroundColor(Color.parseColor("#DAA520"));
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
        switch (item.getItemId()){
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
}
