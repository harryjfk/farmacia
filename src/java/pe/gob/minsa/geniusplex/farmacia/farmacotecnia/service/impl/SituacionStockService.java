/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.MatrizMateria;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.StockDto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpUnidadMedida;

/**
 *
 * @author armando
 */
@Service("situacionStockFarmacotecniaService")
public class SituacionStockService {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    private EntityManager em;

    @Autowired
    ProductoHelper pHelper;

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }

    public List<StockDto> obtenerSituacionStock(int idAlmacen, String descripcion, Date desde, Date hasta, int[] range) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StockDto> cq = cb
                .createQuery(StockDto.class);
        Root<Materias> m = cq.from(Materias.class);
        Join<Materias, MatrizMateria> joinMm = 
                m.<Materias, MatrizMateria>join("insumos", JoinType.LEFT);

        cq.select(cb
                .construct(StockDto.class, 
                        m.<Long>get("id"),
                        m.<String>get("nombre"), 
                        m.<GpUnidadMedida>get("unidad"), 
                        m.<BigDecimal>get("precio"), 
                        m.<Integer>get("cantidad"), 
                        cb.<Integer>sum(joinMm.<Integer>get("cantidad"))))
                .groupBy(m.get("id"),
                        m.get("nombre"), 
                        m.get("unidad"), 
                        m.get("precio"), 
                        m.get("cantidad"));
        Predicate conditions = cb.equal(m.type(), Materias.class);
        if (desde != null) {
            conditions = cb
            .and(
                cb.greaterThanOrEqualTo(m.<Date>get("fechaCreacion"), desde)
            );
        }
        if(hasta != null) {
            conditions = cb.and(
            cb.lessThanOrEqualTo(m.<Date>get("fechaCreacion"), hasta));
        }
        if (idAlmacen > 0) {
            conditions = cb.and(conditions,
                    cb.equal(m.get("almacen").get("idAlmacen"), idAlmacen));
        }
        if (descripcion != null && descripcion.length() > 0) {
            conditions = cb
                    .like(m.<String>get("nombre"), "%" + descripcion + "%");
        }
        cq.where(conditions);
        TypedQuery<StockDto> query = em.createQuery(cq);
        if (range != null) {
            query.setFirstResult(range[0]);
            query.setMaxResults(range[1]);
        }
        List<StockDto> resultList = query.getResultList();

        return resultList;
    }
}
