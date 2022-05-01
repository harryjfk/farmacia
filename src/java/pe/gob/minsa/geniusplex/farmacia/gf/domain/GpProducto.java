/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Producto")
@NamedQueries({
    @NamedQuery(name = "GpProducto.findAll", query = "SELECT g FROM GpProducto g")})
public class GpProducto extends BaseEntity implements Serializable {
    
    @JsonIgnore
    @OneToMany(mappedBy = "idProducto")
    private List<GpMovimientoProducto> gpMovimientoProductoList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdProducto")
    private Integer idProducto;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Abreviatura")
    private String abreviatura;
    @Column(name = "Presentacion")
    private String presentacion;
    @Column(name = "Concentracion")
    private String concentracion;
    @Column(name = "Petitorio")
    private Integer petitorio;
    @Column(name = "EstrSop")
    private Integer estrSop;
    @Column(name = "EstrVta")
    private Integer estrVta;
    @Column(name = "TraNac")
    private Integer traNac;
    @Column(name = "TraLoc")
    private Integer traLoc;
    @Column(name = "Narcotico")
    private Integer narcotico;
    @Column(name = "StockMin")
    private Long stockMin;
    @Column(name = "StockMax")
    private Long stockMax;
    @Column(name = "Requerimiento")
    private Long requerimiento;
    @Column(name = "Adscrito")
    private Integer adscrito;
    @JoinColumn(name = "IdUnidadMedida", referencedColumnName = "IdUnidadMedida")
    @ManyToOne(optional = false)
    private GpUnidadMedida idUnidadMedida;
    @JoinColumn(name = "IdTipoProducto", referencedColumnName = "IdTipoProducto")
    @ManyToOne(optional = false)
    private GpTipoProducto idTipoProducto;
    @JoinColumn(name = "IdProductoSismed", referencedColumnName = "IdProductoSismed")
    @ManyToOne(optional = false)
    private GpProductoSISMED idProductoSismed;
    @JoinColumn(name = "IdProductoSiga", referencedColumnName = "IdProductoSiga")
    @ManyToOne(optional = false)
    private GpProductoSIGA idProductoSiga;
    @JoinColumn(name = "IdFormaFarmaceutica", referencedColumnName = "IdFormaFarmaceutica")
    @ManyToOne(optional = false)
    private GpFormaFarmaceutica idFormaFarmaceutica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    @JsonIgnore
    private List<GpProductoPrecio> precios;

    public GpProducto() {
    }

    public GpProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public GpProducto(Integer idProducto, String descripcion) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        String ff = this.getIdFormaFarmaceutica() != null? this.getIdFormaFarmaceutica().getNombreFormaFarmaceutica(): "";
        String um = this.getIdUnidadMedida() != null? this.getIdUnidadMedida().getNombreUnidadMedida(): "";
       return String.format("%s - %s - %s - %s",
                this.descripcion,
                this.getConcentracion(), ff, um);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public Integer getPetitorio() {
        return petitorio;
    }

    public void setPetitorio(Integer petitorio) {
        this.petitorio = petitorio;
    }

    public Integer getEstrSop() {
        return estrSop;
    }

    public void setEstrSop(Integer estrSop) {
        this.estrSop = estrSop;
    }

    public Integer getEstrVta() {
        return estrVta;
    }

    public void setEstrVta(Integer estrVta) {
        this.estrVta = estrVta;
    }

    public Integer getTraNac() {
        return traNac;
    }

    public void setTraNac(Integer traNac) {
        this.traNac = traNac;
    }

    public Integer getTraLoc() {
        return traLoc;
    }

    public void setTraLoc(Integer traLoc) {
        this.traLoc = traLoc;
    }

    public Integer getNarcotico() {
        return narcotico;
    }

    public void setNarcotico(Integer narcotico) {
        this.narcotico = narcotico;
    }

    public Long getStockMin() {
        return stockMin;
    }

    public void setStockMin(Long stockMin) {
        this.stockMin = stockMin;
    }

    public Long getStockMax() {
        return stockMax;
    }

    public void setStockMax(Long stockMax) {
        this.stockMax = stockMax;
    }

    public Long getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Long requerimiento) {
        this.requerimiento = requerimiento;
    }

    public Integer getAdscrito() {
        return adscrito;
    }

    public void setAdscrito(Integer adscrito) {
        this.adscrito = adscrito;
    }

    public GpUnidadMedida getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(GpUnidadMedida idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public GpTipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(GpTipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public GpProductoSISMED getIdProductoSismed() {
        return idProductoSismed;
    }

    public void setIdProductoSismed(GpProductoSISMED idProductoSismed) {
        this.idProductoSismed = idProductoSismed;
    }

    public GpProductoSIGA getIdProductoSiga() {
        return idProductoSiga;
    }

    public void setIdProductoSiga(GpProductoSIGA idProductoSiga) {
        this.idProductoSiga = idProductoSiga;
    }

    public GpFormaFarmaceutica getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(GpFormaFarmaceutica idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpProducto)) {
            return false;
        }
        GpProducto other = (GpProducto) object;
        return !((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto[ idProducto=" + idProducto + " ]";
    }

    @Override
    public void prePersist() {

    }

    public List<GpMovimientoProducto> getGpMovimientoProductoList() {
        return gpMovimientoProductoList;
    }

    public void setGpMovimientoProductoList(List<GpMovimientoProducto> gpMovimientoProductoList) {
        this.gpMovimientoProductoList = gpMovimientoProductoList;
    }

    public List<GpProductoPrecio> getPrecios() {
        return precios;
    }

    public void setPrecios(List<GpProductoPrecio> precios) {
        this.precios = precios;
    }
    
    public String getSingleDescripcion() {
        return descripcion;
    }
    
    public void setSingleDescripcion(String desc) {
        descripcion = desc;
    }
    

}
