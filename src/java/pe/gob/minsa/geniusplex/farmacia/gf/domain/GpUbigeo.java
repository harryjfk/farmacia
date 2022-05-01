/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Ubigeo")
@NamedQueries({
    @NamedQuery(name = "GpUbigeo.findAll", query = "SELECT g FROM GpUbigeo g")})
public class GpUbigeo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdUbigeo")
    private String idUbigeo;
    @Basic(optional = false)
    @Column(name = "NombreUbigeo")
    private String nombreUbigeo;
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

    public GpUbigeo() {
    }

    public GpUbigeo(String idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public GpUbigeo(String idUbigeo, String nombreUbigeo, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idUbigeo = idUbigeo;
        this.nombreUbigeo = nombreUbigeo;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(String idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public String getNombreUbigeo() {
        return nombreUbigeo;
    }

    public void setNombreUbigeo(String nombreUbigeo) {
        this.nombreUbigeo = nombreUbigeo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUbigeo != null ? idUbigeo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpUbigeo)) {
            return false;
        }
        GpUbigeo other = (GpUbigeo) object;
        return !((this.idUbigeo == null && other.idUbigeo != null) || (this.idUbigeo != null && !this.idUbigeo.equals(other.idUbigeo)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.newpackage.GpUbigeo[ idUbigeo=" + idUbigeo + " ]";
    }
    
}
