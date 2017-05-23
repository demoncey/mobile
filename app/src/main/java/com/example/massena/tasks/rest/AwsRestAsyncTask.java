package com.example.massena.tasks.rest;

import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.Msg;
import com.example.massena.tasks.ExtendedAsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by massena on 2017-05-23.
 */

public class AwsRestAsyncTask extends ExtendedAsyncTask {

    private final String url="https://mfqlu2s2ga.execute-api.eu-west-1.amazonaws.com/prod/service";

    public AwsRestAsyncTask(Handler handler){
        super(handler);
    }

    @Override
    public void doIt(){
        Msg msg;
        try {
            URL service = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) service.openConnection();
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            Message message = getHandler().obtainMessage();

            if(connection.getResponseCode()==200){
                msg = new Msg("200 200 200 200");

            }else{
                msg = new Msg(this.toString() + "not 200 not 200 not 200 not 200");
            }
            message.obj = msg;
            getHandler().sendMessage(message);

        } catch  (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        synchronized (this) {
            try {
                wait(1000);
            } catch (Exception e) {
            }
        }



    }
}
