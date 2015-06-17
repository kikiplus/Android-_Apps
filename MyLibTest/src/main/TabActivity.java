package main;

import java.util.ArrayList;

import UIComfonent.BasicTab;
import UIComfonent.BasicTab2;
import UIComfonent.BasicTab3;
import ViewAdapter.TabPageAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.test.mihye.R;

/***
 * @Class Name :
 * @Description :
 * @since 2015. 6. 17.
 * @version 1.0
 * @author grapegirl
 */
public class TabActivity extends FragmentActivity implements TabListener{
    
    /** 뷰 페이퍼 */
    private ViewPager mPager = null;
    /** 탭 페이지 어뎁터 */
    private TabPageAdapter mTabPageAdapter = null;
    /** 탭 페이지 리스트 */
    private ArrayList<Fragment> mFagementList = null;
    /** 액션바 */
    private ActionBar mActionBar = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.tabview_main_layout);

        //1. 액션바 구성
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //2. 탭 구성
        mFagementList = new ArrayList<Fragment>();
        mFagementList.add(new BasicTab());
        mFagementList.add(new BasicTab2());
        mFagementList.add(new BasicTab3());
        mFagementList.add(new BasicTab());
        mTabPageAdapter = new TabPageAdapter(mFagementList, getSupportFragmentManager());

        //3. 뷰 페이퍼에서 뷰 어뎁터 장착 및 이벤트 설정
        mPager = (ViewPager) findViewById(R.id.tabview_main_viewpager);
        mPager.setAdapter(mTabPageAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }
            
        });

        //4. 액션바에 탭 구성 및 이벤ㅌ 설정
        for (int i = 0; i < mTabPageAdapter.getCount(); i++) {
            mActionBar.addTab(mActionBar.newTab().setTabListener(this).setText("test " + i));
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

}
