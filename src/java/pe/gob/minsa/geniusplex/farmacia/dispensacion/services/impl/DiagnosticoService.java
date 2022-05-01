/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;

/**
 * Servicios para gestionar Ventas
 * 
 */
@Service("diagnosticoService")
public class DiagnosticoService extends GpServiceManager<DiagnosticoCIE> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    
    public DiagnosticoService() {
        super(DiagnosticoCIE.class);
    }
    
    @PostConstruct
    public void init(){
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public List<DiagnosticoCIE> listarActivos() {
        TypedQuery<DiagnosticoCIE> query = em.createQuery("SELECT d FROM DiagnosticoCIE d WHERE d.activo = 1", DiagnosticoCIE.class);
        return query.getResultList();
    }
    
}
