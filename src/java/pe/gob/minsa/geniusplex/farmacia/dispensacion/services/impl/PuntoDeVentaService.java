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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PuntoDeVenta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;

/**
 *
 * @author armando
 */
@Service("puntoDeVentaService")
public class PuntoDeVentaService extends GpServiceManager<PuntoDeVenta> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public PuntoDeVentaService() {
        super(PuntoDeVenta.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

}
