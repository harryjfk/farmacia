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
@Table(name = "Far_Tipo_Almacen")
@NamedQueries({
    @NamedQuery(name = "GpTipoAlmacen.findAll", query = "SELECT g FROM GpTipoAlmacen g")})
public class GpTipoAlmacen implements Serializable {
   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdTipoAlmacen")
    private Integer idTipoAlmacen;
    @Basic(optional = false)
    @Column(name = "NombreTipoAlmacen")
    private String nombreTipoAlmacen;
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

    public GpTipoAlmacen() {
    }

    public GpTipoAlmacen(Integer idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }

    public GpTipoAlmacen(Integer idTipoAlmacen, String nombreTipoAlmacen, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idTipoAlmacen = idTipoAlmacen;
        this.nombreTipoAlmacen = nombreTipoAlmacen;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdTipoAlmacen() {
        return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(Integer idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }

    public String getNombreTipoAlmacen() {
        return nombreTipoAlmacen;
    }

    public void setNombreTipoAlmacen(String nombreTipoAlmacen) {
        this.nombreTipoAlmacen = nombreTipoAlmacen;
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
        hash += (idTipoAlmacen != null ? idTipoAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpTipoAlmacen)) {
            return false;
        }
        GpTipoAlmacen other = (GpTipoAlmacen) object;
        return !((this.idTipoAlmacen == null && other.idTipoAlmacen != null) || (this.idTipoAlmacen != null && !this.idTipoAlmacen.equals(other.idTipoAlmacen)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.newpackage.GpTipoAlmacen[ idTipoAlmacen=" + idTipoAlmacen + " ]";
    }
    
}
