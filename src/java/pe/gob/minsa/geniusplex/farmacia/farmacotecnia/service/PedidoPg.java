/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.PaginaModulo;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;

/**
 *
 * @author armando
 */
public class PedidoPg extends PaginaModulo<GpMovimiento> {

    private String idPeriodo;
    private long idAlmacenOrigen;
    private long idConcepto;

    public PedidoPg() {
        super();
        super.setEntityClass(GpMovimiento.class);
    }

    /**
     * @return the idPeriodo
     */
    public String getIdPeriodo() {
        return idPeriodo;
    }

    /**
     * @param idPeriodo the idPeriodo to set
     */
    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    /**
     * @return the idAlmacenOrigen
     */
    public long getIdAlmacenOrigen() {
        return idAlmacenOrigen;
    }

    /**
     * @param idAlmacenOrigen the idAlmacenOrigen to set
     */
    public void setIdAlmacenOrigen(long idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    /**
     * @return the idConcepto
     */
    public long getIdConcepto() {
        return idConcepto;
    }

    /**
     * @param idConcepto the idConcepto to set
     */
    public void setIdConcepto(long idConcepto) {
        this.idConcepto = idConcepto;
    }

    @Override
    public List<GpMovimiento> listarRango(Object idModulo, int[] range, String sSearch, Object[] sort) {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<GpMovimiento> cq = cb.createQuery(this.getEntityClass());
        Root<GpMovimiento> from = cq.from(this.getEntityClass());

        Predicate[] predicates = filtrar(sSearch, from, cb);
        if (Long.parseLong(String.valueOf(idModulo)) > 0) {
            JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(from.get("IdModulo"), idModulo));
        }
        Predicate[] plus = fillPlus(cb, from);
        if (predicates.length > 0) {
            cq.where(cb.and(
                    cb.or(predicates),
                    cb.and(plus)
            ));
        } else {
            cq.where(plus);
        }

        ordenar(sort, cq, cb, from);

        TypedQuery<GpMovimiento> query = this.getEntityManager().createQuery(cq);

        if (range != null && range.length >= 2) {
            query.setMaxResults(range[1]);
            query.setFirstResult(range[0]);
        }

        return query.getResultList();
    }

    @Override
    public int contarPaginado(Object idModulo, String sSearch) {
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<GpMovimiento> cq = cb.createQuery(this.getEntityClass());
        Root<GpMovimiento> from = cq.from(this.getEntityClass());

        Predicate[] predicates = filtrar(sSearch, from, cb);
        if (Long.parseLong(String.valueOf(idModulo)) > 0) {
            JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(from.get("IdModulo"), idModulo));
        }
        Predicate[] plus = fillPlus(cb, from);
        if (predicates.length > 0) {
            cq.where(cb.and(
                    cb.or(predicates),
                    cb.and(plus)
            ));
        } else {
            cq.where(plus);
        }

        TypedQuery<GpMovimiento> query = this.getEntityManager().createQuery(cq);
        return query.getResultList().size();
    }

    private Predicate[] fillPlus(CriteriaBuilder cb, Root<GpMovimiento> from) {
        Predicate[] plus = new Predicate[0];
        plus = JpaCriteriaHelper.agregarPredicado(plus, cb.equal(from.get("idPeriodo").get("idPeriodo"), this.getIdPeriodo()));
        plus = JpaCriteriaHelper.agregarPredicado(plus, cb.equal(from.get("tipoMovimiento"), 'S'));
        if (this.getIdConcepto() > 0) {
            plus = JpaCriteriaHelper.agregarPredicado(plus, cb.equal(from.get("concepto").get("idConcepto"), this.getIdConcepto()));
        }
        if (this.getIdAlmacenOrigen() > 0) {
            plus = JpaCriteriaHelper.agregarPredicado(plus, cb.equal(from.get("idAlmacenOrigen").get("idAlmacen"), this.getIdAlmacenOrigen()));
        }
        return plus;
    }
}
