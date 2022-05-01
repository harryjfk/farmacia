/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PuntoDeVenta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
@Service("ptoVentaService")
public class PVentaService  extends GpServiceManager<PuntoDeVenta>{

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public PVentaService() {
        super(PuntoDeVenta.class);
    }
    
    @PostConstruct
    public void init(){
        em = emf.createEntityManager();
        setEntityManager(em);
    }
    
    public List<PuntoDeVenta> listarModulo(Object idModulo){
        ListaModulo<PuntoDeVenta> listaModulo = new ListaModulo<PuntoDeVenta>(PuntoDeVenta.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
        
    }
}
