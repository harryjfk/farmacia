/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;

/**
 *
 * @author armando
 */
@Service("kitProductoService")
public class KitProductoService extends GpServiceManager<KitAtencionProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    private EntityManager em;

    public KitProductoService() {
        super(KitAtencionProducto.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }
}
