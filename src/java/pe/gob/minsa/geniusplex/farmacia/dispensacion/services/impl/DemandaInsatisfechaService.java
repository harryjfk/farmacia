/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DemandaInsatisfecha;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Lote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;

/**
 *
 * @author stark
 */
@Service("demandaInsatisfecha")
public class DemandaInsatisfechaService extends GpServiceManager<DemandaInsatisfecha> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    @Autowired
    DataSource dataSource;

    @Autowired
    private ProductoHelper producto;
    @Autowired
    private GpAlmacenService almacenService;
    @Autowired
    private GpProductoService productoService;

    public DemandaInsatisfechaService() {
        super(DemandaInsatisfecha.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<DemandaInsatisfecha> listarModulo(Object idModulo) {
        ListaModulo<DemandaInsatisfecha> listaModulo = new ListaModulo<DemandaInsatisfecha>(DemandaInsatisfecha.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        List<DemandaInsatisfecha> resultList = listaModulo.listarPorModulo(idModulo);

        setProductoLote(resultList);
        return resultList;
    }

    @Override
    public DemandaInsatisfecha obtenerPorId(Object id) {
        DemandaInsatisfecha d = super.obtenerPorId(id);
        ProductoLote productoLote = producto.getProductoLote(d.getIdAlmacen(), d.getLote(), d.getIdProducto());
        d.setProducto(productoLote);
        return d;
    }

    private void setProductoLote(List<DemandaInsatisfecha> resultList) {
        for (DemandaInsatisfecha d : resultList) {
            ProductoLote productoLote = producto.getProductoLote(d.getIdAlmacen(), d.getLote(), d.getIdProducto());
            if (productoLote != null) {
                d.setProducto(productoLote);
            } else {//creamos un producto lote ficticio, solo para mostrar los datos (esta demanda puede haber sido generada desde una preventa)
                productoLote = new ProductoLote();
                GpAlmacen almacen = almacenService.obtenerPorId(d.getIdAlmacen().intValue());
                productoLote.setAlmacen(almacen);
                
                productoLote.setCantidad(0);
                productoLote.setPrecio(BigDecimal.ZERO);
                
                GpProducto prod = productoService.obtenerPorId(d.getIdProducto());
                productoLote.setProducto(prod);
                Lote lote = new Lote();
                lote.setDescripcion("");
                productoLote.setLote(lote);
                d.setProducto(productoLote);
            }
        }
    }

    public List<DemandaInsatisfecha> listarModuloPaciente(Object idModulo, Object idCliente) {
        TypedQuery<DemandaInsatisfecha> q = em.createQuery("SELECT d FROM DemandaInsatisfecha d WHERE d.IdModulo = :idModulo AND d.cliente.idCliente = :idCliente", DemandaInsatisfecha.class);
        q.setParameter("idModulo", idModulo);
        List<DemandaInsatisfecha> lista = q.setParameter("idCliente", idCliente).getResultList();
        setProductoLote(lista);
        return lista;
    }

    public boolean existeProducto(Object idModulo, Object idCliente, long idProducto) {
        TypedQuery<DemandaInsatisfecha> q = em.createQuery("SELECT d FROM DemandaInsatisfecha d WHERE d.IdModulo = :idModulo AND d.cliente.idCliente = :idCliente AND d.idProducto=:producto", DemandaInsatisfecha.class);
        q.setParameter("idModulo", idModulo);
        q.setParameter("producto", idProducto);
        List<DemandaInsatisfecha> lista = q.setParameter("idCliente", idCliente).getResultList();

        return lista.size() > 0;
    }

    public List<DemandaInsatisfecha> ConsultaDemandaInsatisfecha(long idModulo, Long idCliente, Date startDate, Date endDate, int idAlmacen) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_Demanda_Insatisfecha_Consultar", DemandaInsatisfecha.class);
        query.registerStoredProcedureParameter("Cliente", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdAlmacen", Integer.class, ParameterMode.IN);

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
        query.setParameter("IdAlmacen", idAlmacen);

        boolean execute = query.execute();
        List<DemandaInsatisfecha> resultList = query.getResultList();
        setProductoLote(resultList);
        return resultList;
    }

}
