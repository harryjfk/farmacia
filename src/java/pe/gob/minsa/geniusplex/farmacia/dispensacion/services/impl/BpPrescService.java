/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProductoPk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.enums.VentaEstadoEnum;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.BpPrescripcionDTO;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author stark
 */
@Service("bpPrescService")
public class BpPrescService extends GpServiceManager<Venta> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    @Autowired
    VentaService ventaService;
    @Autowired
    VentaProductoService ventaProductoService;
    @Autowired
    GpProductoService productoService;

    public BpPrescService() {
        super(Venta.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public BpPrescripcionDTO consultar(FilterData fData) {

        Integer periodo = null;
        boolean hasPeriodo = false;
        if (fData.getParams().containsKey("periodo")) {
            periodo = Integer.parseInt(fData.getParams().get("periodo").toString());
            fData.getParams().remove("periodo");
            hasPeriodo = true;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(Venta.class);
        Root<Venta> fromVenta = cq.from(Venta.class);

        Predicate[] predicates = new Predicate[]{cb.isTrue(fromVenta.<Boolean>get("procesoVenta"))};
        if (fData.getParams().containsKey("cliente:idCliente")) {
            Object idCliente = fData.getParams().get("cliente:idCliente");
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromVenta.get("cliente").get("idCliente"), idCliente));
        }
        if (fData.getParams().containsKey("ventafechaRegistro")) {
            Object fecha = fData.getParams().get("ventafechaRegistro");
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromVenta.get("ventafechaRegistro"), fecha));
        }
        if (fData.getParams().containsKey("numeroDeReceta")) {
            Object receta = fData.getParams().get("numeroDeReceta");
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromVenta.get("numeroDeReceta"), receta));
        }
        if (fData.getParams().containsKey("diagnostico:codigo")) {
            Object diagnostico = fData.getParams().get("diagnostico:codigo");
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromVenta.get("diagnostico").get("codigo"), diagnostico));
        }
        
        cq.where(predicates);
        TypedQuery<Venta> query = em.createQuery(cq);
        List<Venta> tmpVentas = query.getResultList();
        List<Venta> ventas = new ArrayList<Venta>();
        Calendar calendar = GregorianCalendar.getInstance();

        for (Venta tmpVenta : tmpVentas) {
            sincronizar(tmpVenta);
            boolean cancelada = tmpVenta.getEstado() == VentaEstadoEnum.CANCELADA;
            Boolean anulada = tmpVenta.isAnulada();
            if (!cancelada && (anulada == null || !anulada)) {
                if (hasPeriodo) {
                    calendar.setTime(tmpVenta.getVentafechaRegistro());
                    int year = calendar.get(Calendar.YEAR);
                    if (year == periodo) {
                        ventas.add(tmpVenta);
                    }
                } else {
                    ventas.add(tmpVenta);
                }

            }
        }
        if (ventas.isEmpty()) {
            return null;
        }
        BpPrescripcionDTO dto = new BpPrescripcionDTO(ventas);
        return dto;
    }

    public boolean editarProducto(long idVenta, int idProd, double cantidad, int idProdNew) {
        VentaProductoPk pk = new VentaProductoPk(idVenta, idProd);
        VentaProducto ventaProducto = ventaProductoService.obtenerPorId(pk);

        if (ventaProducto != null) {
            if (idProd != idProdNew) {
                ventaProducto = new VentaProducto();

                Venta venta = ventaService.obtenerPorId(idVenta);
                ventaProducto.setVenta(venta);

                GpProducto prodNew = productoService.obtenerPorId(idProdNew);
                ventaProducto.setProducto(prodNew);

                BigDecimal precio = ventaService.obtenerPrecioDeProducto(idProdNew);
                ventaProducto.setPrecio(precio);

                ventaProductoService.eliminar(pk);

                ventaProducto.setCantidad(cantidad);
                return ventaProductoService.insertar(ventaProducto);
            }
            ventaProducto.setCantidad(cantidad);
            return ventaProductoService.actualizar(ventaProducto);
        }
        return false;
    }

    public HashMap<String, Object> getEditData(long idVenta, int idProd) {
        VentaProductoPk pk = new VentaProductoPk(idVenta, idProd);
        VentaProducto ventaProducto = ventaProductoService.obtenerPorId(pk);
        if (ventaProducto != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("cantidad", ventaProducto.getCantidad());
            map.put("producto", ventaProducto.getProducto().getIdProducto());
            return map;
        }
        return null;
    }

    public boolean eliminar(long idVenta, int idProd) {
        VentaProductoPk pk = new VentaProductoPk(idVenta, idProd);
        return ventaProductoService.eliminar(pk);
    }

}
