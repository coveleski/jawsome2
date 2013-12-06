package com.cs352;

public class Singleton {

    int frame_size;
    int pages_per_process;
    int main_mem_frames;

    private static Singleton instance = null;

    public int getFrame_size() {
        return frame_size;
    }

    public void setFrame_size(int frame_size) {
        this.frame_size = frame_size;
    }

    public int getPages_per_process() {
        return pages_per_process;
    }

    public void setPages_per_process(int pages_per_process) {
        this.pages_per_process = pages_per_process;
    }

    public int getMain_mem_frames() {
        return main_mem_frames;
    }

    public void setMain_mem_frames(int main_mem_frames) {
        this.main_mem_frames = main_mem_frames;
    }

    private Singleton(){

    }

    public static Singleton getInstance(){
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }



}
