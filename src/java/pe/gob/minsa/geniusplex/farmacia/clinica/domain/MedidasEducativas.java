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
@Table(name="Far_MedidasEducativas")
public class MedidasEducativas extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "Fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    @Column(name="Boleta")
    private int Boleta;
    @Column(name="Diptico")
    private int Diptico;
    @Column(name="Triptico")
    private int Triptico;
    @Column(name="Afiches")
    private int Afiches;
    @Column(name="Otros")
    private int Otros;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="Paciente")
    private Paciente paciente;
    @Column(name="Tema")
    private String tema;
    @Column(name = "Servicio")
    private String servicio;
    @Column(name = "IdModulo")
    private long IdModulo;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getBoleta() {
        return Boleta;
    }

    public void setBoleta(int Boleta) {
        this.Boleta = Boleta;
    }

    public int getDiptico() {
        return Diptico;
    }

    public void setDiptico(int Diptico) {
        this.Diptico = Diptico;
    }

    public int getTriptico() {
        return Triptico;
    }

    public void setTriptico(int Triptico) {
        this.Triptico = Triptico;
    }

    public int getAfiches() {
        return Afiches;
    }

    public void setAfiches(int Afiches) {
        this.Afiches = Afiches;
    }

    public int getOtros() {
        return Otros;
    }

    public void setOtros(int Otros) {
        this.Otros = Otros;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 73 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
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
        final MedidasEducativas other = (MedidasEducativas) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PRM{" + "id=" + id + ", fecha=" + fecha + ", Boleta=" + Boleta + ", Diptico=" + Diptico + ", Triptico=" + Triptico + ", Afiches=" + Afiches + ", Otros=" + Otros + ", paciente=" + paciente + ", tema=" + tema + ", servicio=" + servicio + '}';
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    } 
    
}