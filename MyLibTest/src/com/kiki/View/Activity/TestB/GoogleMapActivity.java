package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kiki.View.Bean.GpsLocation;
import com.kiki.View.R;
import com.kiki.android.Listener.IGPSReceive;
import com.kiki.android.Logic.Managers.GPSManager;
import com.kiki.android.Utils.ContextUtils;
import com.kiki.android.Utils.KLog;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * Created by cs on 2015-10-29.
 */
public class GoogleMapActivity extends Activity implements View.OnClickListener, IGPSReceive, OnMapReadyCallback {

    private String TAG = this.getClass().getSimpleName();

    private GPSManager mGpsMgr;

    private ToggleButton mToggleButton;

    private GoogleMap mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleamp_layout);

        mGpsMgr = new GPSManager(getApplicationContext(), this);
        mToggleButton = (ToggleButton) findViewById(R.id.google_map_button);
        mToggleButton.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGpsMgr.stopLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_map_button:
                if (mToggleButton.isChecked()) {
                    mGpsMgr.startLocation();
                } else {
                    mGpsMgr.stopLocation();
                }
                break;
        }
    }

    @Override
    public void onGpsReceive(int type, Object obj) {
        KLog.d(TAG, "@@ onGpsReceive type : " + type);
        switch (type){
            case RECEIVE_OK:
            case RECEIVE_UPDATE:
                GpsLocation location = (GpsLocation)obj;
                if(location != null){
                    double locationLatitude = location.getLatitude();
                    double locationLongitude = location.getLongitude();
                    LatLng latLng = new LatLng(locationLatitude, locationLongitude);
                    mapView.addMarker(new MarkerOptions().position(latLng));
                }
                break;
            case RECEIVE_FAIL:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView = googleMap;
    }
}
