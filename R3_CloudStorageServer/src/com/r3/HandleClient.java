package com.r3;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import bswabe.*;
import com.r3.FileUPLOAD;

/**
 *
 * @author bala
 */
public class HandleClient extends Thread {

    Socket clientSocket = null;
    String nodeid;
    ServerGUI guiinst;
    ObjectInputStream in;
    ObjectOutputStream out;
  

    HandleClient(Socket sc, ServerGUI g) {
        clientSocket = sc;

        guiinst = g;

     


    }

    void sendMessage(Message m) {


        try {
            out.writeObject(m);
            out.flush();

           guiinst.writetolog("Sending the  message");

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();

        }

    }

    public void run() {
        try {


            guiinst.writetolog("Client handler started");

            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println(" Got input stream");

             guiinst.writetolog(" Got input stream");

            while (true) {

                System.out.println(" Waiting for messages");

           //   Message m = (Message) in.readObject();
                
      ///  FileUPLOAD f2 = new FileUPLOAD() ;       
Object f=   in.readObject();

System.out.println("Class :" + f.getClass());
System.out.println(" Fileupload typecast");
Message m = (Message)f;

                switch (m.type) {
                    case 1: // File upload Request
                    {
                        FileUPLOAD fl = (FileUPLOAD) m;

                        System.out.println("Got the File upload message");

                        guiinst.writetolog("Recived File upload message....");
                        
                        

                   //     String fname = common.getinstance().filepath + fl.filename;

 String fname =  fl.filename;
 
                        guiinst.writetolog("Receiving File " + fname + "....");
 String outputFileName =  (fname.substring(fname.lastIndexOf('\\')+1));                      
                       FileOutputStream fos = new FileOutputStream(common.getinstance().filepath + outputFileName);

                        fos.write(fl.filecontent);

                        fos.close();

                        guiinst.writetolog("write completed ");
/////////////splitting file///
 guiinst.writetolog("Spiltting File ");
 
 SplitFile.SplitFile(new File(fname), guiinst);
 
 guiinst.writetolog("Spiltting , converting to numeric and then appying Encryption to each part of File Completed ");
 
// guiinst.writetolog("Coverting to numeric values");
 
 
 //guiinst.writetolog("Applying Encryption on each part of file");
 //guiinst.writetolog("Encryption Completed");
 
                        break;
                    }
                    case 2: // File Download Request
                    {

                        FileDownload fd = (FileDownload) m;

                        guiinst.writetolog("Recived File download message....");

                        guiinst.writetolog("File name =" + fd.filename);

                        guiinst.writetolog("attributes=" + fd.attributes);


                        //R3_Cpabe r3cpabe = R3_Cpabe.getinstance();

                        //r3cpabe.genprvkey(fd.attributes);

                        //r3cpabe.decrypt(fd.filename);

                         
                        try {
                          
                            //if (Bswabe.downloadfileflag) {
                                
                              Bswabe.downloadfileflag=false;
                                
                                joinFile.join(common.getinstance().filepath+ "EncStore\\" +fd.filename);
                                guiinst.writetolog("successfully recreated file");
                                String fulfile =common.getinstance().filepath+"EncStore\\"+fd.filename+".decoded";
                                System.out.println("full filename= "+ fulfile);
                                File fn = new File(fulfile);
                               

                                FileDownloadRes fdres = new FileDownloadRes();

                                FileInputStream fin = new FileInputStream(fn);
                                
                                fdres.filecontent = new byte[(int) fn.length()];
                                fin.read(fdres.filecontent);
                                fin.close();
                                System.out.println("file downloaded ");
                                sendMessage(fdres);
                                System.out.println("Message Sent");

                            //}else{
                              //  FileDownloadRes fdres = new FileDownloadRes();
                                
                                //    fdres.error =-1;
                            
                                
                              //  sendMessage(fdres);
                            //}
                            
                        } catch (Exception e) {
                            e.printStackTrace();

                            FileDownloadRes fdres = new FileDownloadRes();
                            fdres.error = -1;

                            sendMessage(fdres);

                        }




                        break;
                    }
                    case 4: guiinst.writetolog("Downloaded to ");
                        break;
                    

                }



            }

        } catch (Exception e) {
            guiinst.writetolog("!!! Detected error ");

            e.printStackTrace();





        }



    }
}
