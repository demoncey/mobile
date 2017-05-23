package com.example.massena.testapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.massena.messages.MessageBuilder;
import com.example.massena.messages.Msg;
import com.example.massena.tasks.CoordinatesAsyncTask;
import com.example.massena.tasks.ExtendedAsyncTask;
import com.example.massena.tasks.gps.GpsService;
import com.example.massena.tasks.rest.AwsRestAsyncTask;




public class MainActivity extends AppCompatActivity {
    private String[] adapterData = new String[] { "Afghanistan", "Albania", "Algeria",
            "American Samoa", "Andorra", "Angola"};
    Button thread1;
    Button thread2;
    Button getposition;
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
                tv.append(message.getType().name()+":"+message.getSource() + ":" + message.getData().toString() + "\n");
            }
        };
        thread1= (Button) findViewById(R.id.thread1);
        thread2= (Button) findViewById(R.id.thread2);
        getposition= (Button) findViewById(R.id.getposition);

        

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
                                Message message =new MessageBuilder(this.getHandler()).setDefaultType().setData("IT is test data from Task").setSource(this.toString()).builMessage();
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

        getposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoordinatesAsyncTask task=new CoordinatesAsyncTask(handler);
                task.exec();
            }
        });

    }


}
