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
public class FileUploadRes extends Message implements Serializable {

    FileUploadRes()
    {
        type = 3;
    }

    int error = 0; // -1 in case error , 0 means success

}
