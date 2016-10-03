/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r3;
import java.math.BigInteger;
/**
 *
 * @author Lata
 */
public class ConvertBig {
    
    public static BigInteger toBigInteger(String str)
    {
        return new BigInteger(str.getBytes());
    }
     public static BigInteger toBigInteger(byte [] bArr)
    {
        return new BigInteger(bArr);
    }
    public static String fromBigInteger(BigInteger bar)
    {
        return new String(bar.toByteArray());
    }
}
