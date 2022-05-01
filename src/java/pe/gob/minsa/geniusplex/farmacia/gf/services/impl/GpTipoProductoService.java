/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.services.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoDocumento;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoProducto;

/**
 *
 * @author armando
 */
@Service("gpTipoProducto")
public class GpTipoProductoService extends GpServiceManager<GpTipoProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public GpTipoProductoService() {
        super(GpTipoProducto.class);
    }

    @PostConstruct
    public void init() {
        EntityManager em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
}
