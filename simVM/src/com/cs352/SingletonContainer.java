package com.cs352;

public class SingletonContainer {

    private int frame_size;
    private int frame_size_exp;
    private int pages_per_process;
    private int pages_per_process_exp;
    private int main_mem_frames;
    private int main_mem_frames_exp;

    public Frame main_mem[];

    private static SingletonContainer instance = null;

    private SingletonContainer(){

    }

    public int getPages_per_process_exp() {
        return pages_per_process_exp;
    }

    public int getMain_mem_frames_exp() {
        return main_mem_frames_exp;
    }

    public int getFrame_size_exp() {
        return frame_size_exp;
    }

    public int getFrame_size() {
        return frame_size;
    }

    public void setFrame_size(int frame_size) {
        this.frame_size = frame_size;
        this.frame_size_exp = findExponent(frame_size);
    }

    public int getPages_per_process() {
        return pages_per_process;
    }

    public void setPages_per_process(int pages_per_process) {
        this.pages_per_process = pages_per_process;
        this.pages_per_process_exp = findExponent(pages_per_process);
    }

    public int getMain_mem_frames() {
        return main_mem_frames;
    }

    public void setMain_mem_frames(int main_mem_frames) {
        this.main_mem_frames = main_mem_frames;
        this.main_mem_frames_exp = findExponent(main_mem_frames);

        this.main_mem = new Frame[main_mem_frames];
        for (int i = 0; i < main_mem_frames; ++i){
            main_mem[i] = new Frame(this.frame_size);
        }
    }

    private int findExponent(int i){
        return (int) Math.ceil(Math.log(i) / Math.log(2));
    }

    public static SingletonContainer getInstance(){
        if (instance == null){
            instance = new SingletonContainer();
        }
        return instance;
    }



}
