package com.cs352;

import java.util.Stack;

public class Frame {

    byte[] frame_memory;

    private long time;

    int owner_id;

    public long getTime() {
        return time;
    }

    public int getOwnerID() {
        return owner_id;
    }

    public Frame(int i){

        frame_memory = new byte[i];


    }

    public void access(int id){
        time = System.currentTimeMillis();
        owner_id = id;
    }


}