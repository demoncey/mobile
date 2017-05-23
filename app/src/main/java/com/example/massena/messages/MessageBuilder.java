package com.example.massena.messages;

import android.os.Handler;
import android.os.Message;

import java.util.UUID;

/**
 * Created by massena on 2017-05-23.
 */

public class MessageBuilder  {
    final private UUID uuid;
    private String source;
    private Object data =null;
    private Msg.TYPE type =Msg.TYPE.DEFAULT;

    final private Handler handler;

    public MessageBuilder(Handler handler){
        this.uuid=UUID.randomUUID();
        this.handler=handler;
    }


    public MessageBuilder setData(Object data){
        this.data=data;
        return this;
    }


    public MessageBuilder setType(Msg.TYPE type){
        this.type=type;
        return this;
    }



    public MessageBuilder setSource(String source){
        this.source=source;
        return this;
    }

    public MessageBuilder setRestType(){
        this.type=Msg.TYPE.REST;
        return this;
    }

    public MessageBuilder setTestType(){
        this.type=Msg.TYPE.TEST;
        return this;
    }


    public MessageBuilder setDefaultType(){
        this.type=Msg.TYPE.DEFAULT;
        return this;
    }



    public MessageBuilder setCoordinateType(){
        this.type=Msg.TYPE.COORDINATES;
        return this;
    }


    public Message builMessage(){
        Message message=this.handler.obtainMessage();
        message.obj=new Msg(this.uuid,this.type,this.data,this.source);
        return message;
    }
}
