package com.cs352;

public class simVM {

    /**
     expected args are, in order
     page frame size in bytes
     num pages for each process
     num frames in main mem
     num user processes
     */
    public static void main(String[] args) {
        //the number of user processes
        int user_processes;

	    //check for appropriate number of args
        if (args.length < 4){
            System.out.println("too few arguments");
            return;
        }
        try{
            //parse the input args as ints
            int frame_size = Integer.parseInt(args[0]);
            int pages_per_process = Integer.parseInt(args[1]);
            int main_mem_frames= Integer.parseInt(args[2]);
            user_processes= Integer.parseInt(args[3]);

            //set the input args to a container class
            //so that threads can access it
            SingletonContainer.getInstance().setFrame_size(frame_size);
            SingletonContainer.getInstance().setMain_mem_frames(main_mem_frames);
            SingletonContainer.getInstance().setPages_per_process(pages_per_process);
        }
        //catch exceptions
        catch(NumberFormatException e){
            System.out.println("one or more arguments are non-integer");
            return;
        }

        //make new threads and start them
        for (int i = 1; i <= user_processes; ++i){
            new Thread(new UserRunnable(i)).start();
        }
    }
}
