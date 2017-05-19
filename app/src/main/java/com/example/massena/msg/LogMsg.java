package com.example.massena.msg;

/**
 * Created by massena on 2017-05-20.
 */

public class LogMsg extends Msg {
    public LogMsg(String msg) {
        super(msg);
    }
    @Override
    public void setType(){
        this.type=TYPE.LOG;
    }
}
