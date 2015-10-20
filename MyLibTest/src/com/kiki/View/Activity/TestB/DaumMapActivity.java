package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.kiki.View.R;
import com.kiki.android.Utils.ContextUtils;

import net.daum.mf.map.api.MapView;


/**
 *
 */
public class DaumMapActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daumamp_layout);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(ContextUtils.KEY_DAUM_MAP_KEY);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

    }
}
