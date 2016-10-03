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
public class FileUPLOAD extends Message implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public FileUPLOAD()
    {
        type=1;
    }

    public String filename;

    byte [] filecontent;

    byte [] metdata;

    byte [] watermarkimage;


}
