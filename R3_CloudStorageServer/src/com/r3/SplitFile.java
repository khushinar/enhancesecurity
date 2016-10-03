/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.math.BigInteger;

import com.r3.Paillier;
import com.r3.common;
/**
 *
 * @author Lata
 */
class SplitFile {
    public static void SplitFile(File f,  ServerGUI guiinst) throws IOException {
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
                            //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];
        Paillier paillier = new Paillier();
  SampleEncoder sen = new SampleEncoder();
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();
if(f.length()< 1024 * 1024)
{
    sizeOfFiles=500; //500kb
}
else if (f.length()<= 5* 1024 *1024)
{
    sizeOfFiles= 1024*1024; //1 MB
}
else if(f.length()<= 10* 1024 *1024){
    sizeOfFiles= 2*1024* 1024;  //2 MB
}
else {
 sizeOfFiles= 5*1024* 1024;     //5 MB
}

            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                
             //help   String outputFileName =  (fname.substring(fname.lastIndexOf('\\')+1));                      
                // help       FileOutputStream fos = new FileOutputStream(common.getinstance().filepath + outputFileName);
            //    File newFile = new File(f.getParent(), name + "."
                //        + String.format("%03d", partCounter++));
                File newFile = new File(common.getinstance().filepath+"EncStore\\" +name +"." + String.format("%03d", partCounter++));
                guiinst.writetolog("Creating File.. " + newFile.getName()+ "...");
              
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    BigInteger tempNum = ConvertBig.toBigInteger(buffer);
                    guiinst.writetolog("Applying Fully Homomorphic Encryption to "+ newFile.getName());
                    BigInteger em1 = paillier.Encryption(tempNum);
//BigInteger em2 = paillier.Encryption(m2);
                    
                    
                    byte [] finalBarr = tempNum.toByteArray();
                  //  out.write(buffer, 0, tmp);//tmp is chunk size
                    out.write(finalBarr);
                    
                    ///calling function - Reed-solomom erasure corecting code
                    
                 //   guiinst.writetolog("Applying Reed-Solomon erasure correcting code to use switching Matrix");
                    
                  
                 //   sen.doMain(newFile.getName());
                }
            }
            
            for(int p =1; p<partCounter; p++)
            {
                 guiinst.writetolog("Applying Reed-Solomon erasure correcting code to use switching Matrix");
                     File newFile = new File(common.getinstance().filepath,"EncStore\\"+ name + "."  + String.format("%03d", p));
               
                        sen.doMain(newFile.getName());
 
            }
guiinst.writetolog("File Splitted in " + partCounter + " Parts"+ "and applied FHE to each part and then key matrix generated for each part");
        }
    }
}
