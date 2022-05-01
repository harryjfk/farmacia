/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author stark
 */
@Entity
@Table(name="Far_Ram")
public class RAM extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="Paciente")
    private String paciente;
    
    @Column(name="Edad")
    private String edad;
    
    @Column(name="Sexo")
    private String sexo;
    
    @Column(name="Peso")
    private String peso;
    
    @Column(name="HistoriaClinica")
    private String historia;
    
    @Column(name="Establecimiento")
    private String establecimiento;
    
    @Column(name="Categoria")
    private String categoria;
    
    @Column(name = "Medico")
    private String medico;
    
    @Column(name="Direccion")
    private String direccion;
    
    @Column(name="Telefono")
    private String telefono;
    
    @Column(name="Fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    @Column(name = "Observaciones")
    private String observaciones;
    
    @JoinColumn(name="Medicamentos")
    @ManyToMany
    private List<MedicamentoSospechoso> medicamentos;
    
    @JoinColumn(name="Reacciones")
    @ManyToMany
    private List<ReaccionesAdversas> reacciones;
    
    @JoinColumn
    @ManyToMany
    private List<OtrosMedicamentos> otros;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.paciente != null ? this.paciente.hashCode() : 0);
        hash = 43 * hash + (this.historia != null ? this.historia.hashCode() : 0);
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
        final RAM other = (RAM) obj;
        if ((this.paciente == null) ? (other.paciente != null) : !this.paciente.equals(other.paciente)) {
            return false;
        }
        return !((this.historia == null) ? (other.historia != null) : !this.historia.equals(other.historia));
    }

    

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.clinica.domain.RAM[ id=" + id + " ]";
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<MedicamentoSospechoso> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<MedicamentoSospechoso> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<ReaccionesAdversas> getReacciones() {
        return reacciones;
    }

    public void setReacciones(List<ReaccionesAdversas> reacciones) {
        this.reacciones = reacciones;
    }

    public List<OtrosMedicamentos> getOtros() {
        return otros;
    }

    public void setOtros(List<OtrosMedicamentos> otros) {
        this.otros = otros;
    }
    
    
    
    
}
