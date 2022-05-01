/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PracticaDispensacion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
@Service("practicasDispensacionService")
public class PracticaDispensacionService extends GpServiceManager<PracticaDispensacion> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public PracticaDispensacionService() {
        super(PracticaDispensacion.class);
    }

    @PostConstruct
    public void init() {
        EntityManager em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<PracticaDispensacion> listarModulo(Object idModulo) {
        ListaModulo<PracticaDispensacion> listaModulo = new ListaModulo<PracticaDispensacion>(PracticaDispensacion.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);

    }

    public List<PracticaDispensacion> listarPorModuloVentas(long idModulo, long idVenta) {
        TypedQuery<PracticaDispensacion> q = em.createQuery("SELECT p FROM PracticaDispensacion p WHERE p.IdModulo = :idModulo AND p.venta.id = :idVenta ", PracticaDispensacion.class);
        q.setParameter("idModulo", idModulo);
        List<PracticaDispensacion> lista = q.setParameter("idVenta", idVenta).getResultList();
        return lista;
    }

    public List<PracticaDispensacion> ConsultaPracticaDispensacions(long idModulo, long idCliente, Date startDate, Date endDate, String nroVenta) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_PracticaDispensacion_Consultar", PracticaDispensacion.class);
        query.registerStoredProcedureParameter("Cliente", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("NroVenta", String.class, ParameterMode.IN);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start = null;
        String end = null;
        if (startDate != null) {
            start = format.format(startDate);

        }
        if (endDate != null) {
            end = format.format(endDate);
        }

        query.setParameter("Cliente", idCliente);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("StartDate", start);
        query.setParameter("EndDate", end);
        query.setParameter("NroVenta", nroVenta);

        boolean execute = query.execute();
        List<PracticaDispensacion> resultList = query.getResultList();
        return resultList;
    }

}
