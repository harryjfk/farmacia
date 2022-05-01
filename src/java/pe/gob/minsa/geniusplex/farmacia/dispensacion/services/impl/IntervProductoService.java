/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.RemoteGpServiceManager;

/**
 *
 * @author armando
 */
@Service("intervProductoService")
public class IntervProductoService extends RemoteGpServiceManager<IntervProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public IntervProductoService() {
        super(IntervProducto.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }
}
