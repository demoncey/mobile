package com.example.massena.tasks;

/**
 * Created by massena on 2017-05-20.
 */


import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;


public class UiExtendedAsyncTask extends AsyncTask implements View.OnClickListener {
    private final Handler handler;
    private boolean running=false;


    public void setRunning(boolean flag){
        running=flag;


    }

    private boolean isRunning(){
        return this.running;
    }

    public UiExtendedAsyncTask(Handler handler){
        this.handler=handler;
    }

    public UiExtendedAsyncTask(){
        this.handler=null;
    }

    public void exec (){
        //now several task in parallel are working
        this.setRunning(true);
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @Override
    protected Object doInBackground(Object[] params) {
        //something like run put your code here
        while(isRunning()){
            doIt();
        }
        return null;
    }

    public void doIt(){


    }

    @Override
    public void onClick(View v) {
        this.exec();
    }

    public Handler getHandler(){
        return this.handler;
    }
}
