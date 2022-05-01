/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Inventario_Rutinario")
public class InventarioRutinario extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
       
    @Transient
    private ProductoLote productolote;
    
    @Column(name="Almacen")
    private Long idAlmacen;
    @Column(name="Lote")
    private String lote;
    @Column(name = "Producto")
    private Integer idProducto;
    @Column(name = "IdModulo")
    private long IdModulo;
    @Column(name="Stock")
    private long stock;
    @Column(name="StockCorrecto")
    private long stockReal;
    @Column(name="Precio")
    private BigDecimal precio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InventarioRutinario() {
    }

    public ProductoLote getProductolote() {
        return productolote;
    }
   
    public void setProductolote(ProductoLote productolote) {
        setIdAlmacen((Long) productolote.getIdAlmacen());
        setLote(productolote.getLote().getDescripcion());
        setIdProducto(productolote.getProducto().getIdProducto());
        stock = productolote.getCantidad();
        this.productolote = productolote;
        
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getStockReal() {
        return stockReal;
    }

    public void setStockReal(long stockReal) {
        this.stockReal = stockReal;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return the idAlmacen
     */
    public Long getIdAlmacen() {
        return idAlmacen;
    }

    /**
     * @param idAlmacen the idAlmacen to set
     */
    public void setIdAlmacen(Long idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 13 * hash + (this.productolote != null ? this.productolote.hashCode() : 0);
        hash = 13 * hash + (int) (this.IdModulo ^ (this.IdModulo >>> 32));
        hash = 13 * hash + (int) (this.stock ^ (this.stock >>> 32));
        hash = 13 * hash + (int) (this.stockReal ^ (this.stockReal >>> 32));
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
        final InventarioRutinario other = (InventarioRutinario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.productolote != other.productolote && (this.productolote == null || !this.productolote.equals(other.productolote))) {
            return false;
        }
        if (this.IdModulo != other.IdModulo) {
            return false;
        }
        if (this.stock != other.stock) {
            return false;
        }
        if (this.stockReal != other.stockReal) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    @Override
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }
    
}
