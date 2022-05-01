/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.Ime_IIIDao;
import pe.gob.minsa.farmacia.domain.Ime_III;

/**
 *
 * @author admin
 */
public class Ime_IIIService {
    
    @Autowired
    Ime_IIIDao imeiiiDao;
    
    public List<Ime_III> listar(int idIme){
        return imeiiiDao.listar(idIme);
    }
    
    public void insertar(Ime_III imeiii){
        imeiiiDao.insertar(imeiii);
    }
}
