/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import pe.gob.minsa.farmacia.domain.Personal;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Intervencion_Sanitaria")
public class IntervSanitaria extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "NumeroSalida")
    private String nroSalida;

    @JsonDeserialize(as = Date.class)
    @Column(name = "FechaRegistro")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "HistClinica")
    private String nroHistClinica;

    @Column(name = "Coordinador")
    private String coordinador;

    @Column(name = "Referencia")
    private String referencia;

    @Column(name = "Anulada")
    private Boolean anulada;

    @JsonDeserialize(as = Cliente.class)
    @JoinColumn(name = "Cliete")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Cliente cliente;

    @JsonDeserialize(as = Prescriptor.class)
    @JoinColumn(name = "Prescriptor")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Prescriptor prescriptor;

    @JsonDeserialize(as = DiagnosticoCIE.class)
    @JoinColumn(name = "DiagnosticoCIE")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private DiagnosticoCIE diagnostico;

    @JsonDeserialize(as = Componente.class)
    @JoinColumn(name = "Componente")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Componente componente;

    @JsonDeserialize(as = SubComponente.class)
    @JoinColumn(name = "SubComponente")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private SubComponente subComponente;

    @JsonDeserialize(as = Proceso.class)
    @JoinColumn(name = "Proceso")
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Proceso proceso;

    @JsonDeserialize(as = List.class, contentAs = IntervSanitariaProducto.class)
    @OneToMany(mappedBy = "interSanitaria", cascade = CascadeType.REMOVE)
    private List<IntervSanitariaProducto> intervSanitariaProductos;

    @Column(name = "IdModulo")
    private Long IdModulo;

    @Transient
    public HashMap<String, String> datosCoor;
    @Transient
    private Personal coorPersonal;

    public Long getIdModulo() {
        return this.IdModulo;
    }

    public void setIdModulo(Long idModulo) {
        IdModulo = idModulo;
    }

    public String getNroSalida() {
        return nroSalida;
    }

    public void setNroSalida(String id) {
        this.nroSalida = id;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the nroHistClinica
     */
    public String getNroHistClinica() {
        return nroHistClinica;
    }

    /**
     * @param nroHistClinica the nroHistClinica to set
     */
    public void setNroHistClinica(String nroHistClinica) {
        this.nroHistClinica = nroHistClinica;
    }

    /**
     * @return the coordinador
     */
    public String getCoordinador() {
        return coordinador;
    }

    /**
     * @param coordinador the coordinador to set
     */
    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the anulada
     */
    public Boolean isAnulada() {
        return anulada;
    }

    /**
     * @param anulada the anulada to set
     */
    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the prescriptor
     */
    public Prescriptor getPrescriptor() {
        return prescriptor;
    }

    /**
     * @param prescriptor the prescriptor to set
     */
    public void setPrescriptor(Prescriptor prescriptor) {
        this.prescriptor = prescriptor;
    }

    /**
     * @return the diagnostico
     */
    public DiagnosticoCIE getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(DiagnosticoCIE diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the componente
     */
    public Componente getComponente() {
        return componente;
    }

    /**
     * @param componente the componente to set
     */
    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    /**
     * @return the subComponente
     */
    public SubComponente getSubComponente() {
        return subComponente;
    }

    /**
     * @param subComponente the subComponente to set
     */
    public void setSubComponente(SubComponente subComponente) {
        this.subComponente = subComponente;
    }

    /**
     * @return the proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the intervSanitariaProductos
     */
    public List<IntervSanitariaProducto> getIntervSanitariaProductos() {
        return intervSanitariaProductos;
    }

    /**
     * @param intervSanitariaProductos the intervSanitariaProductos to set
     */
    public void setIntervSanitariaProductos(List<IntervSanitariaProducto> intervSanitariaProductos) {
        this.intervSanitariaProductos = intervSanitariaProductos;
    }

    /**
     * @return the datosCoor
     */
    public HashMap<String, String> getDatosCoor() {
        return datosCoor;
    }

    /**
     * @param datosCoor the datosCoor to set
     */
    @JsonIgnore
    public void setDatosCoor(HashMap<String, String> datosCoor) {
        this.datosCoor = datosCoor;
    }
    public Personal getCoorPersonal() {
        return this.coorPersonal;
    }
    
    public void setCoorPersonal(Personal coor) {
        this.coorPersonal = coor;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroSalida != null ? nroSalida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the nroSalida fields are not set
        if (!(object instanceof IntervSanitaria)) {
            return false;
        }
        IntervSanitaria other = (IntervSanitaria) object;
        return !((this.nroSalida == null && other.nroSalida != null) || (this.nroSalida != null && !this.nroSalida.equals(other.nroSalida)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.InervSanitaria[ id=" + nroSalida + " ]";
    }

    @PrePersist
    @PreUpdate
    public void beforeCreate() {
        if (this.isAnulada() == null) {
            this.setAnulada(false);
        }
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

}
