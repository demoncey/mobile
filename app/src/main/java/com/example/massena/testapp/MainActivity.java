package com.example.massena.testapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.massena.msg.LogMsg;
import com.example.massena.msg.Msg;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    Button button;
    Button log;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv =(TextView) findViewById(R.id.textView);
        final Handler  handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Msg message =(Msg) msg.obj;
                if(message.getType()== Msg.TYPE.TEST) {
                    tv.append("TEST "+message.getUUID() + "-" + message.getMessage() + "\n");
                }
                if(message.getType()== Msg.TYPE.LOG) {
                    tv.append("LOG "+message.getUUID() + "-" + message.getMessage() + "\n");
                }

            }
        };
        button= (Button) findViewById(R.id.button);
        log= (Button) findViewById(R.id.log);
        button.setOnClickListener(new View.OnClickListener(){

            boolean start= false;
            @Override
            public void onClick(View v) {
                if(!start){
                    start=true;
                    tv.setText("");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(start) {
                                Msg msg=new Msg(this.toString());
                                Message message = handler.obtainMessage();
                                message.obj=msg;
                                handler.sendMessage(message);
                                synchronized (this) {
                                    try {
                                        wait(5000);
                                    } catch (Exception e) {}
                                }
                            }

                        }
                    }).start();
                    button.setText("Stop");
                }else{

                    start=false;
                    tv.setText("Stopped");
                    button.setText("Start");

                }
            }
        });


        log.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable(){

                    @Override
                    public void run() {



                        try {
                            URL  githubEndpoint = new URL("https://api.github.com/");
                            HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
                            if (myConnection.getResponseCode() == 200) {

                                InputStream responseBody = myConnection.getInputStream();
                                InputStreamReader responseBodyReader =
                                        new InputStreamReader(responseBody, "UTF-8");

                                LogMsg msg=new LogMsg(responseBodyReader.toString());
                                Message message = handler.obtainMessage();
                                message.obj=msg;
                                handler.sendMessage(message);
                            } else {
                                // Error handling code goes here
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        LogMsg msg=new LogMsg(this.toString());
                        Message message = handler.obtainMessage();
                        message.obj=msg;
                        handler.sendMessage(message);

                    }
                });
            }
        });

    }


}
