package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.kiki.View.R;

import net.daum.mf.map.api.MapView;


/**
 * Created by cs on 2015-10-08.
 */
public class DaumMapActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daumamp_layout);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("783e2fdef22927878490302ecfc58a52");

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

    }
}
