/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;

/**
 *
 * @author stark
 */
@Entity
@Table(name="Far_PRM")
public class PRM extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="Paciente")
    private Paciente paciente;
    @Column(name="Servicio")
    private String servicio;
    
    @Column(name="Fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    @Column(name = "IdModulo")
    private long IdModulo;
    @Column(name = "Edad")
    private int edad;
    @Column(name = "HstoriaClinica")
    private String historia;
    @Column(name = "Diagnostico")
    private String diagnostico;
    @Column(name="Prm")
    private String prm;
    @Column(name="IntervencionFarmaceutica")
    private String intervencion;
    @Column(name="Ram")
    private String ram;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrm() {
        return prm;
    }

    public void setPrm(String prm) {
        this.prm = prm;
    }

    public String getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(String intervencion) {
        this.intervencion = intervencion;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PRM)) {
            return false;
        }
        PRM other = (PRM) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.clinica.domain.PRM[ id=" + id + " ]";
    }
    
}
