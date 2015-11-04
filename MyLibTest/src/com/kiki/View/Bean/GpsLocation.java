package com.kiki.View.Bean;

/**
 * Created by cs on 2015-10-29.
 */
public class GpsLocation {

    private double mLatitude;
    private double mLongitude;

    public GpsLocation() {
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
         mLongitude = longitude;
    }

}
