/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Kit_Atencion_Producto")
@IdClass(KitAtencionProductoPk.class)
public class KitAtencionProducto extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IdKitAtencion", insertable = false, updatable = false)
    private long idKitAtencion;
    
    @Id
    @Column(name = "IdProducto", insertable = false, updatable = false)
    private Integer idProducto;
    
    @Column(name = "Cantidad")
    private double cantidad;
    
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH} )
    @JoinColumn(name = "IdKitAtencion")
    private KitAtencion kitAtencion;
    
    @ManyToOne
    @JoinColumn(name = "IdProducto")
    private GpProducto producto;
    
     public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the kitAtencion
     */
    public KitAtencion getKitAtencion() {
        return kitAtencion;
    }

    /**
     * @param kitAtencion the kitAtencion to set
     */
    public void setKitAtencion(KitAtencion kitAtencion) {
        this.kitAtencion = kitAtencion;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (int) (this.idKitAtencion ^ (this.idKitAtencion >>> 32));
        hash = 71 * hash + (this.idProducto != null ? this.idProducto.hashCode() : 0);
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
        final KitAtencionProducto other = (KitAtencionProducto) obj;
        if (this.idKitAtencion != other.idKitAtencion) {
            return false;
        }
        return this.idProducto == other.idProducto;
    }

    @Override
    public String toString() {
        return idKitAtencion + idProducto + "";
    }

    @Override
    public void prePersist() {}
}
