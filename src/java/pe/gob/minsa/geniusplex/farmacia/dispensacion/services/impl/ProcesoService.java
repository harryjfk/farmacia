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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;

/**
 * Servicios para gestionar Procesoes
 * 
 */
public class ProcesoService extends GpServiceManager<Proceso> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    
    public ProcesoService() {
        super(Proceso.class);
    }
    
    @PostConstruct
    public void init(){
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
}
