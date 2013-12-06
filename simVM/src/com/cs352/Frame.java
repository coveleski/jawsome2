package com.cs352;

public class Frame {

    //byte[] frame_memory;

    //frame's last access time
    private long time;
    //owning process' id
    private int owner_id = -1;

    //getter for time
    public long getTime() {
        return time;
    }
    //getter for id
    public int getOwnerID() {
        return owner_id;
    }
    //dead simple constructor
    public Frame(){
        //frame_memory = new byte[i];
    }
    //update the frame's owner and access time on access
    public void access(int id){
        time = System.currentTimeMillis();
        owner_id = id;
    }


}