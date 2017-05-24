package com.example.massena.tasks.gps;

/**
 * Created by massena on 2017-05-20.
 */


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.MessageBuilder;

import android.util.Log;



public class CoordinatesAsyncTask extends AsyncTask implements LocationListener {
    private final Handler handler;
    private final Context context;
    private boolean isNetworkEnabled=false;

    public CoordinatesAsyncTask(Handler handler,Context context){

        this.handler=handler;
        this.context=context;
    }


    public void exec (){
        this.execute(this);
    }
    @Override
    protected Object doInBackground(Object[] params) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(isNetworkEnabled){
            Message message =new MessageBuilder(handler).setCoordinateType().setData("network localization enabled").setSource(this.toString()).builMessage();
            handler.sendMessage(message);
        }






        Message message =new MessageBuilder(handler).setCoordinateType().setData(locationManager.toString()).setSource(this.toString()).builMessage();
        handler.sendMessage(message);
        Log.i(this.toString(),"Get Coordinates done\n");
        return null;
    }

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
}
