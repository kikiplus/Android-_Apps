package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.os.Bundle;
import android.widget.NumberPicker;

import com.kiki.View.R;
import com.kiki.android.Listener.UIEvent.OnPopupEventListener;

/**
 * Created by cs on 2015-10-08.
 */
public class TestActivity2 extends Activity implements NumberPicker.OnValueChangeListener {

    private NumberPicker mNumberPicker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        mNumberPicker = (NumberPicker)findViewById(R.id.test_number_picker);
        mNumberPicker.setOnValueChangedListener( this );
        mNumberPicker.setMinValue( 1 );
        mNumberPicker.setMaxValue( 10 );
        mNumberPicker.setOnLongPressUpdateInterval( 100 );
        mNumberPicker.setWrapSelectorWheel( true );
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }


}
