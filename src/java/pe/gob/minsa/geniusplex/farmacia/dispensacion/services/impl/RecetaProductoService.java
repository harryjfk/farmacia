/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Receta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.RecetaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;

/**
 *
 * @author stark
 */
public class RecetaProductoService extends GpServiceManager<RecetaProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @Autowired
    private ProductoHelper producto;

    public RecetaProductoService() {
        super(RecetaProducto.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    public List<RecetaProducto> listarRecetasProductos(Object idReceta) {
        TypedQuery<RecetaProducto> q = em.createQuery("SELECT r FROM RecetaProducto r WHERE r.idReceta = :idReceta", RecetaProducto.class);
        List<RecetaProducto> lista = q.setParameter("idReceta", idReceta).getResultList();
        for (RecetaProducto r : lista) {

            r.setProductoLote(producto.getProductoLote(r.getIdAlmacen(), r.getIdLote(), r.getIdProducto()));
        }
        return lista;
    }

    public List<RecetaProducto> listarRecetasPacienteProducto(Object idModulo,Object idPaciente, Object idLote, Object idAlmacen, Object idProducto) {
        TypedQuery<RecetaProducto> q = em.createQuery("SELECT r FROM RecetaProducto r WHERE r.paciente = :idPaciente AND r.idProducto =:idProducto AND r.idLote = :idLote AND r.idAlmacen =:idAlmacen AND r.IdModulo=:idModulo", RecetaProducto.class);
        q.setParameter("idLote", idLote);
        q.setParameter("idProducto", idProducto);
        q.setParameter("idAlmacen", idAlmacen);
        q.setParameter("idModulo", idModulo);
        List<RecetaProducto> lista = q.setParameter("idPaciente", idPaciente).getResultList();
        for (RecetaProducto r : lista) {

            r.setProductoLote(producto.getProductoLote(r.getIdAlmacen(), r.getIdLote(), r.getIdProducto()));
        }
        return lista;
    }

    public boolean eliminarRecetasProductos(Object idReceta) {
        List<RecetaProducto> lista = listarRecetasProductos(idReceta);
        boolean result = true;

        for (RecetaProducto recetaProducto : lista) {
            try {
                em.getTransaction().begin();
                em.remove(em.merge(recetaProducto));
                em.getTransaction().commit();
            } catch (Exception ex) {
                result = false;
                em.getTransaction().rollback();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public List<RecetaProducto> ConsultaReceta(long idModulo, long paciente, Date fechaIni, Date fechaFin) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_Recetas_Consultar", RecetaProducto.class);
        query.registerStoredProcedureParameter("Paciente", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FechaIni", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("FechaFin", String.class, ParameterMode.IN);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        query.setParameter("Paciente", paciente);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("FechaIni", fechaIni != null? format.format(fechaIni): null);
        query.setParameter("FechaFin", fechaFin != null? format.format(fechaFin): null);

        List<RecetaProducto> resultList = query.getResultList();
        for (RecetaProducto r : resultList) {

            r.setProductoLote(producto.getProductoLote(r.getIdAlmacen(), r.getIdLote(), r.getIdProducto()));
        }
        return resultList;
    }

}
