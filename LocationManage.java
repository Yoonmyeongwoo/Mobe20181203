package com.example.neighbor07.mobe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/*
* Owner YMW
* 단말기 위치정보 추출 및 갱신 (X,Y) = 유저 위치
* 위치정보 갱신 기준 1시간/50미터
* SernsorManage class 에 사용
* */

public class LocationManage extends Activity{
    Context mcontext =null;


    boolean isGPSEnabled;
    boolean isNetworkEnabled;

    public LocationManager locationManager(Context applicationContext) {
        this.mcontext=applicationContext;

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

             return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 50, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100000,50,locationListener);

        return ((SensorManage)mcontext).locationManager(applicationContext);
    }
}
