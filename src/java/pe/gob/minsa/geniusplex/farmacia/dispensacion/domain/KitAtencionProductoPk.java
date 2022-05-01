/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;

/**
 * Llave primaria compuesta de KitAtencionProducto
 *
 * @author armando
 */
public class KitAtencionProductoPk implements Serializable {

    private long idKitAtencion;
    private Integer idProducto;

    public long getIdKitAtencion() {
        return idKitAtencion;
    }

    public void setIdKitAtencion(long idKitAtencion) {
        this.idKitAtencion = idKitAtencion;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.idKitAtencion ^ (this.idKitAtencion >>> 32));
        hash = 79 * hash + this.idProducto;
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
        final KitAtencionProductoPk other = (KitAtencionProductoPk) obj;
        if (this.idKitAtencion != other.idKitAtencion) {
            return false;
        }
        return this.idProducto == other.idProducto;
    }

}
