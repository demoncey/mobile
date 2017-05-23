package com.example.massena.tasks.rest;

import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.MessageBuilder;
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
            Message message;

            if(connection.getResponseCode()==200){
               message =new MessageBuilder(this.getHandler()).setType(Msg.TYPE.REST).setData("Rest respond with 200").setSource(this.toString()).builMessage();

            }else{
                message =new MessageBuilder(this.getHandler()).setRestType().setData("Rest respond with error").setSource(this.toString()).builMessage();
            }
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
