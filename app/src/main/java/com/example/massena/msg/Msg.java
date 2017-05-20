package com.example.massena.msg;

/**
 * Created by massena on 2017-05-20.
 */



import java.util.UUID;

public  class Msg {
    final private UUID uuid;
    final private Object data;
    protected TYPE type;

    public enum TYPE{
        TEST,
        LOG,
        COORDINATES
    }

    public Msg(Object data){
        this.uuid=UUID.randomUUID();
        this.data=data;
        setType();
    }

    public void setType(){
        this.type=TYPE.TEST;
    }
    public String getUUID(){
        return uuid.toString();
    }

    public Object getData(){
        return data;
    }

    public TYPE getType(){
        return type;
    }



}
