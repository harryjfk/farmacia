/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraUrgenciaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraDeUrgencia;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 * Servicios para gestionar CompraDeUrgenciaes
 *
 */
@Service("compraUrgenciaService")
public class CompraDeUrgenciaService extends GpServiceManager<CompraDeUrgencia> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public CompraDeUrgenciaService() {
        super(CompraDeUrgencia.class);
    }

    @PostConstruct
    public void init() {
        this.em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    public boolean guardarCambios(CompraDeUrgencia compra) {

        List<CompraUrgenciaProducto> compraProductos = compra.getCompraUrgenciaProductos();
        if (compra.getId() > 0) {
            Prescriptor medico = compra.getMedico();
            Paciente paciente = compra.getPaciente();

            compra = obtenerPorId(compra.getId());
            borrarProductos(compra);
            compra.setMedico(medico);
            compra.setPaciente(paciente);
        }
        if (!compraProductos.isEmpty()) {
            compra.setCompraUrgenciaProductos(compraProductos);
            for (int i = 0; i < compra.getCompraUrgenciaProductos().size(); i++) {
                CompraUrgenciaProducto prod = compra.getCompraUrgenciaProductos().get(i);
                GpProducto producto = em.find(GpProducto.class, prod.getProducto().getIdProducto());
                prod.setProducto(producto);
                prod.setCompraDeUrgencia(compra);
                compra.getCompraUrgenciaProductos().set(i, prod);
            }
        }
        return (compra.getId() > 0) ? actualizar(compra) : insertar(compra);
    }

    public CompraDeUrgencia obtener(CompraDeUrgencia compra) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CompraDeUrgencia> cq = cb.createQuery(CompraDeUrgencia.class);
        Root<CompraDeUrgencia> interv = cq.from(CompraDeUrgencia.class);
        cq.where(
                cb.equal(interv.get("paciente"), compra.getPaciente()),
                cb.equal(interv.get("medico"), compra.getMedico()));
        TypedQuery<CompraDeUrgencia> q = em.createQuery(cq);

        try {
            CompraDeUrgencia tmpInt = q.getSingleResult();
            compra = tmpInt;
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {//I hope this never happens but just in case
            compra = q.getResultList().get(0);
        }

        return compra;
    }

    private void borrarProductos(CompraDeUrgencia compra) {
        for (int i = 0; i < compra.getCompraUrgenciaProductos().size(); i++) {
            CompraUrgenciaProducto prod = compra.getCompraUrgenciaProductos().get(i);
            try {
                em.getTransaction().begin();
                em.remove(em.merge(prod));
                em.getTransaction().commit();
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                em.getTransaction().rollback();
            }
        }
    }
}
