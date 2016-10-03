package com.r3;

import cpabe.Cpabe;

public class R3_Cpabe extends Thread {

    final static boolean DEBUG = true;
    public int Time;
    
    static String pubfile = common.getinstance().filepath+"pub_key.txt";
    static String mskfile = common.getinstance().filepath+"master_key.txt";
    static String prvfile = common.getinstance().filepath+"prv_key.txt";
    static String inputfile = common.getinstance().filepath+"input.txt";
    static String encfile = common.getinstance().filepath+"input.txt.cpabe";
    static String decfile = common.getinstance().filepath+"input.txt.new";
    //static String[] attr = { "baf1", "fim1", "foo" };
    static String policy;//= "foo bar fim 2of3 baf 1of2";
    static String student_attr  ;
    static String student_policy ;//
    public static R3_Cpabe dmcp;

    public static R3_Cpabe getinstance() {

        if (dmcp == null) {
            dmcp = new R3_Cpabe();

        }
        return dmcp;
    }

    R3_Cpabe() {
        start();
        
    }

    public void setparam(String pol) {
        student_policy = pol;


    }

    public void encrypabe(String infile) {

        R3_Cpabe.inputfile = infile;
        R3_Cpabe.encfile = infile + ".cpabe";
        R3_Cpabe.decfile = infile + ".new";
        String attr_str;

        //attr_str = student_attr;
        policy = student_policy;
        try {
            Cpabe test = Cpabe.getinstance();
            println("//start to setup");
            test.setup(pubfile, mskfile);
            println("//end to setup");

            println("//start to keygen");
            test.keygen(pubfile, mskfile);
            

            println("//end to keygen");

            println("//start to enc");
            test.enc(pubfile, policy, inputfile, encfile);
            println("//end to enc");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genprvkey(String attr) {
        try {
            Cpabe test = Cpabe.getinstance();
            
            student_attr = attr;
            test.keygenprv(pubfile, prvfile, mskfile, attr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(String infile) {
        try {
            Cpabe test = Cpabe.getinstance();
            R3_Cpabe.encfile = common.getinstance().filepath+infile + ".cpabe";
            R3_Cpabe.decfile = common.getinstance().filepath+infile + ".new";
            println("//start to dec");
            test.dec(pubfile, prvfile, encfile, decfile);
            println("//end to dec");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println("****************** Thread Started ************");
            while (true) {
                Time++;
                sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   

    /* connect element of array with blank */
    public static String array2Str(String[] arr) {
        int len = arr.length;
        String str = arr[0];

        for (int i = 1; i < len; i++) {
            str += " ";
            str += arr[i];
        }

        return str;
    }

    private static void println(Object o) {
        if (DEBUG) {
            System.out.println(o);
        }
    }
}
