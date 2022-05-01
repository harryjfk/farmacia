/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Tipo_Proceso")
@NamedQueries({
    @NamedQuery(name = "GpTipoProceso.findAll", query = "SELECT g FROM GpTipoProceso g")})
public class GpTipoProceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdTipoProceso")
    private Integer idTipoProceso;
    @Basic(optional = false)
    @Column(name = "NombreTipoProceso")
    private String nombreTipoProceso;
    @Basic(optional = false)
    @Column(name = "Activo")
    private int activo;
    @Basic(optional = false)
    @Column(name = "UsuarioCreacion")
    private int usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "FechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "UsuarioModificacion")
    private Integer usuarioModificacion;
    @Column(name = "FechaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @JsonIgnore
    @OneToMany(mappedBy = "idTipoProceso")
    private List<GpMovimiento> gpMovimientoList;

    public GpTipoProceso() {
    }

    public GpTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public GpTipoProceso(Integer idTipoProceso, String nombreTipoProceso, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idTipoProceso = idTipoProceso;
        this.nombreTipoProceso = nombreTipoProceso;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public String getNombreTipoProceso() {
        return nombreTipoProceso;
    }

    public void setNombreTipoProceso(String nombreTipoProceso) {
        this.nombreTipoProceso = nombreTipoProceso;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<GpMovimiento> getGpMovimientoList() {
        return gpMovimientoList;
    }

    public void setGpMovimientoList(List<GpMovimiento> gpMovimientoList) {
        this.gpMovimientoList = gpMovimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoProceso != null ? idTipoProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpTipoProceso)) {
            return false;
        }
        GpTipoProceso other = (GpTipoProceso) object;
        if ((this.idTipoProceso == null && other.idTipoProceso != null) || (this.idTipoProceso != null && !this.idTipoProceso.equals(other.idTipoProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoProceso[ idTipoProceso=" + idTipoProceso + " ]";
    }
    
}
