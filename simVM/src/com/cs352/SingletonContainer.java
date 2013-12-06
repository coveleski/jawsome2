package com.cs352;

import java.util.concurrent.Semaphore;

public class SingletonContainer {
    //the frame size
    private int frame_size;

    private int frame_size_exp;
    //the number of pages per process
    private int pages_per_process;
    //the number of frames in main mem
    private int main_mem_frames;
    //an array of frames making up main mem
    public Frame main_mem[];
    //a semaphore to lock access to main mem
    public Semaphore semaphore = new Semaphore(1);
    //an object of SingletonContainer for Singleton pattern
    private static SingletonContainer instance = null;
    //boring constructor
    private SingletonContainer(){
    }

    //frame_size getter
    public int getFrame_size_exp() {
        return frame_size_exp;
    }
    //frame_size setter
    public void setFrame_size(int frame_size) {
        this.frame_size = frame_size;
        //also calculates log2(frame_size) and sets it
        this.frame_size_exp = findExponent(frame_size);
    }
    //pages_per_process getter
    public int getPages_per_process() {
        return pages_per_process;
    }
    //sets pages_per_process
    public void setPages_per_process(int pages_per_process) {
        this.pages_per_process = pages_per_process;
    }
    //sets main_mem_frames
    public void setMain_mem_frames(int main_mem_frames) {
        //set main_mem_frames
        this.main_mem_frames = main_mem_frames;

        //initializes main_mem_size and fills with with empty frames
        this.main_mem = new Frame[main_mem_frames];
        for (int i = 0; i < main_mem_frames; ++i){
            main_mem[i] = new Frame();
        }
    }
    //calculates log2(i)
    private int findExponent(int i){
        //yeah it's not exactly the fastest but it only runs once
        return (int) Math.ceil(Math.log(i) / Math.log(2));
    }
    //swaps frames in main mem
    int swapFrame(int id){
        //the time of the oldest frame in main
        long oldest_time = -1;
        //the index of the oldest frame in main mem
        int oldest_index = -1;
        //search through main mem for empty frame
        for (int i = 0; i < main_mem.length; ++i){
            //store the current frame
            Frame cur = main_mem[i];
            //swap frames if empty
            if (cur.getOwnerID() == -1){
                //mark as accessed by the passed process id
                cur.access(id);
                //log swap in console
                System.out.printf("Process %d finds a free frame in main memory (frame number = %d)\n", id, i);

                //return the frame number of the new frame
                //to update page table
                return i;
            } else {
                //if the frame is not empty, compare and record oldest frame
                if (cur.getTime() < oldest_time){
                    oldest_time = cur.getTime();
                    oldest_index = i;
                }
            }
        }
        //if no empty frames found, swap with oldest frame
        main_mem[oldest_index].access(id);

        //log swap in console
        System.out.printf("Process %d replaces a frame from main memory (frame number = %d)\n", id, oldest_index);

        //return the frame number of the new frame
        //to update page table
        return oldest_index;
    }
    //make SingletonContainer a singleton pattern
    public static SingletonContainer getInstance(){
        if (instance == null){
            instance = new SingletonContainer();
        }
        return instance;
    }
}
