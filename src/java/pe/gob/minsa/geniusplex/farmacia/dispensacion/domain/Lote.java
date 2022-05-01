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

public class Lote extends BaseEntity implements Serializable {
   
    private long id;
    private String descripcion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lote() {
    }

    public Lote(String descripcion) {
        this.descripcion = descripcion;
        id = 1;
    }
    
    
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Lote[ id=" + id + " ]";
    }
    
}
