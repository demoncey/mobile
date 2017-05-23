package com.example.massena.tasks.gps;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.MessageBuilder;


public class CoordinatesAsyncTask extends AsyncTask {
    private final Handler handler;

    public CoordinatesAsyncTask(Handler handler){
        this.handler=handler;
    }
    public void exec (){
        this.execute(this);
    }
    @Override
    protected Object doInBackground(Object[] params) {

        Message message =new MessageBuilder(handler).setCoordinateType().setData("Coordinates are XXXX YYYY").setSource(this.toString()).builMessage();
        handler.sendMessage(message);
        return null;
    }
}
