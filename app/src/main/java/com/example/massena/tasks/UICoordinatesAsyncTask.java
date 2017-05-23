package com.example.massena.tasks;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.massena.messages.MessageBuilder;


public class UICoordinatesAsyncTask extends AsyncTask implements View.OnClickListener {
    private final Handler handler;

    public UICoordinatesAsyncTask(Handler handler){
        this.handler=handler;
    }

    public void exec (){
        //now several task in parallel are working
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @Override
    protected Object doInBackground(Object[] params) {
        Message message =new MessageBuilder(handler).setCoordinateType().setData("Coordinates are X Y").setSource(this.toString()).builMessage();
        handler.sendMessage(message);
        return null;
    }

    @Override
    public void onClick(View v) {
        this.exec();
    }
}
