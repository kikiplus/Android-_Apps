package com.kikiplus.View.UIComfonent;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kikiplus.android.Listener.UIEvent.OnListItemSelectedListener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : ArrayListFragement
 * @Description : 리스트 프레그먼트
 * @since 2015. 6. 28.
 */
public class ArrayListFragement extends ListFragment {

    private static String[] NUMBERS = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

    private OnListItemSelectedListener mOnListItemSelectedListener = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, NUMBERS));
//        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, getNumbers()));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getListView().setItemChecked(position, true);
        mOnListItemSelectedListener.onListItemSelected(NUMBERS[position]);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mOnListItemSelectedListener = (OnListItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implements OnListItemSelectedListener interface");
        }
    }

    public String[] getNumbers() {
        return getArguments().getStringArray("numbers");
    }
}
