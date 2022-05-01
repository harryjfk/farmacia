package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Demanda_Insatisecha")
public class DemandaInsatisfecha extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @Transient
    private ProductoLote producto;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "Almacen")
    private Long idAlmacen;
    @Column(name = "Lote")
    private String lote;
    @Column(name = "Producto")
    private Integer idProducto;

    @Column(name = "IdModulo")
    private long IdModulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ProductoLote getProducto() {
        return producto;
    }

    public void setProducto(ProductoLote producto) {
        setIdAlmacen((Long) producto.getIdAlmacen());
        setLote(producto.getLote().getDescripcion());
        setIdProducto(producto.getProducto().getIdProducto());
        this.producto = producto;

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long idModulo) {
        this.IdModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DemandaInsatisfecha)) {
            return false;
        }
        DemandaInsatisfecha other = (DemandaInsatisfecha) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DemandaInsatisfecha[ id=" + id + " ]";
    }

    @Override
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setFecha(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
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

    public BigDecimal getSaldo() {
        if (getProducto() != null) {
            return getProducto().getPrecio().multiply(new BigDecimal(String.valueOf(getProducto().getCantidad())));
        } 
        return BigDecimal.ZERO;
    }
}
