/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;

/**
 *
 * @author armando
 */
public class IntervProductoPk implements Serializable {
    
    
    private long idIntervencion;
    private int idProducto;

    /**
     * @return the idIntervencion
     */
    public long getIdIntervencion() {
        return idIntervencion;
    }

    /**
     * @param idIntervencion the Intervencion Id to set
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
     * @param idProducto the Producto Id to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.idIntervencion ^ (this.idIntervencion >>> 32));
        hash = 71 * hash + this.idProducto;
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
        final IntervProductoPk other = (IntervProductoPk) obj;
        if (this.idIntervencion != other.idIntervencion) {
            return false;
        }
        return this.idProducto == other.idProducto;
    }
    
    
    
}
