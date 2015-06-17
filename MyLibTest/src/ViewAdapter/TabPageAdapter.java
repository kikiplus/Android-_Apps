package ViewAdapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/***
 * @Class Name : TabPageAdapter
 * @Description : 탭 페이지 어뎁터
 * @since 2015. 6. 17.
 * @version 1.0
 * @author grapegirl
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    /** 탭 페이지 리스트 */
    private ArrayList<Fragment> mFagmentList = null;

    /**
     * 생성자
     */
    public TabPageAdapter(ArrayList<Fragment> fagmentList, FragmentManager fm) {
        super(fm);
        mFagmentList = fagmentList;
    }

    @Override
    public Fragment getItem(int index) {
        return mFagmentList.get(index);
    }

    @Override
    public int getCount() {
        return mFagmentList.size();
    }
}
