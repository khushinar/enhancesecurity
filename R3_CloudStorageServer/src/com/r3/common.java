/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r3;

/**
 *
 * @author prathap
 */
public class common {
    
    public static common cmn=null;
    
    
    public boolean serverstarted = false;
    public String filepath= "E:\\NetBeans sample\\webBhargavi\\build\\web\\cloudserver\\"  ;  //"E:\\students\\cloudserver\\";
    
    public static common getinstance(){
        if(cmn==null){
            cmn= new common();
        }
        
        return cmn;
    }
    
}
