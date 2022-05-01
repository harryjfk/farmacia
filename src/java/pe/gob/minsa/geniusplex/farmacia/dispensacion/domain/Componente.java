/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Componente")
public class Componente extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    
    @Column(name = "Codigo")
    private String codigo;
    
    @Column(name = "Descripcion")
    private String descripcion;
    
    @JoinColumn(name = "Componente")
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubComponente> subComponentes;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    /**
     * @return the subComponentes
     */
    public List<SubComponente> getSubComponentes() {
        return subComponentes;
    }

    /**
     * @param subComponentes the subComponentes to set
     */
    public void setSubComponentes(List<SubComponente> subComponentes) {
        this.subComponentes = subComponentes;
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
        if (!(object instanceof Componente)) {
            return false;
        }
        Componente other = (Componente) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return this.getDescripcion();
    }
}
