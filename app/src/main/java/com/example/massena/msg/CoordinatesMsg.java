package com.example.massena.msg;

/**
 * Created by massena on 2017-05-20.
 */

public class CoordinatesMsg extends Msg {
    public CoordinatesMsg(String msg) {
        super(msg);
    }
    @Override
    public void setType(){
        this.type=TYPE.COORDINATES;
    }
}




