package com.example.massena.tasks;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.CoordinatesMsg;



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
        CoordinatesMsg msg=new CoordinatesMsg("NO LISTENER"+this.toString());
        Message message = handler.obtainMessage();
        message.obj=msg;
        handler.sendMessage(message);
        return null;
    }
}
