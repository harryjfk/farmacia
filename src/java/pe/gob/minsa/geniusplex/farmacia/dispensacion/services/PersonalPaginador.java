/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;

/**
 *
 * @author stark
 * @param
 */
public final class PersonalPaginador implements GpPaginable {

    private final Class entityClass = GpPersonal.class;
    private EntityManager em;
    private final String[] columns;

    public PersonalPaginador(String[] cls) {
        columns = Arrays.copyOf(cls, cls.length);
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<GpPersonal> listarRango(Object idModulo, int[] range, String sSearch, Object[] sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpPersonal> cq = cb.createQuery(entityClass);
        Root<GpPersonal> from = cq.from(entityClass);
        Predicate[] predicates = filtrar(sSearch, from, cb);

        if (predicates.length > 0) {
            cq.where(cb.and(
                    cb.or(predicates),
                    cb.and(
                            cb.isNotNull(from.get("nombres")),
                            cb.isNotNull(from.get("apellidoPaterno")),
                            cb.isNotNull(from.get("apellidoMaterno")),
                            cb.gt(cb.length(cb.trim(from.get("nombres").as(String.class))), 0),
                            cb.gt(cb.length(cb.trim(from.get("apellidoPaterno").as(String.class))), 0),
                            cb.gt(cb.length(cb.trim(from.get("apellidoMaterno").as(String.class))), 0),
                            cb.notEqual(from.get("nombres"), " "),
                            cb.notEqual(from.get("apellidoPaterno"), " "),
                            cb.notEqual(from.get("apellidoPaterno"), " ")
                    )));
        }

        ordenar(sort, cq, cb, from);

        TypedQuery query = em.createQuery(cq);

        if (range != null && range.length >= 2) {
            query.setMaxResults(range[1]);
            query.setFirstResult(range[0]);
        }

        return query.getResultList();
    }

    @Override
    public int contarPaginado(Object idModulo, String sSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root from = cq.from(entityClass);
        Predicate[] predicates = filtrar(sSearch, from, cb);
        if (Long.parseLong(String.valueOf(idModulo)) > 0) {
            JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(from.get("IdModulo"), idModulo));
        }
        if (predicates.length > 0) {
            cq.where(cb.and(
                    cb.or(predicates),
                    cb.and(
                            cb.isNotNull(from.get("nombres")),
                            cb.isNotNull(from.get("apellidoPaterno")),
                            cb.isNotNull(from.get("apellidoMaterno")),
                            cb.gt(cb.length(cb.trim(from.get("nombres").as(String.class))), 0),
                            cb.gt(cb.length(cb.trim(from.get("apellidoPaterno").as(String.class))), 0),
                            cb.gt(cb.length(cb.trim(from.get("apellidoMaterno").as(String.class))), 0),
                            cb.notEqual(from.get("nombres"), " "),
                            cb.notEqual(from.get("apellidoPaterno"), " "),
                            cb.notEqual(from.get("apellidoPaterno"), " ")
                    )));
        }
        cq.select(cb.count(from));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult())
                .intValue();
    }

    private Predicate[] filtrar(String sSearch, Root from, CriteriaBuilder cb) {
        Predicate[] predicates = new Predicate[0];
        if (sSearch != null && sSearch.length() > 0) {
            String lik = "%" + sSearch + "%";
            for (String column : columns) {//las columnas son estaticas, hay que ver donde se ponen.
                Path path = from.get(column);//campo a filtrar de la entidad (criteria query)
                Predicate like = cb.like(path, lik);
                predicates = JpaCriteriaHelper.agregarPredicado(predicates, like);
            }
        }
        return predicates;
    }

    protected void ordenar(Object[] sort, CriteriaQuery cq, CriteriaBuilder cb, Root from) {
        if (sort[0] != null) {
            int sortColumnIndex = (Integer) sort[1];
            String sortDir = sort[2].toString();
            if (sortDir.equalsIgnoreCase("asc")) {
                cq.orderBy(cb.asc(from.get(columns[sortColumnIndex])));
            } else if (sortDir.equalsIgnoreCase("desc")) {
                cq.orderBy(cb.desc(from.get(columns[sortColumnIndex])));
            }
        }
    }
}
