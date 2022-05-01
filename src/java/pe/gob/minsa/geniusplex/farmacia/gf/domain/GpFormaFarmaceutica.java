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

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Forma_Farmaceutica")
@NamedQueries({
    @NamedQuery(name = "GpFormaFarmaceutica.findAll", query = "SELECT g FROM GpFormaFarmaceutica g")})
public class GpFormaFarmaceutica implements Serializable {
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFormaFarmaceutica")
    private List<GpProducto> gpProductoList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdFormaFarmaceutica")
    private Integer idFormaFarmaceutica;
    @Basic(optional = false)
    @Column(name = "NombreFormaFarmaceutica")
    private String nombreFormaFarmaceutica;
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

    public GpFormaFarmaceutica() {
    }

    public GpFormaFarmaceutica(Integer idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public GpFormaFarmaceutica(Integer idFormaFarmaceutica, String nombreFormaFarmaceutica, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
        this.nombreFormaFarmaceutica = nombreFormaFarmaceutica;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(Integer idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public String getNombreFormaFarmaceutica() {
        return nombreFormaFarmaceutica;
    }

    public void setNombreFormaFarmaceutica(String nombreFormaFarmaceutica) {
        this.nombreFormaFarmaceutica = nombreFormaFarmaceutica;
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
        hash += (idFormaFarmaceutica != null ? idFormaFarmaceutica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpFormaFarmaceutica)) {
            return false;
        }
        GpFormaFarmaceutica other = (GpFormaFarmaceutica) object;
        if ((this.idFormaFarmaceutica == null && other.idFormaFarmaceutica != null) || (this.idFormaFarmaceutica != null && !this.idFormaFarmaceutica.equals(other.idFormaFarmaceutica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.newpackage.GpFormaFarmaceutica[ idFormaFarmaceutica=" + idFormaFarmaceutica + " ]";
    }

    public List<GpProducto> getGpProductoList() {
        return gpProductoList;
    }

    public void setGpProductoList(List<GpProducto> gpProductoList) {
        this.gpProductoList = gpProductoList;
    }
    
}
