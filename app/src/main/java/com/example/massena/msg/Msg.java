package com.example.massena.msg;

/**
 * Created by massena on 2017-05-20.
 */



import java.util.UUID;

public  class Msg {
    final private UUID uuid;
    final private String msg;
    protected TYPE type;

    public enum TYPE{
        TEST,
        LOG
    }

    public Msg(String msg){
        this.uuid=UUID.randomUUID();
        this.msg=msg;
        setType();
    }

    public void setType(){
        this.type=TYPE.TEST;
    }
    public String getUUID(){
        return uuid.toString();
    }

    public String getMessage(){
        return msg;
    }

    public TYPE getType(){
        return type;
    }



}
