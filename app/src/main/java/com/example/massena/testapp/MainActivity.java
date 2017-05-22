package com.example.massena.testapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.massena.messages.LogMsg;
import com.example.massena.messages.Msg;
import com.example.massena.tasks.CoordinatesAsyncTask;
import com.example.massena.tasks.ExtendedAsyncTask;
import com.example.massena.tasks.UICoordinatesAsyncTask;
import com.example.massena.tasks.gps.GpsService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    private String[] adapterData = new String[] { "Afghanistan", "Albania", "Algeria",
            "American Samoa", "Andorra", "Angola"};
    Button button;
    Button log;
    Button test;
    Button test2;
    Button request;
    TextView tv;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final GpsService gps = new GpsService(this);

        setContentView(R.layout.activity_main);
        tv =(TextView) findViewById(R.id.textView);
        final Handler  handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Msg message =(Msg) msg.obj;
                if(message.getType()== Msg.TYPE.TEST) {
                    tv.append("TEST "+message.getUUID() + "-" + message.getData().toString() + "\n");
                }
                if(message.getType()== Msg.TYPE.LOG) {
                    tv.append("LOG "+message.getUUID() + "-" + (String) message.getData() + "\n");
                }
                if(message.getType()== Msg.TYPE.COORDINATES) {
                    tv.append("COORDINATES "+message.getUUID() + "-" + (String) message.getData() + "\n");
                }
            }
        };
        button= (Button) findViewById(R.id.button);
        log= (Button) findViewById(R.id.log);
        test= (Button) findViewById(R.id.button3);
        test2= (Button) findViewById(R.id.test2);
        request= (Button) findViewById(R.id.request);



        button.setOnClickListener(new ExtendedAsyncTask(handler){

            boolean start= false;

            @Override
            protected Object doInBackground(Object[] params) {
                while(start) {
                    Msg msg;
                    if (gps.location !=null){
                        msg=new Msg(gps.location.toString());

                    }else{
                        msg=new Msg(this.toString()+"ExtendedAsyncTask");
                    }
                    Message message = handler.obtainMessage();
                    message.obj=msg;
                    handler.sendMessage(message);
                    synchronized (this) {
                    try {
                        wait(1000);
                    } catch (Exception e) {}
                    }
                }
                return null;
            }

            @Override
            public void onClick(View v) {
                if(!start){
                    start=true;
                    tv.setText("");
                    this.exec();
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

        test.setOnClickListener( new UICoordinatesAsyncTask(handler));

        test2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new CoordinatesAsyncTask(handler).exec();
            }
        });

        request.setOnClickListener( new ExtendedAsyncTask(handler){

            @Override
            protected Object doInBackground(Object[] params) {
                LogMsg msg=new LogMsg("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                Message message = getHandler().obtainMessage();
                message.obj=msg;
                handler.sendMessage(message);
                return null;
            }
        });
    }


}
