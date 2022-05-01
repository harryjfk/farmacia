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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Far_Almacen")
@NamedQueries({
    @NamedQuery(name = "GpAlmacen.findAll", query = "SELECT g FROM GpAlmacen g")})
public class GpAlmacen extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "Farmacia")
    private int farmacia;
    @Basic(optional = false)
    @Column(name = "Abreviatura")
    private String abreviatura;
    @JsonIgnore
    @OneToMany(mappedBy = "idAlmacenDestino")
    private List<GpMovimiento> gpMovimientoList;
    @JsonIgnore
    @OneToMany(mappedBy = "idAlmacenOrigen")
    private List<GpMovimiento> gpMovimientoList1;
    @JsonIgnore
    @OneToMany(mappedBy = "idAlmacenPadre")
    private List<GpAlmacen> gpAlmacenList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdAlmacen")
    private Integer idAlmacen;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Fax")
    private String fax;
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "Ruc")
    private String ruc;
    @Column(name = "Responsable")
    private String responsable;
    @JsonIgnore
    @JoinColumn(name = "IdUbigeo", referencedColumnName = "IdUbigeo")
    @ManyToOne(optional = false)
    private GpUbigeo idUbigeo;
    @JsonIgnore
    @JoinColumn(name = "IdTipoAlmacen", referencedColumnName = "IdTipoAlmacen")
    @ManyToOne(optional = false)
    private GpTipoAlmacen idTipoAlmacen;
    
    @JoinColumn(name = "IdAlmacenPadre", referencedColumnName = "IdAlmacen")
    @ManyToOne
    private GpAlmacen idAlmacenPadre;

    public GpAlmacen() {
    }

    public GpAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public GpAlmacen(Integer idAlmacen, String descripcion) {
        this.idAlmacen = idAlmacen;
        this.descripcion = descripcion;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public GpUbigeo getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(GpUbigeo idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public GpTipoAlmacen getIdTipoAlmacen() {
        return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(GpTipoAlmacen idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }
    
    public GpAlmacen getIdAlmacenPadre() {
        return idAlmacenPadre;
    }

    public void setIdAlmacenPadre(GpAlmacen idAlmacenPadre) {
        this.idAlmacenPadre = idAlmacenPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlmacen != null ? idAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpAlmacen)) {
            return false;
        }
        GpAlmacen other = (GpAlmacen) object;
        return !((this.idAlmacen == null && other.idAlmacen != null) || (this.idAlmacen != null && !this.idAlmacen.equals(other.idAlmacen)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.newpackage.GpAlmacen[ idAlmacen=" + idAlmacen + " ]";
    }

    public List<GpMovimiento> getGpMovimientoList() {
        return gpMovimientoList;
    }

    public void setGpMovimientoList(List<GpMovimiento> gpMovimientoList) {
        this.gpMovimientoList = gpMovimientoList;
    }

    public List<GpMovimiento> getGpMovimientoList1() {
        return gpMovimientoList1;
    }

    public void setGpMovimientoList1(List<GpMovimiento> gpMovimientoList1) {
        this.gpMovimientoList1 = gpMovimientoList1;
    }

    public List<GpAlmacen> getGpAlmacenList() {
        return gpAlmacenList;
    }

    public void setGpAlmacenList(List<GpAlmacen> gpAlmacenList) {
        this.gpAlmacenList = gpAlmacenList;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(int farmacia) {
        this.farmacia = farmacia;
    }
    
}
