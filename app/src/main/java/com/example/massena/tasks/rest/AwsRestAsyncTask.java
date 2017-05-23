package com.example.massena.tasks.rest;

import android.os.Handler;
import android.os.Message;

import com.example.massena.messages.MessageBuilder;
import com.example.massena.messages.Msg;
import com.example.massena.tasks.ExtendedAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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



                InputStreamReader in = new InputStreamReader((InputStream) connection.getContent());
                BufferedReader buff = new BufferedReader(in);
                String line;
                String text="";
                StringBuilder sb= new StringBuilder();
                do {
                    line = buff.readLine();
                    sb.append(line);
                } while (line != null);


                JSONObject json = new JSONObject(sb.toString());
                message =new MessageBuilder(this.getHandler()).setType(Msg.TYPE.REST).setData(json).setSource(this.toString()).builMessage();

            }else{
                message =new MessageBuilder(this.getHandler()).setRestType().setData("Rest respond with error").setSource(this.toString()).builMessage();
            }
            getHandler().sendMessage(message);

        } catch  (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
