package com.cs352;

import java.io.*;
import java.util.Map;

/**
 * Created by David on 12/4/13.
 */
public class UserRunnable implements Runnable{

    int page_table[];
    int id;
    String filename;

    public UserRunnable(int id){
        this.id = id;
        filename = "address_" + id +".txt";
    }
    public void run(){
        //open the appropriate file
        try {
            //open the file and prep for reading
            FileReader fr = new FileReader(new File(filename));
            BufferedReader in = new BufferedReader(fr);
            //get the initial  address from the file
            String s_address = in.readLine();
            //while the file end is not reached
            while (s_address != null){
                //convert address to int
                int address = Integer.parseInt(s_address);




                //get next address
                s_address = in.readLine();
            }



            in.close();
            fr.close();

        } catch (FileNotFoundException e) {
            System.out.println("error opening " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
