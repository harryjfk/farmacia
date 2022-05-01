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
public class CompraUrgenciaProductoPk implements Serializable {
    
    
    private long idCompraDeUrgencia;
    private int idProducto;

    /**
     * @return the idCompraDeUrgencia
     */
    public long getIdCompraDeUrgencia() {
        return idCompraDeUrgencia;
    }

    /**
     * @param idCompraDeUrgencia the Intervencion Id to set
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
     * @param idProducto the Producto Id to set
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.idCompraDeUrgencia ^ (this.idCompraDeUrgencia >>> 32));
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
        final CompraUrgenciaProductoPk other = (CompraUrgenciaProductoPk) obj;
        if (this.idCompraDeUrgencia != other.idCompraDeUrgencia) {
            return false;
        }
        return this.idProducto == other.idProducto;
    }
    
    
    
}
