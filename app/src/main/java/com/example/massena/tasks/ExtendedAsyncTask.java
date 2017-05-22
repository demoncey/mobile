package com.example.massena.tasks;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.massena.messages.CoordinatesMsg;


public class ExtendedAsyncTask extends AsyncTask implements View.OnClickListener {
    private final Handler handler;

    public ExtendedAsyncTask(Handler handler){
        this.handler=handler;
    }

    public ExtendedAsyncTask(){
        this.handler=null;
    }

    public void exec (){
        //now several task in parallel are working
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @Override
    protected Object doInBackground(Object[] params) {
        //something like run put your code here
        return null;
    }

    @Override
    public void onClick(View v) {
        this.exec();
    }

    public Handler getHandler(){
        return this.handler;
    }
}
