/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.VentaHelper;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.FVenta;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.CorteCajaDTO;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.CorteCajaVentaDTO;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author armando
 */
@Service("fCorteCajaService")
public class CorteCajaService extends GpServiceManager<FVenta> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    @Autowired
    VentaHelper vHelper;

    public CorteCajaService() {
        super(FVenta.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    /**
     *
     * @param fData (debe venir order by formaDePago:descripcion)
     * @return
     * @throws BusinessException
     */
    public CorteCajaDTO obtenerCorteCaja(FilterData fData) throws BusinessException {
        try {
            fData.getParams().put("procesoVenta", true);
            List<FVenta> ventas = this.dynamicQuery(fData);
            CorteCajaDTO dto = new CorteCajaDTO(ventas);
            obtenerNombreDocumento(dto);
            return dto;
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CorteCajaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(Arrays.asList("Ha ocurrido un error insperado"));
        } catch (ParseException ex) {
            Logger.getLogger(CorteCajaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(Arrays.asList("Ha ocurrido un error insperado"));
        }
    }

    private void obtenerNombreDocumento(CorteCajaDTO dto) {
        HashMap<Integer, String> docs = new HashMap<Integer, String>();
        for (CorteCajaVentaDTO tblVenta : dto.getTblVentas()) {
            FVenta venta = tblVenta.getVenta();
            int documento = venta.getDocumento();
            if (docs.containsKey(documento)) {
                venta.setDocTipo(docs.get(documento));
            } else {
                String nombreDocumento = vHelper.getNombreDocumento(documento, em);
                venta.setDocTipo(nombreDocumento);
                docs.put(documento, nombreDocumento);
            }
        }
    }

    public boolean eliminarPorNroOperaion(String nroOp) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<FVenta> cd = cb.createCriteriaDelete(entityClass);
        cd.where(cb.equal(cd.from(entityClass).get("nroOperacion"), nroOp));
        em.getTransaction().begin();
        Query query = em.createQuery(cd);
        int entidadesEliminadas = query.executeUpdate();
        em.getTransaction().commit();
        if (entidadesEliminadas > 1) {
            java.util.logging.Logger.getLogger(CorteCajaService.class.getName()).log(Level.INFO, null, "Eliminar venta: hay mas de una venta con el mismo numero de operacion; esto o deveria estar pasando.");
            em.getTransaction().rollback();
            return false;
        }
        return true;
    }

    public FVenta encontrarPorNroOperacion(String nroOperacion) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FVenta> cq = cb.createQuery(entityClass);
        cq.where(cb.equal(cq.from(entityClass).get("nroOperacion"), nroOperacion));
        TypedQuery<FVenta> query = em.createQuery(cq);
        FVenta venta;
        try {
            venta = query.getSingleResult();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CorteCajaService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return venta;
    }

}
