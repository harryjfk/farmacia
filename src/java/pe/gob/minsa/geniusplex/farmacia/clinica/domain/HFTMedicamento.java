/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author stark
 */
@Entity
@Table(name="Far_HFTMedicamento")
public class HFTMedicamento extends BaseEntity implements Serializable {
    @JsonIgnore
    @ManyToMany(mappedBy = "medicamentos")
    private List<HFT> hfts;
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="Producto")
    private GpProducto producto;
    
    @Column(name="Dosis")
    private String dosis;
    
    @Column(name="Via")
    private String Via;
    @Column(name="PRM")
    private String PRM;
    @Column(name="Total")
    private String total;
    
    @Column(name="Frecuencia")
    private String frecuencia;
    
    @Column(name = "IdModulo")
    private Long IdModulo;
   
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.producto != null ? this.producto.hashCode() : 0);
        hash = 37 * hash + (this.IdModulo != null ? this.IdModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HFTMedicamento other = (HFTMedicamento) obj;
        if (this.producto != other.producto && (this.producto == null || !this.producto.equals(other.producto))) {
            return false;
        }
        if (this.IdModulo != other.IdModulo && (this.IdModulo == null || !this.IdModulo.equals(other.IdModulo))) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.clinica.domain.HFTMedicamento[ id=" + id + " ]";
    }

    public GpProducto getProducto() {
        return producto;
    }

    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getVia() {
        return Via;
    }

    public void setVia(String Via) {
        this.Via = Via;
    }

    public String getPRM() {
        return PRM;
    }

    public void setPRM(String PRM) {
        this.PRM = PRM;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(Long IdModulo) {
        this.IdModulo = IdModulo;
    }

    public List<HFT> getHfts() {
        return hfts;
    }

    public void setHfts(List<HFT> hfts) {
        this.hfts = hfts;
    }

   

    
}
