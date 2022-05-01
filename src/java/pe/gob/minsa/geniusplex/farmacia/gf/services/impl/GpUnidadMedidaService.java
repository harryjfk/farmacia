/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.services.impl;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpUnidadMedida;

/**
 *
 * @author armando
 */
@Service
public class GpUnidadMedidaService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    
    public List<GpUnidadMedida> listar() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("GpUnidadMedida.findAll");
        List result = query.getResultList();
        return result;
    }
    
    public GpUnidadMedida obtenerPorId(Integer idUnidadMedida) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<GpUnidadMedida> query = em.createQuery("SELECT u FROM GpUnidadMedida u WHERE u.idUnidadMedida = :id", GpUnidadMedida.class);
        query.setParameter("id", idUnidadMedida);
        try {
            GpUnidadMedida unidadMedida = query.getSingleResult();
            return unidadMedida;
        } catch (NoResultException e) {
            java.util.logging.Logger.getLogger(GpUnidadMedidaService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (NonUniqueResultException e) {
            java.util.logging.Logger.getLogger(GpUnidadMedidaService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
}
