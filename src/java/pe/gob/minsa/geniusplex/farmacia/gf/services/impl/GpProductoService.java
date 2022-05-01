/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.services.impl;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;

/**
 *
 * @author armando
 */
@Service("gpProductoService")
public class GpProductoService extends GpServiceManager<GpProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public GpProductoService() {
        super(GpProducto.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public Date obtenerFechaDeVencimiento(int idProducto, int idAlmacen, String lote) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Date> cq = cb.createQuery(Date.class);
        
        Root<GpMovimientoProducto> mp = cq.from(GpMovimientoProducto.class);
        Join<GpMovimiento, GpMovimientoProducto> m = 
                mp.<GpMovimiento, GpMovimientoProducto>join("idMovimiento");
        cq.select(mp.<Date>get("fechaVencimiento"));
        cq.where(
                cb.equal(mp.get("idProducto").get("idProducto"), idProducto),
                cb.equal(m.get("idAlmacenDestino").get("idAlmacen"), idAlmacen),
                cb.equal(mp.get("lote"), lote),
                cb.equal(m.get("tipoMovimiento"), 'I')
        );
        TypedQuery<Date> query = em.createQuery(cq);
        query.setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
