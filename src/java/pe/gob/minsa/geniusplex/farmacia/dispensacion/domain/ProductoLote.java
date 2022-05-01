/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */


public class ProductoLote extends BaseEntity implements Serializable {
    
    
    private long idProducto;
    
    
    private long idLote;
    
   
    private long idAlmacen;
    
    
    private Lote lote;
    
   
    private GpProducto producto;
    
    
    private GpAlmacen almacen;
    
   
    private String registroSanitario;
    
    
    private Date fechaVencimiento;
    
    
    private int cantidad;
    
    
    private BigDecimal precio;

    public ProductoLote() {
    }

    public ProductoLote(GpMovimientoProducto mov) {
        idProducto = mov.getIdProducto().getIdProducto();
        idLote = 1;
        idAlmacen = mov.getIdMovimiento().getIdAlmacenDestino().getIdAlmacen();
        lote =new Lote(mov.getLote());
        producto= mov.getIdProducto();
        almacen=mov.getIdMovimiento().getIdAlmacenDestino();
        fechaVencimiento = mov.getFechaVencimiento();
        cantidad = mov.getCantidad();
        precio = mov.getPrecio();
        
    }

    
    

    

    /**
     * @return the idLote
     */
    public long getIdLote() {
        return idLote;
    }

    /**
     * @return the idAlmacen
     */
    public long getIdAlmacen() {
        return idAlmacen;
    }

    /**
     * @return the lote
     */
    public Lote getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(Lote lote) {
        this.lote = lote;
    }

    /**
     * @return the producto
     */
    public GpProducto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }

    /**
     * @return the almacen
     */
    public GpAlmacen getAlmacen() {
        return almacen;
    }

    /**
     * @param almacen the almacen to set
     */
    public void setAlmacen(GpAlmacen almacen) {
        this.almacen = almacen;
    }

    /**
     * @return the registroSanitario
     */
    public String getRegistroSanitario() {
        return registroSanitario;
    }

    /**
     * @param registroSanitario the registroSanitario to set
     */
    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public BigDecimal getTotal() {
        return getPrecio().add(new BigDecimal(String.valueOf(getCantidad())));
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.idProducto ^ (this.idProducto >>> 32));
        hash = 67 * hash + (int) (this.idLote ^ (this.idLote >>> 32));
        hash = 67 * hash + (int) (this.idAlmacen ^ (this.idAlmacen >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoLote other = (ProductoLote) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idLote != other.idLote) {
            return false;
        }
        return this.idAlmacen == other.idAlmacen;
    }
    
}
