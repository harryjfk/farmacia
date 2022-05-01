/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author armando
 */
public class VentaProductoPk implements Serializable {

    private long idVenta;
    private int idProducto;

    public VentaProductoPk() {
    }

    public VentaProductoPk(long idVenta, int idProducto) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.idVenta ^ (this.idVenta >>> 32));
        hash = 37 * hash + this.idProducto;
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
        final VentaProductoPk other = (VentaProductoPk) obj;
        if (this.idVenta != other.idVenta) {
            return false;
        }
        return this.idProducto == other.idProducto;
    }

}
