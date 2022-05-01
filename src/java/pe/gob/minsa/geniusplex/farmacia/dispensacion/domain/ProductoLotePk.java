/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;

/**
 *
 * 
 */
public class ProductoLotePk implements Serializable{
    
    private long idProducto;
    private String idLote;
    private long idAlmacen;

    public ProductoLotePk() {
    }

    public ProductoLotePk(long idProducto, String idLote, long idAlmacen) {
        this.idProducto = idProducto;
        this.idLote = idLote;
        this.idAlmacen = idAlmacen;
    }

    /**
     * @return the idProducto
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the idLote
     */
    public String getIdLote() {
        return idLote;
    }

    /**
     * @param idLote the idLote to set
     */
    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    /**
     * @return the idAlmacen
     */
    public long getIdAlmacen() {
        return idAlmacen;
    }

    /**
     * @param idAlmacen the idAlmacen to set
     */
    public void setIdAlmacen(long idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.idProducto ^ (this.idProducto >>> 32));
        hash = 59 * hash + (this.idLote != null ? this.idLote.hashCode() : 0);
        hash = 59 * hash + (int) (this.idAlmacen ^ (this.idAlmacen >>> 32));
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
        final ProductoLotePk other = (ProductoLotePk) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idLote != other.idLote) {
            return false;
        }
        return this.idAlmacen == other.idAlmacen;
    }
    
    
    
}
