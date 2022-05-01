/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

public class SubCompProdPg<T> extends PaginaModulo<T> {

    private long idSubComponente;

    public SubCompProdPg(Class<T> entityClass, String[] cls) {
        super(entityClass, cls);
    }

    public SubCompProdPg(String[] columns) {
        super(columns);
    }

    public SubCompProdPg() {
    }

    public long getIdSubComponente() {
        return idSubComponente;
    }

    public void setIdSubComponente(long id) {
        idSubComponente = id;
    }

    @Override
    public List<T> listarRango(Object idModulo, int[] range, String sSearch, Object[] sort) {
        Class<T> entityClass = getEntityClass();
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> from = cq.from(entityClass);

        SubComponente subComponente = em.find(SubComponente.class, getIdSubComponente());
        if (subComponente.getProductos().isEmpty()) {
            return new ArrayList<T>();
        }
        Predicate[] predicates = filtrar(sSearch, from, cb);
        if (Long.parseLong(String.valueOf(idModulo)) > 0) {
            JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(from.get("IdModulo"), idModulo));
        }
        if (predicates.length > 0) {
            cq.where(cb.and(
                    cb.or(predicates),
                    cb.and(from.in(subComponente.getProductos()))
            ));
        } else {
            cq.where(cb.and(from.in(subComponente.getProductos())));
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
        EntityManager em = getEntityManager();
        SubComponente subComponente = em.find(SubComponente.class, getIdSubComponente());
        List<GpProducto> productos = subComponente.getProductos();
         if (productos.isEmpty()) {
            return 0;
        }
         int cont = productos.size();
         if(sSearch != null && sSearch.length() > 0) {
            for (GpProducto producto : productos) {
                String um = producto.getIdUnidadMedida() != null? producto.getIdUnidadMedida().getNombreUnidadMedida(): "";
                String ff = producto.getIdFormaFarmaceutica() != null? producto.getIdFormaFarmaceutica().getNombreFormaFarmaceutica(): "";
                String desc = producto.getDescripcion() != null? producto.getDescripcion(): "";
                String con = producto.getConcentracion() != null? producto.getConcentracion(): "";
                String pre = producto.getPresentacion() != null? producto.getPresentacion():"";
                
                if(!sSearch.contains(ff) && !sSearch.contains(um) && !sSearch.contains(desc)
                        && !sSearch.contains(con) && !sSearch.contains(pre)) {
                    cont--;
                }
            }
         }
        return cont;
    }
}
