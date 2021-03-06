package com.example.massena.tasks.gps;

/**
 * Created by massena on 2017-05-20.
 */


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.massena.messages.MessageBuilder;

import android.support.v4.app.ActivityCompat;
import android.util.Log;


public class CoordinatesAsyncTask extends AsyncTask implements LocationListener {
    private final Handler handler;
    private final Context context;
    private boolean isNetworkEnabled = false;
    private boolean isGpsEnabled = false;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    public CoordinatesAsyncTask(Handler handler, Context context) {

        this.handler = handler;
        this.context = context;
    }


    public void exec() {
        this.execute(this);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Message message;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (isNetworkEnabled) {

            Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
            Log.i(this.toString(),"Network is enabled");
            Log.i(this.toString(),"------------------------------------------------------------------------------------------------");

            message = new MessageBuilder(handler).setCoordinateType().setData("YYYYY network localization enabled").setSource(this.toString()).builMessage();
            handler.sendMessage(message);
        }
        if (isGpsEnabled) {
            Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
            Log.i(this.toString(),"Gps is enabled");
            Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
            message = new MessageBuilder(handler).setCoordinateType().setData("YYYYY gps localization enabled").setSource(this.toString()).builMessage();
            handler.sendMessage(message);


            if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                Log.i(this.toString(),"Problem with permission");
                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                return 0;
            }
            Looper.prepare();
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation==null ){
                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                Log.i(this.toString(),"NULL NULL NULL NULL NULL");
                Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
                message = new MessageBuilder(handler).setCoordinateType().setData("CHUJOZA").setSource(this.toString()).builMessage();
                handler.sendMessage(message);
                return  null;
            }

            Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
            Log.i(this.toString(),Double.toString(lastKnownLocation.getLongitude()));
            Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
            message = new MessageBuilder(handler).setCoordinateType().setData(lastKnownLocation).setSource(this.toString()).builMessage();
            handler.sendMessage(message);

        }







//        Message message =new MessageBuilder(handler).setCoordinateType().setData(Boolean.toString(isNetworkEnabled)+Boolean.toString(isGpsEnabled)).setSource(this.toString()).builMessage();
//        handler.sendMessage(message);
//        Log.i(this.toString(),"Get Coordinates done\n");
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(this.toString(),"------------------------------------------------------------------------------------------------");
        Log.i(this.toString(),"LOCATION CHANGED XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
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
}
