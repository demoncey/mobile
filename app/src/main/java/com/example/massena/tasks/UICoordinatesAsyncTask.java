package com.example.massena.tasks;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.massena.messages.CoordinatesMsg;


public class UICoordinatesAsyncTask extends AsyncTask implements Runnable ,View.OnClickListener {
    private final Handler handler;

    public UICoordinatesAsyncTask(Handler handler){
        this.handler=handler;
    }
    @Override
    public void run() {
        CoordinatesMsg msg=new CoordinatesMsg("UI UI UI"+this.toString());
        Message message = handler.obtainMessage();
        message.obj=msg;
        handler.sendMessage(message);
    }
    public void exec (){
        //now several task in parallel are working
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    public void onClick(View v) {
        this.exec();
    }
}
