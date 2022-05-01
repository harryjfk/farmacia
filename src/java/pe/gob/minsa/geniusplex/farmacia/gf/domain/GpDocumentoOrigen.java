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
@Table(name = "Far_Documento_Origen")
@NamedQueries({
    @NamedQuery(name = "GpDocumentoOrigen.findAll", query = "SELECT g FROM GpDocumentoOrigen g")})
public class GpDocumentoOrigen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdDocumentoOrigen")
    private Integer idDocumentoOrigen;
    @Basic(optional = false)
    @Column(name = "NombreDocumentoOrigen")
    private String nombreDocumentoOrigen;
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
    @OneToMany(mappedBy = "idDocumentoOrigen")
    private List<GpMovimiento> gpMovimientoList;

    public GpDocumentoOrigen() {
    }

    public GpDocumentoOrigen(Integer idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
    }

    public GpDocumentoOrigen(Integer idDocumentoOrigen, String nombreDocumentoOrigen, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idDocumentoOrigen = idDocumentoOrigen;
        this.nombreDocumentoOrigen = nombreDocumentoOrigen;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdDocumentoOrigen() {
        return idDocumentoOrigen;
    }

    public void setIdDocumentoOrigen(Integer idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
    }

    public String getNombreDocumentoOrigen() {
        return nombreDocumentoOrigen;
    }

    public void setNombreDocumentoOrigen(String nombreDocumentoOrigen) {
        this.nombreDocumentoOrigen = nombreDocumentoOrigen;
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
        hash += (idDocumentoOrigen != null ? idDocumentoOrigen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpDocumentoOrigen)) {
            return false;
        }
        GpDocumentoOrigen other = (GpDocumentoOrigen) object;
        if ((this.idDocumentoOrigen == null && other.idDocumentoOrigen != null) || (this.idDocumentoOrigen != null && !this.idDocumentoOrigen.equals(other.idDocumentoOrigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpDocumentoOrigen[ idDocumentoOrigen=" + idDocumentoOrigen + " ]";
    }
    
}
