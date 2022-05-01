/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author stark
 */
@Entity
@Table(name="Far_Reacciones")
public class ReaccionesAdversas extends BaseEntity implements Serializable {
    @JsonIgnore
    @ManyToMany(mappedBy = "reacciones")
    private List<RAM> rams;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Reaccion")
    private String reaccion;
    
    @Column(name="FechaInicio")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name="FechaFinal")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinal;
    
    @Column(name="Evolucion")
    private  String evolucion;
            

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.clinica.domain.ReaccionesAdversas[ id=" + id + " ]";
    }

    public String getReaccion() {
        return reaccion;
    }

    public void setReaccion(String reaccion) {
        this.reaccion = reaccion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 47 * hash + (this.reaccion != null ? this.reaccion.hashCode() : 0);
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
        final ReaccionesAdversas other = (ReaccionesAdversas) obj;
        if ((this.reaccion == null) ? (other.reaccion != null) : !this.reaccion.equals(other.reaccion)) {
            return false;
        }
        return true;
    }

    public List<RAM> getRams() {
        return rams;
    }

    public void setRams(List<RAM> rams) {
        this.rams = rams;
    }
    
}
