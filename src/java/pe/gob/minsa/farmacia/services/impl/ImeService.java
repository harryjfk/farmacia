package pe.gob.minsa.farmacia.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ImeDao;
import pe.gob.minsa.farmacia.domain.Ime;
import pe.gob.minsa.farmacia.domain.ImeB;
import pe.gob.minsa.farmacia.domain.Ime_III;
import pe.gob.minsa.farmacia.util.BusinessException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author admin
 */
public class ImeService {
    
    
    @Autowired
    ImeDao imeDao;
    
    public int procesar(Timestamp fechaDesde,Timestamp fechaHasta, int idAlmacen, ImeB imeB, ArrayList<Ime_III> detGasto)throws BusinessException{
        
        return imeDao.procesar(fechaDesde, fechaHasta, idAlmacen, imeB, detGasto);
    }
    
    public Ime obtenerIme(int idAlmacen){
        return imeDao.obtener(idAlmacen);
    }
    
    public int procesar2(Timestamp fechaDesde,Timestamp fechaHasta, ImeB imeB, ArrayList<Ime_III> detGasto)throws BusinessException{
        
        return imeDao.procesar2(fechaDesde, fechaHasta, imeB, detGasto);
    }
}
