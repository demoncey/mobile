package com.example.massena.messages;

/**
 * Created by massena on 2017-05-20.
 */



import java.util.UUID;

public  class Msg {
    final private UUID uuid;
    final private Object data;
    protected TYPE type;
    private String source="NULLLLLLLLL";

    public enum TYPE{
        DEFAULT,
        TEST,
        LOG,
        COORDINATES,
        REST
    }

    public Msg(Object data){
        this.uuid=UUID.randomUUID();
        this.data=data;
        setType();
    }


    public Msg(UUID uuid,TYPE type,Object data,String source){
        this.uuid=uuid;
        this.data=data;
        this.type=type;
        this.source=source;
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
    public String getSource(){
        return this.source;
    }



}
