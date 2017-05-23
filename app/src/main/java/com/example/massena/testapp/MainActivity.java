package com.example.massena.testapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.massena.messages.LogMsg;
import com.example.massena.messages.Msg;
import com.example.massena.tasks.CoordinatesAsyncTask;
import com.example.massena.tasks.ExtendedAsyncTask;
import com.example.massena.tasks.UiExtendedAsyncTask;
import com.example.massena.tasks.UICoordinatesAsyncTask;
import com.example.massena.tasks.gps.GpsService;
import com.example.massena.tasks.rest.AwsRestAsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    private String[] adapterData = new String[] { "Afghanistan", "Albania", "Algeria",
            "American Samoa", "Andorra", "Angola"};
    Button thread1;
    Button thread2;
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
        thread1= (Button) findViewById(R.id.thread1);
        thread2= (Button) findViewById(R.id.thread2);
        test= (Button) findViewById(R.id.button3);
        test2= (Button) findViewById(R.id.test2);
        request= (Button) findViewById(R.id.request);


        thread1.setOnClickListener(new View.OnClickListener() {
                boolean start = false;
                ExtendedAsyncTask task=null;
                @Override
                public void onClick(View v) {
                    if (!start) {
                        start = true;
                        tv.setText("");
                        thread1.setText("Stop");
                        task = new ExtendedAsyncTask(handler) {
                            @Override
                            public void doIt() {
                                Msg msg;
                                msg = new Msg(this.toString() + "ExtendedAsyncTask Pure task");
                                Message message = getHandler().obtainMessage();
                                message.obj = msg;
                                handler.sendMessage(message);
                                synchronized (this) {
                                    try {
                                        wait(1000);
                                    } catch (Exception e) {
                                    }
                                }


                            }
                        };
                        task.exec();
                    }else{
                        task.setRunning(false);
                        start = false;
                        tv.setText("Stopped");
                        thread1.setText("Start");
                    }
                }
        });

        thread2.setOnClickListener(new View.OnClickListener() {

            boolean start = false;
            AwsRestAsyncTask task=null;
            @Override
            public void onClick(View v) {
                if (!start){
                    start = true;
                    thread2.setText("Stop");
                    task=new AwsRestAsyncTask(handler);
                    task.exec();

                }else{
                    task.setRunning(false);
                    start = false;
                    thread2.setText("Start");
                }
            }
        });


        test.setOnClickListener( new UICoordinatesAsyncTask(handler));

        test2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new CoordinatesAsyncTask(handler).exec();
            }
        });

        request.setOnClickListener( new UiExtendedAsyncTask(handler){

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
