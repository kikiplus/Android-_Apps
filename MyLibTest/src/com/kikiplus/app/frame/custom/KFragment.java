package com.kikiplus.app.frame.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kikiplus.app.R;

/***
 * @author grapegirl
 * @version 1.1
 * @Class Name : KFragment
 * @Description : Fragment 상속받은 Custom Fragement
 * @since 2017. 02. 11.
 */
public class KFragment extends Fragment {

    private String mTitle = null;

    public KFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab1, null);
        return view;
    }

    @Override
    public View getView() {
        return super.getView();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;

    }
}
