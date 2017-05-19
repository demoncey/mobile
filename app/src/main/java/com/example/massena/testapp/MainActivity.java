package com.example.massena.testapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.massena.msg.LogMsg;
import com.example.massena.msg.Msg;

import java.lang.Thread;


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
                    tv.append("Test "+message.getUUID() + "-" + message.getMessage() + "\n");
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
                                Msg msg=new Msg("Przemek");
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
                LogMsg msg=new LogMsg("Alleluja");
                Message message = handler.obtainMessage();
                message.obj=msg;
                handler.sendMessage(message);

            }
        });

    }


}
