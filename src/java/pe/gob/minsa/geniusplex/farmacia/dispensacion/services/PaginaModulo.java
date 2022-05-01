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

/**
 *
 * @author stark
 * @param <T>
 */
public class PaginaModulo<T> implements GpPaginable<T> {

    private Class<T> entityClass;
    private EntityManager em;
    private String[] columns;

    public PaginaModulo(Class<T> entityClass, String[] cls) {
        columns = Arrays.copyOf(cls, cls.length);
        this.entityClass = entityClass;
    }

    public PaginaModulo(String[] columns) {
        this.columns = columns;
    }

    public PaginaModulo() {
        this.columns = null;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
    

    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    public void setEntityClass(Class<T> entClass) {
        this.entityClass = entClass;
    }

    public String[] getColumn() {
        return this.columns;
    }

    public void setColumns(String[] cls) {
        this.columns = cls;
    }

    @Override
    public List<T> listarRango(Object idModulo, int[] range, String sSearch, Object[] sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> from = cq.from(entityClass);
        Predicate[] predicates = filtrar(sSearch, from, cb);
        if (Long.parseLong(String.valueOf(idModulo)) > 0) {
            JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(from.get("IdModulo"), idModulo));
        }
        if (predicates.length > 0) {
            cq.where(cb.or(predicates));
        }

        ordenar(sort, cq, cb, from);

        TypedQuery<T> query = em.createQuery(cq);

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
        Root<T> from = cq.from(entityClass);
        Predicate[] predicates = filtrar(sSearch, from, cb);
        if (Long.parseLong(String.valueOf(idModulo)) > 0) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(from.get("IdModulo"), idModulo));
        }
        if (predicates.length > 0) {
            cq.where(cb.or(predicates));
        }
        cq.select(cb.count(from));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult())
                .intValue();
    }

    protected Predicate[] filtrar(String sSearch, Root<T> from, CriteriaBuilder cb) {
        Predicate[] predicates = new Predicate[0];
        if (sSearch != null && sSearch.length() > 0) {
            String lik = "%" + sSearch + "%";
            for (String column : columns) {
                Path path = getPath(column, from);
                Predicate like = cb.like(path, lik);
                predicates = JpaCriteriaHelper.agregarPredicado(predicates, like);
            }
        }
        return predicates;
    }

    protected Path getPath(String column, Root<T> from) {
        //las columnas son estaticas, hay que ver donde se ponen.
        String[] tmp = null;
        if (column.contains(".")) {
            tmp = column.split("\\.");
            column = tmp[0];
        }
        Path path = from.get(column);//campo a filtrar de la entidad (criteria query)
        if (null != tmp) {
            path = from.get(column).get(tmp[1]);
            for (int i = 2; i < tmp.length; i++) {//navegar hacia el campo en la otra entidad
                path = path.get(tmp[i]);
            }
        }
        return path;
    }

    protected void ordenar(Object[] sort, CriteriaQuery<T> cq, CriteriaBuilder cb, Root<T> from) {
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
