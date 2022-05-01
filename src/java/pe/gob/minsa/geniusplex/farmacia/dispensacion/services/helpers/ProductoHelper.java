/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.services.impl.MovimientoProductoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLotePk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProductoLoteService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProductoPrecio;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;

/**
 *
 * @author armando
 */
@Component("productoHelper")
public class ProductoHelper {

    @Autowired
    private ProductoLoteService productoLoteService;
    @Autowired
    private MovimientoProductoService movProdService;
    @Autowired
    GpProductoService productoService;

    public ProductoHelper() {
    }

    public BigDecimal obtenerPrecio(EntityManager em, Integer idProducto, String tipoPrecio) {
        GpProducto producto = em.find(GpProducto.class, idProducto);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpProductoPrecio> cq = cb.createQuery(GpProductoPrecio.class);
        Root<GpProductoPrecio> fromPrecio = cq.from(GpProductoPrecio.class);
        
        Subquery scq = cq.subquery(Date.class);
        Root<GpProductoPrecio> fromPrecio2 = scq.from(GpProductoPrecio.class);
        scq.select(cb.greatest(fromPrecio2.get("fechaRegistro").as(Date.class)))
                .where(cb.equal(fromPrecio2.get("producto"), producto));

        cq.where(cb.equal(fromPrecio.get("producto"), producto),
                cb.equal(scq, fromPrecio.get("fechaRegistro")));

        TypedQuery<GpProductoPrecio> q = em.createQuery(cq);
        GpProductoPrecio precio;
        try {
            precio = q.getSingleResult();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(ProductoHelper.class.getName()).log(Level.INFO, String.valueOf(idProducto), e);
            return BigDecimal.ZERO;
        }

        if (tipoPrecio.equalsIgnoreCase("ad")) {
            return precio.getPrecioAdquisicion();
        }
        if (tipoPrecio.equalsIgnoreCase("op")) {
            return precio.getPrecioOperacion();
        }
        if (tipoPrecio.equalsIgnoreCase("di")) {
            return precio.getPrecioDistribucion();
        }
        return BigDecimal.ZERO;
    }

    public List<GpProducto> refrescarProductos(EntityManager em, List<GpProducto> productos) {
        ArrayList<GpProducto> gpProductos = new ArrayList<GpProducto>();
        for (GpProducto producto : productos) {
            producto = em.getReference(GpProducto.class, producto.getIdProducto());
            gpProductos.add(producto);
        }
        return gpProductos;
    }

    public List<KitAtencionProducto> obtenerPorductosPorKitAtencion(EntityManager em, long kId, List<GpProducto> userSelected) throws BusinessException {
        KitAtencion kit = em.find(KitAtencion.class, kId);
        if (kit.getProductos().isEmpty()) {
            throw new BusinessException(Arrays.asList("El kit de atenci&oacute;n seleccionado no tiene productos asociados."));
        }
        userSelected = this.refrescarProductos(em, userSelected);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<KitAtencionProducto> cq = cb.createQuery(KitAtencionProducto.class);
        Root<KitAtencionProducto> from = cq.from(KitAtencionProducto.class);
        if (!userSelected.isEmpty()) {
            cq.where(
                    cb.equal(from.get("kitAtencion"), kit),
                    from.get("producto").in(userSelected).not()
            ).distinct(true);
        } else {
            cq.where(
                    cb.equal(from.get("kitAtencion"), kit)
            ).distinct(true);
        }
        TypedQuery<KitAtencionProducto> q = em.createQuery(cq);
        List<KitAtencionProducto> kitAtencionProductos = q.getResultList();
        return kitAtencionProductos;
    }

    public ProductoLote getProductoLote(Long idAlmacen, String lote, Integer idProducto) {
        ProductoLotePk productoLotePk = new ProductoLotePk(idProducto, lote, idAlmacen);
        ProductoLote producto = productoLoteService.obtenerPorId(productoLotePk);
        return producto;
    }

    public int getProductoStock(EntityManager em, int idProducto) {
        String priceQuery = "select mp.Cantidad AS cantidad from Far_Movimiento_Producto mp "
                + "         inner join Far_Movimiento m on m.IdMovimiento = mp.IdMovimiento "
                + "         inner join Far_Concepto c on c.IdConcepto = m.IdConcepto "
                + "where mp.IdProducto = " + idProducto
                + " and c.NombreConcepto = 'COMPRA' " //Este valor puede cambiar
                + "and m.FechaRegistro > "
                + "       all(select FechaRegistro from Far_Movimiento m1 "
                + "               inner join Far_Movimiento_Producto mp1 ON mp1.IdMovimiento = m1.IdMovimiento"
                + "               where mp1.IdProducto =  " + idProducto
                + "                   and m.FechaRegistro != m1.FechaRegistro"
                + "       )";
        Query query = em.createNativeQuery(priceQuery);
        List movPrds = query.getResultList();
        if (!movPrds.isEmpty()) {
            return Integer.parseInt(movPrds.get(0).toString());
        }
        return 0;
    }

    /**
     * Obtiene Stock por Almacen y Lote
     *
     * @param idProducto
     * @param idAlmacen
     * @param lote
     * @return
     */
    public int obtenerStock(int idProducto, int idAlmacen, String lote) {
        return movProdService.stockPorProductoYAlmacen(idProducto, idAlmacen, lote);
    }

    /**
     * Obtien Stock por Almacen y Lote
     *
     * @param idProducto
     * @param idAlmacen
     * @param lote
     * @return
     */
    public int obtenerStock(int idProducto, int idAlmacen) {
        int stock = 0;
        List<String> lotes = obtenerLote(idProducto, idAlmacen);
        for (String lote : lotes) {
            stock += obtenerStock(idProducto, idAlmacen, lote);
        }
        return stock;
    }
    
    public int obtenerStock(int idProducto, List<GpAlmacen> almacenes) {
        int stock = 0;
        for (GpAlmacen almacen : almacenes) {
            stock += this.obtenerStock(idProducto, almacen.getIdAlmacen());
        }
        return stock;
    }

    public List<String> obtenerLote(int idProducto, int idAlmacen) {
        return movProdService.obtenerLotes(idProducto, idAlmacen);
    }
    
    public String obtenerLoteConStock(int idProducto, int idAlmacen) {
        List<String> lotes = movProdService.obtenerLotes(idProducto, idAlmacen);
        for (String lote : lotes) {
            int sctock = obtenerStock(idProducto, idAlmacen, lote);
            if(sctock > 0) 
                return lote;
        }
        return null;
    }

    
    /**
     * Devuelve una descricion del producto mas detallada
     *
     * @param producto
     * @return 
     */
    public String obtenerDescProducto(GpProducto producto) {
        return String.format("%s - %s - %s - %s - %s",
                producto.getDescripcion(),
                producto.getConcentracion(),
                producto.getIdFormaFarmaceutica().getNombreFormaFarmaceutica(),
                producto.getIdUnidadMedida().getNombreUnidadMedida(),
                producto.getPresentacion());
    }
    
    /**
     * Devuelve una descripcion mas detalla del producto (incompleta, se
     * recomienda usuar la sobrecarga de GpProducto)
     *
     * @param producto
     * @return 
     */
    public String obtenerDescProducto(Producto producto) {
        return String.format("%s - %s - %s",
                producto.getDescripcion(),
                producto.getConcentracion(),
                producto.getPresentacion());
    }
    
}
