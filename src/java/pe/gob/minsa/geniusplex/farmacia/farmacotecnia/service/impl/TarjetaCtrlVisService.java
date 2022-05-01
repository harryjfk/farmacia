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
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.MatrizMateria;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.TarjetaControlDto;

/**
 *
 * @author armando
 */
@Service("tarjetaCtrlVisService")
public class TarjetaCtrlVisService {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }

    public List<TarjetaControlDto> obtenerTarjetasCtrlVis(Date desde,
            Date hasta, int[] range) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TarjetaControlDto> cq = cb
                .createQuery(TarjetaControlDto.class);

        Root<MatrizMateria> fromMt = cq.from(MatrizMateria.class);
        cq.select(cb
                .construct(
                        TarjetaControlDto.class,
                        fromMt.get("insumo"), 
                        cb.sum(fromMt.<Integer>get("cantidad"))
                )
        ).groupBy(fromMt.get("insumo"));

        cq.where(
                cb.greaterThanOrEqualTo(fromMt.<Date>get("fechaCreacion"), desde),
                cb.lessThanOrEqualTo(fromMt.<Date>get("fechaCreacion"), hasta)
        );

        TypedQuery<TarjetaControlDto> query = em.createQuery(cq);
        
        if(range != null) {
            query.setFirstResult(range[0]);
            query.setMaxResults(range[1]);
        }
        
        List<TarjetaControlDto> resultList = query.getResultList();

        return resultList;
    }

}
