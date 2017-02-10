package com.kikiplus.View.UIFrame;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kikiplus.View.Bean.Book;
import com.kikiplus.View.R;


/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : DetailFragement
 * @Description : 상세 플래그먼트
 * @since 2015. 6. 28.
 */
public class DetailFragement extends Fragment {

    public static DetailFragement newInstance(int position) {
        DetailFragement mInstance = new DetailFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    public int getPosition() {
        return getArguments().getInt("position", 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transversemode_detail_layout, container, false);
        TextView textView = (TextView) view.findViewById(R.id.transversemode_details_textview);
        textView.setText(Book.DETAILS[getPosition()]);
        return view;
    }
}
