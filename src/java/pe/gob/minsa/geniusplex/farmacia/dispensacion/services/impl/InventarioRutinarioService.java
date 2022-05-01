/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.InventarioRutinario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;

@Service("gpInventarioRutinario")
public class InventarioRutinarioService extends GpServiceManager<InventarioRutinario> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    @Autowired
    private ProductoHelper producto;

    @Autowired
    private ProductoHelper productoHelper;

    @Autowired
    private GpAlmacenService almacenService;

    public InventarioRutinarioService() {
        super(InventarioRutinario.class);
    }

    @PostConstruct
    public void init() {
        EntityManager em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<InventarioRutinario> listarModulo(Object idModulo) {
        ListaModulo<InventarioRutinario> listaModulo = new ListaModulo<InventarioRutinario>(InventarioRutinario.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        List<InventarioRutinario> lista = listaModulo.listarPorModulo(idModulo);
        setProductoLote(lista);
        return lista;
    }

    public List<InventarioRutinario> listarModuloAlmacen(Object idModulo, Object idAlmacen) {
        TypedQuery<InventarioRutinario> q = em.createQuery("SELECT i FROM InventarioRutinario i WHERE i.IdModulo = :idModulo AND i.idAlmacen = :idAlmacen", InventarioRutinario.class);
        q.setParameter("idModulo", idModulo);
        List<InventarioRutinario> lista = q.setParameter("idAlmacen", idAlmacen).getResultList();
        setProductoLote(lista);
        return lista;
    }

    public boolean contieneProducto(Object idModulo, Object idAlmacen, Object idProducto, Object idLote) {
        TypedQuery<InventarioRutinario> q = em.createQuery("SELECT i FROM InventarioRutinario i WHERE i.IdModulo = :idModulo AND i.idAlmacen = :idAlmacen AND i.idProducto = :idProducto AND i.lote = :idLote", InventarioRutinario.class);
        q.setParameter("idModulo", idModulo);
        q.setParameter("idProducto", idProducto);
        q.setParameter("idLote", idLote);
        List<InventarioRutinario> lista = q.setParameter("idAlmacen", idAlmacen).getResultList();
        setProductoLote(lista);
        return lista.size() > 0;
    }

    public List<InventarioRutinario> ConsultarModuloAlmacen(Object idModulo, Object idAlmacen, Date startDate, Date endDate) {
        TypedQuery<InventarioRutinario> q = em.createQuery("SELECT i FROM InventarioRutinario i WHERE i.IdModulo = :idModulo AND i.idAlmacen = :idAlmacen AND ((i.fechaCreacion BETWEEN :start AND :end) OR (:start is null AND :end is null))", InventarioRutinario.class);
        q.setParameter("idModulo", idModulo);
        q.setParameter("start", startDate);
        q.setParameter("end", endDate);
        List<InventarioRutinario> lista = q.setParameter("idAlmacen", idAlmacen).getResultList();
        setProductoLote(lista);
        return lista;
    }

    public List<InventarioRutinario> ConsultaInventarioRutinario(long idModulo, long idAlmacen, Date startDate, Date endDate) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_InventarioRutinario_Consultar", InventarioRutinario.class);
        query.registerStoredProcedureParameter("Almacen", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start = null;
        String end = null;
        if (startDate != null) {
            start = format.format(startDate);

        }
        if (endDate != null) {
            end = format.format(endDate);
        }

        query.setParameter("Almacen", idAlmacen);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("StartDate", start);
        query.setParameter("EndDate", end);

        List<InventarioRutinario> resultList = query.getResultList();
        this.setProductoLote(resultList);
        return resultList;
    }

    private void setProductoLote(List<InventarioRutinario> resultList) {
        for (InventarioRutinario d : resultList) {
            ProductoLote productoLote = producto.getProductoLote(d.getIdAlmacen(), d.getLote(), d.getIdProducto());
            d.setProductolote(productoLote);
        }
    }

    /**
     * Buscar el Stock de un producto en todos los almacenes, menos en los del
     * modulo seleccionado
     *
     * @param idModulo
     * @param idProducto
     * @return Mapa con el nombre del almacen y el stock
     */
    public Map<String, Integer> obtenerStocksGlobal(Long idModulo,
            Integer idProducto) {

        List<GpAlmacen> almacens = almacenService.listar();
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (GpAlmacen almacen : almacens) {
            if (almacen.getFarmacia() != idModulo) {
                int stock = productoHelper.obtenerStock(idProducto, almacen.getIdAlmacen());
                result.put(almacen.getDescripcion(), stock);
            }
        }
        return result;
    }
}
