package com.cs352;

import java.io.*;

public class UserRunnable implements Runnable {

    int page_table[];
    int id;
    String filename;

    //here's an inner class to turn integers from
    // the address_n file into a page# and offset
    private class Address {
        //the offset
        private int offset;
        //the page number
        private int page_number;
        //the full address integer
        private int full;
        //constructor
        private Address(int i) {
            full = i;
            //get the exponents for the frame size
            int exp = SingletonContainer.getInstance().getFrame_size_exp();
            //offset is rightmost 2^pagesize bits
            this.offset = i & ((int) Math.pow(2, exp) - 1);
            //page # is the rest, so we'll just
            // shift off the offset and call it good
            this.page_number = i >> (exp);
        }
        //toString method override, for printing purposes.
        public String toString() {

            return Integer.toString(full);

        }

    }
    //a runnable, to do threading memory access things
    public UserRunnable(int id) {
        //the thread's id
        this.id = id;
        //the filename to open addresses from
        filename = "address_" + id + ".txt";
        //the pagetable, size defined by args
        page_table = new int[SingletonContainer.getInstance().getPages_per_process()];
        //initialize page_table entries to -1
        for (int i = 0; i < page_table.length; ++i) {
            page_table[i] = -1;
        }
    }

    //the things the thread does when it runs
    public void run() {

        try {
            //open the file and prep for reading
            FileReader fr = new FileReader(new File(filename));
            BufferedReader in = new BufferedReader(fr);
            //get the initial  address from the file
            String s_address = in.readLine();
            //while the file end is not reached
            while (s_address != null) {
                //convert address to int
                int unprocessed_address = Integer.parseInt(s_address);
                Address address = new Address(unprocessed_address);
                //lookup the address in the pagetable
                lookup(address);
                //get next address
                s_address = in.readLine();
            }
            //thread ending stuff
            System.out.println("Process " + id + " ends.");
            //close streams
            in.close();
            fr.close();
        //catch some exceptions that shouldn't happen
        } catch (FileNotFoundException e) {
            //be a little lazy and don't really deal with them
            System.out.println("error opening " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //looks up the address in main mem
    private void lookup(Address address) {

        //get the frame number from the page table
        int frame_num = page_table[address.page_number];
        //get a lock to access main mem
        try {
            SingletonContainer.getInstance().semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //if the entry is set in the page table and the frame is owned by this process id
        //hoorah, no page fault
        if (frame_num != -1 && SingletonContainer.getInstance().main_mem[frame_num].getOwnerID() == this.id) {
            SingletonContainer.getInstance().main_mem[frame_num].access(id);
            System.out.printf("Process %d accesses address %s(page number = %d, page offset = %d) in main memory(frame number = %d)\n", id, address, address.page_number, address.offset, frame_num);
            //release the lock
            SingletonContainer.getInstance().semaphore.release();
            return;
        } else {
            //if the entry is still in the page table
            // but no longer owned by this process, reset entry in table
            if (frame_num != -1){
                page_table[address.page_number] = -1;
            }
            //page fault
            //log some stuff
            System.out.printf("Process %d accesses address %s(page number = %d, page offset = %d) not in main memory\n", id, address, address.page_number, address.offset);
            System.out.printf("Process %d issues an I/O operation to swap in demanded page(page number = %d)\n", id, address.page_number);
            //swap the frame with an existing one in main mem
            frame_num = SingletonContainer.getInstance().swapFrame(id);
            //release the lock
            SingletonContainer.getInstance().semaphore.release();

            //sleep to simulate I/O time
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                //pretend to catch exceptions, hope this doesn't happen
                System.out.println("Sleep failed");
                e.printStackTrace();
            }

            //wake from sleep, update page table
            page_table[address.page_number] = frame_num;
            //log some more stuff
            System.out.printf("Process %d demanded page(page number = %d) has been swapped in from main memory (frame number = %d)\n", id, address.page_number, frame_num);
            System.out.printf("Process %d accesses address %s(page number = %d, page offset = %d) in main memory(frame number = %d)\n", id, address, address.page_number, address.offset, frame_num);
        }
    }
}
