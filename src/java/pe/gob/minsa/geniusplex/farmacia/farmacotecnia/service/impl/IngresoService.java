/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Matriz;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;

/**
 *
 * @author armando
 */
@Service("ingresoService")
public class IngresoService extends GpServiceManager<GpMovimiento> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public IngresoService() {
        super(GpMovimiento.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Materias> listarIngresosPorAlmacen(
            int idAlmacen, Date desde, Date hasta, String search, int[] range) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Materias> cq = cb.createQuery(Materias.class);
        Root<Materias> materia = cq.from(Materias.class);
        cq.select(materia);
        Predicate conditions = cb.and(cb.equal(materia.get("almacen").get("idAlmacen"),
                idAlmacen), cb.notEqual(materia.type(), Matriz.class));

        if (desde != null) {
            conditions = cb
                    .and(conditions, 
            cb.greaterThanOrEqualTo(materia.<Date>get("fechaCreacion"), desde));

        }
        
        if(hasta != null) {
            conditions = cb.and(conditions,
                    cb.lessThanOrEqualTo(materia.<Date>get("fechaCreacion"), hasta));
        }
        
        if(search != null && search.length() > 0) {
            conditions = cb.and(conditions,
                    cb.like(materia.<String>get("nombre"), "%" + search + "%"));
        }
        cq.where(conditions);
        TypedQuery<Materias> query = em.createQuery(cq);

        return query.setFirstResult(range[0])
                .setMaxResults(range[1])
                .getResultList();
    }

}
