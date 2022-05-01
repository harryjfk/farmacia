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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraUrgenciaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.RemoteGpServiceManager;

/**
 *
 * @author armando
 */
@Service("compraUrgenciaProductoService")
public class CompraUrgenciaProductoService extends RemoteGpServiceManager<CompraUrgenciaProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public CompraUrgenciaProductoService() {
        super(CompraUrgenciaProducto.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }
}
