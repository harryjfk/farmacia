/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@IdClass(CompraUrgenciaProductoPk.class)
@Table(name = "Far_Compra_De_Urgencia_Producto")
public class CompraUrgenciaProducto extends RemoteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    @Column(name = "IdCompraDeUrgencia", insertable = false, updatable = false)
    private long idCompraDeUrgencia;

    @JsonIgnore
    @Id
    @Column(name = "idProducto", insertable = false, updatable = false)
    private int idProducto;

    @Column(name = "Cantidad", precision = 2)
    private double cantidad;

    @JsonIgnore
    @JoinColumn(name = "IdCompraDeUrgencia")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private CompraDeUrgencia compraDeUrgencia;

    @JsonDeserialize(as = GpProducto.class)
    @JoinColumn(name = "IdProducto")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private GpProducto producto;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.getIdCompraDeUrgencia() ^ (this.getIdCompraDeUrgencia() >>> 32));
        hash = 97 * hash + this.getIdProducto();
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
        final CompraUrgenciaProducto other = (CompraUrgenciaProducto) obj;
        if (this.getIdCompraDeUrgencia() != other.getIdCompraDeUrgencia()) {
            return false;
        }
        return this.getIdProducto() == other.getIdProducto();
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * @return the idCompraDeUrgencia
     */
    public long getIdCompraDeUrgencia() {
        return idCompraDeUrgencia;
    }

    /**
     * @param idCompraDeUrgencia the idCompraDeUrgencia to set
     */
    public void setIdCompraDeUrgencia(long idCompraDeUrgencia) {
        this.idCompraDeUrgencia = idCompraDeUrgencia;
    }

    /**
     * @return the idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the compraDeUrgencia
     */
    public CompraDeUrgencia getCompraDeUrgencia() {
        return compraDeUrgencia;
    }

    /**
     * @param compraDeUrgencia the compraDeUrgencia to set
     */
    public void setCompraDeUrgencia(CompraDeUrgencia compraDeUrgencia) {
        this.compraDeUrgencia = compraDeUrgencia;
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

}
