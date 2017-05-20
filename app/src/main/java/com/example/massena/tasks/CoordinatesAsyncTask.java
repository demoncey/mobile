package com.example.massena.tasks;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.CoordinatesMsg;



public class CoordinatesAsyncTask extends AsyncTask implements Runnable {
    private final Handler handler;

    public CoordinatesAsyncTask(Handler handler){
        this.handler=handler;
    }
    @Override
    public void run() {
        CoordinatesMsg msg=new CoordinatesMsg(this.toString());
        Message message = handler.obtainMessage();
        message.obj=msg;
        handler.sendMessage(message);
    }
    public void exec (){
        this.execute(this);
    }
    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }
}
