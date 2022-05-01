/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.MatrizMateria;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.ConsumoPromedioMenDto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;

/**
 *
 * @author armando
 */
@Service("consumoPromedioMensualService")
public class ConsumoPromedioMensualService {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }

    public List<ConsumoPromedioMenDto> getConsumoPromedioMensual(long idModulo,
            Date desde, Date hasta, int idAlmacen, String search, int[] range) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ConsumoPromedioMenDto> cq = cb.createQuery(ConsumoPromedioMenDto.class);
        Root<Materias> insumo = cq.from(Materias.class);
        Join<Materias, MatrizMateria> mm = insumo
                .<Materias, MatrizMateria>join("insumos", JoinType.LEFT);
        cq.select(
                cb.construct(ConsumoPromedioMenDto.class, insumo, mm.get("cantidad"))
        );
        
        Predicate[] condiciones = new Predicate[]{cb.equal(insumo.type(), Materias.class)};
        if (idAlmacen > 0) {
            condiciones = JpaCriteriaHelper.agregarPredicado(condiciones, cb.equal(insumo.get("almacen").get("idAlmacen"), idAlmacen));
        }
        if (desde != null) {
            condiciones = JpaCriteriaHelper.agregarPredicado(condiciones, cb.greaterThanOrEqualTo(insumo.<Date>get("fechaCreacion"), desde));
        }
        if (hasta != null) {
            condiciones = JpaCriteriaHelper.agregarPredicado(condiciones, cb.lessThanOrEqualTo(insumo.<Date>get("fechaCreacion"), hasta));
        }
        cq.where(condiciones);
        TypedQuery<ConsumoPromedioMenDto> query = em.createQuery(cq);
        return query.setMaxResults(range[1]).setFirstResult(range[0]).getResultList();
    }

}
