/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Intervencion")
public class Intervencion extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdIntervencion")
    private long id;
    
    @Column(name = "Espeicalidad")   
    private String especialidad;
    
    @Column(name = "Atendida")
    private Integer atendida;
    
    @Column(name = "Programada")
    private Integer programada;
    
    @JsonDeserialize(as = Paciente.class)
    @JoinColumn(name = "Paciente")
    @ManyToOne
    private Paciente paciente;
    
    @JsonDeserialize(as = Prescriptor.class)
    @JoinColumn(name = "Medico")
    @ManyToOne
    private Prescriptor medico;
    
    @JsonDeserialize(as = List.class, contentAs = IntervProducto.class)
    @OneToMany(mappedBy = "intervencion", cascade = CascadeType.ALL)
    private List<IntervProducto> intervProductos;
    
    @JsonDeserialize(as = Date.class)
    @Column(name = "FechaIntervencion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIntervencion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the especialidad
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @return the atendida
     */
    public Integer getAtendida() {
        return atendida;
    }

    /**
     * @param atendida the atendida to set
     */
    public void setAtendida(Integer atendida) {
        this.atendida = atendida;
    }

    /**
     * @return the programada
     */
    public Integer getProgramada() {
        return programada;
    }

    /**
     * @param programada the programada to set
     */
    public void setProgramada(Integer programada) {
        this.programada = programada;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paaciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the medico
     */
    public Prescriptor getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(Prescriptor medico) {
        this.medico = medico;
    }

    /**
     * @return the intervProductos
     */
    public List<IntervProducto> getIntervProductos() {
        return intervProductos;
    }

    /**
     * @param intervProductos the intervProductos to set
     */
    public void setIntervProductos(List<IntervProducto> intervProductos) {
        this.intervProductos = intervProductos;
    }

    /**
     * @return the fechaIntervencion
     */
    public Date getFechaIntervencion() {
        return fechaIntervencion;
    }

    /**
     * @param fechaIntervencion the fechaIntervencion to set
     */
    public void setFechaIntervencion(Date fechaIntervencion) {
        this.fechaIntervencion = fechaIntervencion;
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
        if (!(object instanceof Intervencion)) {
            return false;
        }
        Intervencion other = (Intervencion) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Intervencion[ id=" + id + " ]";
    }

    @Override
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }
    
}
