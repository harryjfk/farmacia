/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Equipo")
public class Equipo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "IdModulo")
    private long IdModulo;

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Equipo[ id=" + id + " ]";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 89 * hash + (int) (this.IdModulo ^ (this.IdModulo >>> 32));
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
        final Equipo other = (Equipo) obj;
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        return this.IdModulo == other.IdModulo;
    }

    
}
