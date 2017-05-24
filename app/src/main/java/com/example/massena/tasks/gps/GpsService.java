package com.example.massena.tasks.gps;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by massena on 2017-05-20.
 */

public class GpsService extends Service implements LocationListener {

    private final Context context;
    // Declaring a Location Manager
    private LocationManager locationMgr;

    private boolean isNetworkEnabled = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute



    public Location location =null;

    public GpsService(Context context) {
        this.context = context;
        getCoordinates();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
        Log.i(this.toString(),"GPS SERVICE IS WORKING CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        Log.i(this.toString(),"------------------------------------------------------------------------------------------------");

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

    Location getCoordinates() {

        locationMgr = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        isNetworkEnabled = locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission((Activity)this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission((Activity)this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                Log.i(this.toString(),"WTF????????????????????????????????????????????");
                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                return null;
            }
            locationMgr.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if (locationMgr != null) {
                location = locationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                Log.i(this.toString(),"GPS SERVICE IS WORKING");
                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");

            }



        }

        return location;
    }

}
