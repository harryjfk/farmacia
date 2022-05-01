/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;

/**
 *
 * @author stark
 */
public class RecetaProductoPk implements Serializable{
    
    private long idReceta;
    private int idProducto;
    private String idLote;
    private long idAlmacen;

    public RecetaProductoPk() {
    }

    public RecetaProductoPk(long idReceta, int idProducto, String idLote, long idAlmacen) {
        this.idReceta = idReceta;
        this.idProducto = idProducto;
        this.idLote = idLote;
        this.idAlmacen = idAlmacen;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.idReceta ^ (this.idReceta >>> 32));
        hash = 37 * hash + (int) (this.idProducto ^ (this.idProducto >>> 32));
        hash = 37 * hash + (this.idLote != null ? this.idLote.hashCode() : 0);
        hash = 37 * hash + (int) (this.idAlmacen ^ (this.idAlmacen >>> 32));
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
        final RecetaProductoPk other = (RecetaProductoPk) obj;
        if (this.idReceta != other.idReceta) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if ((this.idLote == null) ? (other.idLote != null) : !this.idLote.equals(other.idLote)) {
            return false;
        }
        if (this.idAlmacen != other.idAlmacen) {
            return false;
        }
        return true;
    }

    
   

    
    

    public long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(long idReceta) {
        this.idReceta = idReceta;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public long getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(long idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
    
    
   

    
   
    
    
}
