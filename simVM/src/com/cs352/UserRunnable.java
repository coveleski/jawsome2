package com.cs352;

import java.io.*;

public class UserRunnable implements Runnable{

    int page_table[];
    Frame pages[];
    int id;
    String filename;


    private class Address{

        private int offset;
        private int page_number;

        private Address(int i){
            //get the exponents for the frame size
            int exp = SingletonContainer.getInstance().getFrame_size_exp();
            //offset is 2^pagesize bits
            this.offset = i & ((int) Math.pow(2, exp) -1);
            //page # is the rest, so we'll just
            // shift off the offset and call it good
            this.page_number = i >> (16-exp);
        }

        public int getPage_number() {
            return page_number;
        }

        public void setPage_number(int page_number) {
            this.page_number = page_number;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

    }

    public UserRunnable(int id){
        this.id = id;
        filename = "address_" + id +".txt";

        pages = null;


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
                int unprocessed_address = Integer.parseInt(s_address);
                Address address = new Address(unprocessed_address);
                lookup(address);




                //get next address
                s_address = in.readLine();
            }

            System.out.println("Process " + id + " ends.");
            //close streams
            in.close();
            fr.close();

        } catch (FileNotFoundException e) {
            System.out.println("error opening " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void lookup(Address address){

    }
}
