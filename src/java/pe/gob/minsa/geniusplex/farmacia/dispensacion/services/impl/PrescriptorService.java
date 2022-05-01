/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 * Servicios para gestionar Prescriptores
 *
 */
public class PrescriptorService extends GpServiceManager<Prescriptor> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public PrescriptorService() {
        super(Prescriptor.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    /**
     * Verifica la existencia de un personal como presriptor, creando el
     * prescriptor en caso de que no exista
     *
     * @param t
     * @param idModulo
     * @return
     */
    public Prescriptor validarPersonal(Prescriptor t, long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Prescriptor> cq = cb.createQuery(entityClass);
        Root<Prescriptor> from = cq.from(entityClass);

        cq.where(
                cb.equal(from.get("personal"), t.getPersonal()),
                cb.equal(from.get("IdModulo"), idModulo)
        );
        TypedQuery<Prescriptor> query = em.createQuery(cq);
        try {
            t = query.getSingleResult();
        } catch (NoResultException e) {
            t.setIdModulo(idModulo);
            super.insertar(t);
            java.util.logging.Logger.getLogger(PrescriptorService.class.getName()).log(Level.INFO, null, e);
        } catch (NonUniqueResultException e) {
            java.util.logging.Logger.getLogger(PrescriptorService.class.getName()).log(Level.SEVERE, "Hay dos presriptores en el sistema con el mismo nombre", e);
        }
        return t;
    }

    public List<Prescriptor> listarModulo(Object idModulo) {
        ListaModulo<Prescriptor> listaModulo = new ListaModulo<Prescriptor>(Prescriptor.class);
        listaModulo.setEntityManager(entityManagerFactory.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }
}
