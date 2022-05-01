/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.util;

import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class UtilDto {
    
    
    public static BigDecimal getBigDecimalFromNull(BigDecimal bd){
        if(bd==null)
            return new BigDecimal(0);
        else 
            return bd;
    }
}
