/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.HFTMedicamentoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;

/**
 *
 * @author stark
 */
@Entity
@Table(name="Far_FarmacoTerapeutico")
public class HFT extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="Paciente")
    private Paciente paciente;
    
    @Column(name="Servicio")
    private String servicio;
    
    @Column(name="Diagnostico")
    private String diagnostico;
    
    @Column(name = "Alergias")
    private String alergias;
    
    @Column(name = "Evolucion")
    private String evolucion;
    
    @Column(name = "Observaciones")
    private String observacione;
    
    @Column(name = "IMC")
    private String imc;
    
    @Column(name = "ExamenesAuxiliares")
    private String examenes;
    
    @Column(name = "Terapia")
    private String terapia;
    
    @Column(name ="Fluido")
    private String fluido;
    
    @Column(name = "IdModulo")
    private Long IdModulo;
    
    @Column(name="Edad")
    private String edad;
    
    @Column(name="Peso")
    private String peso;
    
    @Column(name="FechaIngreso")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngreso;
    
    @Column(name="FechaAlta")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    
    @Column(name="Sis")
    private String sis;
    
    @Column(name="Cama")
    private String cama;
    @JoinColumn(name="Medicamento")
    @ManyToMany
    private List<HFTMedicamento> medicamentos;
   
    
    public Long getIdModulo() {
        return IdModulo;
    }
    
    public void setIdModulo(Long idModulo) {
        IdModulo = idModulo;
    }
    

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

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }

    public String getObservacione() {
        return observacione;
    }

    public void setObservacione(String observacione) {
        this.observacione = observacione;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getExamenes() {
        return examenes;
    }

    public void setExamenes(String examenes) {
        this.examenes = examenes;
    }

    public String getFluido() {
        return fluido;
    }

    public void setFluido(String fluido) {
        this.fluido = fluido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getSis() {
        return sis;
    }

    public void setSis(String sis) {
        this.sis = sis;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.paciente != null ? this.paciente.hashCode() : 0);
        hash = 29 * hash + (this.IdModulo != null ? this.IdModulo.hashCode() : 0);
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
        final HFT other = (HFT) obj;
        if (this.paciente != other.paciente && (this.paciente == null || !this.paciente.equals(other.paciente))) {
            return false;
        }
        if (this.IdModulo != other.IdModulo && (this.IdModulo == null || !this.IdModulo.equals(other.IdModulo))) {
            return false;
        }
        return true;
    }

    public String getTerapia() {
        return terapia;
    }

    public void setTerapia(String terapia) {
        this.terapia = terapia;
    }

    public List<HFTMedicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<HFTMedicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    
    
    
    
    
}
