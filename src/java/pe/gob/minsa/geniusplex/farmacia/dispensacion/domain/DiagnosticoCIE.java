/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Diagnostico_CIE")
public class DiagnosticoCIE extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String codigo;

    @Column(name = "Descripcion")
    private String descripcion;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final DiagnosticoCIE other = (DiagnosticoCIE) obj;
        return !((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
}
