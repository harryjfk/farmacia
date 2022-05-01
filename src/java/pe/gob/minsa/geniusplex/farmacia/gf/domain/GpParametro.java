/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Parametro")
@NamedQueries({
    @NamedQuery(name = "GpParametro.findAll", query = "SELECT g FROM GpParametro g"),
    @NamedQuery(name = "GpParametro.findByNombre", query = "SELECT g FROM GpParametro g WHERE g.nombreParametro = :nombre")})
public class GpParametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdParametro")
    private Integer idParametro;
    @Basic(optional = false)
    @Column(name = "NombreParametro")
    private String nombreParametro;
    @Basic(optional = false)
    @Column(name = "DescripcionParametro")
    private String descripcionParametro;
    @Basic(optional = false)
    @Column(name = "Valor")
    private String valor;

    public GpParametro() {
    }

    public GpParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public GpParametro(Integer idParametro, String nombreParametro, String descripcionParametro, String valor) {
        this.idParametro = idParametro;
        this.nombreParametro = nombreParametro;
        this.descripcionParametro = descripcionParametro;
        this.valor = valor;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public String getDescripcionParametro() {
        return descripcionParametro;
    }

    public void setDescripcionParametro(String descripcionParametro) {
        this.descripcionParametro = descripcionParametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpParametro)) {
            return false;
        }
        GpParametro other = (GpParametro) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpParametro[ idParametro=" + idParametro + " ]";
    }

}
