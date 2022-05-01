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
public class IntervSanitariaPk implements Serializable {

    private String idIntervSanitaria;
    private Integer idProducto;

    /**
     * @return the idIntervSanitaria
     */
    public String getIdIntervSanitaria() {
        return idIntervSanitaria;
    }

    /**
     * @param idIntervSanitaria the idIntervSanitaria to set
     */
    public void setIdIntervSanitaria(String idIntervSanitaria) {
        this.idIntervSanitaria = idIntervSanitaria;
    }

    /**
     * @return the idProducto
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.idIntervSanitaria != null ? this.idIntervSanitaria.hashCode() : 0);
        hash = 37 * hash + (this.idProducto != null ? this.idProducto.hashCode() : 0);
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
        final IntervSanitariaPk other = (IntervSanitariaPk) obj;
        if (!this.idIntervSanitaria.equals(other.idIntervSanitaria) && (this.idIntervSanitaria == null || !this.idIntervSanitaria.equals(other.idIntervSanitaria))) {
            return false;
        }
        return !(this.idProducto.equals(other.idProducto) && (this.idProducto == null || !this.idProducto.equals(other.idProducto)));
    }
    
    
}
