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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 * Servicios para gestionar Componentes
 * 
 */
public class ComponenteService extends GpServiceManager<Componente> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    
    public ComponenteService() {
        super(Componente.class);
    }
    
    @PostConstruct
    public void init(){
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public List<Componente> listarModulo(Object idModulo){
        ListaModulo<Componente> listaModulo = new ListaModulo<Componente>(Componente.class);
        listaModulo.setEntityManager(entityManagerFactory.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }
}
