package com.r3;


import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bala
 */
public class FileDownload extends Message implements Serializable {
private static final long serialVersionUID = 1L;
   public FileDownload()
    {
        type = 2;
    }

    public  String filename;
   
   public String attributes;
}
