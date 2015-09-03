package com.kiki.View.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.kiki.View.R;
import com.kiki.View.UIComfonent.ArrayListFragement;

import com.kiki.android.Listener.UIEvent.OnListItemSelectedListener;

/***
 * @Class Name :ListFragementTest
 * @Description : 리스트 프레그먼트 클래스
 * @since 2015. 6. 28.
 * @version 1.0
 * @author grapegirl
 */
public class ListFragementTest extends Activity implements OnListItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listfragment_main_layout);

        ArrayListFragement list = new ArrayListFragement();

//        액티비티에서 값을 전달할때
//        String[] numbers = new String[]{"1","2","3","4","5","6","7","8"};
//
//        Bundle bundle = new Bundle();
//        bundle.putStringArray("numbers", numbers);
//        list.setArguments(bundle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, list);
        ft.commit();

    }

    @Override
    public void onListItemSelected(String itemName) {
        setTitle(getTitle() + itemName);
    }
}
