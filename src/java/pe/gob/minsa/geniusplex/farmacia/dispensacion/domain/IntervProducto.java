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
@IdClass(IntervProductoPk.class)
@Table(name = "Far_Intervencion_Producto")
public class IntervProducto extends RemoteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    @Column(name = "IdIntervencion", insertable = false, updatable = false)
    private long idIntervencion;

    @JsonIgnore
    @Id
    @Column(name = "idProducto", insertable = false, updatable = false)
    private int idProducto;

    @Column(name = "Cantidad", precision = 2)
    private double cantidad;

    @JsonIgnore
    @JoinColumn(name = "IdIntervencion")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Intervencion intervencion;

    @JsonDeserialize(as = GpProducto.class)
    @JoinColumn(name = "IdProducto")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private GpProducto producto;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.getIdIntervencion() ^ (this.getIdIntervencion() >>> 32));
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
        final IntervProducto other = (IntervProducto) obj;
        if (this.getIdIntervencion() != other.getIdIntervencion()) {
            return false;
        }
        return this.getIdProducto() == other.getIdProducto();
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * @return the idIntervencion
     */
    public long getIdIntervencion() {
        return idIntervencion;
    }

    /**
     * @param idIntervencion the idIntervencion to set
     */
    public void setIdIntervencion(long idIntervencion) {
        this.idIntervencion = idIntervencion;
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
     * @return the intervencion
     */
    public Intervencion getIntervencion() {
        return intervencion;
    }

    /**
     * @param intervencion the intervencion to set
     */
    public void setIntervencion(Intervencion intervencion) {
        this.intervencion = intervencion;
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
