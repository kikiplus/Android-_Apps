package com.kiki.View.Activity.TestB;

import android.app.Activity;
import android.app.FragmentManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kiki.View.Bean.GpsLocation;
import com.kiki.View.R;
import com.kiki.android.Listener.IGPSReceive;
import com.kiki.android.Logic.Managers.GPSManager;
import com.kiki.android.Utils.AppUtils;
import com.kiki.android.Utils.KLog;

/**
 * Created by cs on 2015-10-29.
 */
public class GoogleMapActivity2 extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback {

    private String TAG = this.getClass().getSimpleName();

    private GPSManager mGpsMgr;

    private ToggleButton mToggleButton;

    private GoogleMap mGoogleMap;

    private MapView mMapView;

    private Location mLocation;

    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

    private final long MIN_TIMEE_CHANGE_FOR_UPDATES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleamp_layout);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        mToggleButton = (ToggleButton) findViewById(R.id.google_map_button);
        mToggleButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setMarker(double latitude, double longitude) {
        AppUtils.toast(getApplicationContext(), "setMarker");
        AppUtils.toast(getApplicationContext(), "setMarker latitude: " + latitude);
        AppUtils.toast(getApplicationContext(), "setMarker longitude: " + longitude);
        double locationLatitude = latitude;
        double locationLongitude = longitude;
        LatLng latLng = new LatLng(locationLatitude, locationLongitude);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng));
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(latLng)
                .build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        Toast.makeText(this, "위치 찾음요 : " + locationLatitude + ", " + locationLongitude, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        AppUtils.toast(this, "onClick");
        switch (view.getId()) {
            case R.id.google_map_button:
                if (mToggleButton.isChecked()) {
                    AppUtils.toast(this, "startLocation");
                    getLocation();
                } else {
                    AppUtils.toast(this, "stopLocation");
                    stopLocation();
                }
                break;
        }
    }

    /**
     * 내 위치 가져오기
     */
    private void getLocation() {
        AppUtils.toast(getApplicationContext(), "getLocation");
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGps) {
            if (locationManager != null) {
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        } else {
            boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (locationManager != null) {
                mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if (mLocation != null) {
            setMarker(mLocation.getLatitude(), mLocation.getLongitude());
        } else {
            AppUtils.toast(getApplicationContext(), "위치 정보가 없네용");
        }
    }

    /**
     * 내 위치 가져오기 중지 메소드
     */
    private void stopLocation() {
        AppUtils.toast(getApplicationContext(), "stopLocation");
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationManager.removeUpdates(mLocationListener);
    }

    /**
     * 내위치 정보 리스너
     */
    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            AppUtils.toast(getApplicationContext(), "onLocationChanged");
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Toast.makeText(getApplicationContext(), "위치정보 값 변경 : " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
            setMarker(latitude, longitude);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "GPS 상태 변경 : " + status, Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS 상태 가능 : " + provider, Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "GPS 상태 불가능 : " + provider, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        AppUtils.toast(this, "onMapReady");
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
