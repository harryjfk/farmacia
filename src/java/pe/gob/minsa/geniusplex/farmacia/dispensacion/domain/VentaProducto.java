/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Venta_Producto")
@IdClass(VentaProductoPk.class)
public class VentaProducto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdVenta", insertable = false, updatable = false)
    private long idVenta;

    @Id
    @Column(name = "IdProducto", insertable = false, updatable = false)
    private int idProducto;

    @Basic(optional = false)
    @Column(name = "Cantidad")
    private Double cantidad;

    @Column(name = "CantidadModificada")
    private Integer cantidadActual;

    @JsonDeserialize(as = BigDecimal.class)
    @Column(name = "Precio", precision = 38, scale = 4)
    private BigDecimal precio;

    @JsonIgnore
    @JoinColumn(name = "IdVenta", referencedColumnName = "Id")
    @ManyToOne
    private Venta venta;

    @JsonDeserialize(as = GpProducto.class)
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    @ManyToOne(optional = false)
    private GpProducto producto;

    @Transient
    private int stock;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.idVenta ^ (this.idVenta >>> 32));
        hash = 83 * hash + this.idProducto;
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
        final VentaProducto other = (VentaProducto) obj;
        if (this.idVenta != other.idVenta) {
            return false;
        }
        return this.idProducto == other.idProducto;
    }

    @Override
    public String toString() {
        return "VentaProducto{" + "idVenta=" + idVenta + ", idProducto=" + idProducto + '}';
    }

    /**
     * @return the cantidad
     */
    public Double getCantidad() {
        return cantidad != null ? cantidad : 0;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidadActual() {
        return cantidadActual == null || cantidadActual <= 0 ? cantidad.intValue() : cantidadActual;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidadActual(Integer cantidad) {
        this.cantidadActual = cantidad;
    }

    /**
     * @return the venta
     */
    public Venta getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Venta venta) {
        this.venta = venta;
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

    public BigDecimal getImporteTotal() {
        Double cant = this.getCantidad();
        if (getCantidadActual() != null && getCantidadActual() > 0) {
            cant = (double) getCantidadActual();
        }
        return this.getPrecio()
                .multiply(new BigDecimal(cant.toString()))
                .setScale(4, RoundingMode.HALF_EVEN);
    }

    public void setImporteTotal(BigDecimal importeTotal) {
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int s) {
        stock = s;
    }

    @PrePersist
    public void checkModificado() {
        if (this.getCantidadActual() == null) {
            this.setCantidadActual(this.getCantidad().intValue());
        }
    }

}
