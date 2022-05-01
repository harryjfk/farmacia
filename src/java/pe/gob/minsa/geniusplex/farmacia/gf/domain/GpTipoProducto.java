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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Tipo_Producto")
@NamedQueries({
    @NamedQuery(name = "GpTipoProducto.findAll", query = "SELECT g FROM GpTipoProducto g")})
public class GpTipoProducto extends BaseEntity implements Serializable {
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoProducto")
    private List<GpProducto> gpProductoList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdTipoProducto")
    private Integer idTipoProducto;
    @Basic(optional = false)
    @Column(name = "NombreTipoProducto")
    private String nombreTipoProducto;
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
    

    public GpTipoProducto() {
    }

    public GpTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public GpTipoProducto(Integer idTipoProducto, String nombreTipoProducto, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idTipoProducto = idTipoProducto;
        this.nombreTipoProducto = nombreTipoProducto;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
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
        hash += (idTipoProducto != null ? idTipoProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpTipoProducto)) {
            return false;
        }
        GpTipoProducto other = (GpTipoProducto) object;
        if ((this.idTipoProducto == null && other.idTipoProducto != null) || (this.idTipoProducto != null && !this.idTipoProducto.equals(other.idTipoProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.newpackage.GpTipoProducto[ idTipoProducto=" + idTipoProducto + " ]";
    }

    public List<GpProducto> getGpProductoList() {
        return gpProductoList;
    }

    public void setGpProductoList(List<GpProducto> gpProductoList) {
        this.gpProductoList = gpProductoList;
    }
    
}
