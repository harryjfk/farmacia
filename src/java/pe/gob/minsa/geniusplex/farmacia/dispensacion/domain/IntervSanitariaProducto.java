/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */
@Entity
@IdClass(IntervSanitariaPk.class)
@Table(name = "Far_Intervencion_Sanitaria_Producto")
public class IntervSanitariaProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IdIntervSanitaria", insertable = false, updatable = false)
    @JsonIgnore
    private String idIntervSanitaria;
    
    @Id
    @Column(name = "IdProducto", insertable = false, updatable = false)
    @JsonIgnore
    private Integer idProducto;
    
    @Column(name = "Cantidad")
    private Double cantidad;
    
    @Column(name = "Precio")
    private BigDecimal precio;
    
    @JoinColumn(name = "IdProducto")
    @ManyToOne
    private GpProducto producto;
    
    @JsonIgnore
    @JoinColumn(name = "IdIntervSanitaria")
    @ManyToOne
    private IntervSanitaria interSanitaria;
    
    @Transient
    private int stock;
    

    public String getIdIntervSanitaria() {
        return idIntervSanitaria;
    }

    public void setIdIntervSanitaria(String idIntervSanitaria) {
        this.idIntervSanitaria = idIntervSanitaria;
    }

    /**
     * @return the idProducto
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the cantidad
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public BigDecimal getImporteParcial() {
        return this.getPrecio()
                .multiply(new BigDecimal(this.getCantidad().toString()))
                .setScale(2, RoundingMode.UP);
    }
    
    public void setImporteParcial(String importeParcial) {
        
    }


    /**
     * @return the producto
     */
    public GpProducto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }

    /**
     * @return the interSanitaria
     */
    public IntervSanitaria getInterSanitaria() {
        return interSanitaria;
    }

    /**
     * @param interSanitaria the interSanitaria to set
     */
    public void setInterSanitaria(IntervSanitaria interSanitaria) {
        this.interSanitaria = interSanitaria;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getIdIntervSanitaria() != null ? getIdIntervSanitaria().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the idIntervSanitaria fields are not set
        if (!(object instanceof IntervSanitariaProducto)) {
            return false;
        }
        IntervSanitariaProducto other = (IntervSanitariaProducto) object;
        return !((this.getIdIntervSanitaria() == null && other.getIdIntervSanitaria() != null) || (this.getIdIntervSanitaria() != null && !this.idIntervSanitaria.equals(other.idIntervSanitaria)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitarioProducto[ id=" + getIdIntervSanitaria() + " ]";
    }
    
}
