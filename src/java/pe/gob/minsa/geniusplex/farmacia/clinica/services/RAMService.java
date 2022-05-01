/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.services;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.RAM;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;

/**
 *
 * @author stark
 */
@Service("ramService")
public class RAMService  extends GpServiceManager<RAM>{

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public RAMService() {
        super(RAM.class);
    }
    
    @PostConstruct
    public void init(){
        em = emf.createEntityManager();
        setEntityManager(em);
    }
    
    public List<RAM> listar(Object historia) {
        TypedQuery<RAM> q = em.createQuery("SELECT r FROM RAM r WHERE r.historia = :historia ",RAM.class);
        
        List<RAM> lista = q.setParameter("historia", historia).getResultList();
        return lista;
    }
    
}
