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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author stark
 */
@Entity
@Table(name = "Far_Movimiento")
@NamedQueries({
    @NamedQuery(name = "GpMovimiento.findAll", query = "SELECT g FROM GpMovimiento g")})
public class GpMovimiento extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "NumeroMovimiento")
    private int numeroMovimiento;
    @Column(name = "NumeroDocumentoOrigen")
    private String numeroDocumentoOrigen;
    @Column(name = "FechaDocumentoOrigen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumentoOrigen;
    @Column(name = "NumeroProceso")
    private String numeroProceso;
    @JoinColumn(name = "IdTipoProceso", referencedColumnName = "IdTipoProceso")
    @ManyToOne
    private GpTipoProceso idTipoProceso;
    @JoinColumn(name = "IdTipoDocumentoMov", referencedColumnName = "IdTipoDocumentoMov")
    @ManyToOne
    private GpTipoDocumentoMov idTipoDocumentoMov;
    @JoinColumn(name = "IdTipoCompra", referencedColumnName = "IdTipoCompra")
    @ManyToOne
    private GpTipoCompra idTipoCompra;
    @JoinColumn(name = "IdProveedor", referencedColumnName = "IdProveedor")
    @ManyToOne
    private GpProveedor idProveedor;
    
    @JoinColumn(name = "IdPeriodo", referencedColumnName = "IdPeriodo")
    @ManyToOne(optional = false)
    private GpPeriodo idPeriodo;
    @JoinColumn(name = "IdDocumentoOrigen", referencedColumnName = "IdDocumentoOrigen")
    @ManyToOne
    private GpDocumentoOrigen idDocumentoOrigen;
    
    @JoinColumn(name = "IdAlmacenDestino", referencedColumnName = "IdAlmacen")
    @ManyToOne
    private GpAlmacen idAlmacenDestino;

    @JoinColumn(name = "IdAlmacenOrigen", referencedColumnName = "IdAlmacen")
    @ManyToOne
    private GpAlmacen idAlmacenOrigen;
    
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
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdMovimiento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovimiento;
    @Basic(optional = false)
    @Column(name = "TipoMovimiento")
    private Character tipoMovimiento;
    @Column(name = "FechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "NumeroDocumentoMov")
    private String numeroDocumentoMov;
    @Column(name = "FechaRecepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;
    @Column(name = "Referencia")
    private String referencia;
    @JsonIgnore
    @OneToMany(mappedBy = "idMovimiento", cascade = CascadeType.ALL)
    private List<GpMovimientoProducto> gpMovimientoProductoList;
    @JsonIgnore
    @OneToMany(mappedBy = "idMovimientoIngreso")
    private List<GpMovimiento> gpMovimientoList;
    @JoinColumn(name = "IdMovimientoIngreso", referencedColumnName = "IdMovimiento")
    @JsonIgnore
    @ManyToOne
    private GpMovimiento idMovimientoIngreso;
    
    @JoinColumn(name = "IdConcepto")
    @ManyToOne
    private GpConcepto concepto;

    public GpMovimiento() {
    }

    public GpMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public GpMovimiento(Integer idMovimiento, Character tipoMovimiento) {
        this.idMovimiento = idMovimiento;
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Character getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Character tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }


    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNumeroDocumentoMov() {
        return numeroDocumentoMov;
    }

    public void setNumeroDocumentoMov(String numeroDocumentoMov) {
        this.numeroDocumentoMov = numeroDocumentoMov;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

//    public String getNumeroCompra() {
//        return numeroCompra;
//    }
//
//    public void setNumeroCompra(String numeroCompra) {
//        this.numeroCompra = numeroCompra;
//    }
//
//    public Date getFechaCompra() {
//        return fechaCompra;
//    }
//
//    public void setFechaCompra(Date fechaCompra) {
//        this.fechaCompra = fechaCompra;
//    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    

    

   

    public List<GpMovimientoProducto> getGpMovimientoProductoList() {
        return gpMovimientoProductoList;
    }

    public void setGpMovimientoProductoList(List<GpMovimientoProducto> gpMovimientoProductoList) {
        this.gpMovimientoProductoList = gpMovimientoProductoList;
    }

    public List<GpMovimiento> getGpMovimientoList() {
        return gpMovimientoList;
    }

    public void setGpMovimientoList(List<GpMovimiento> gpMovimientoList) {
        this.gpMovimientoList = gpMovimientoList;
    }

    public GpMovimiento getIdMovimientoIngreso() {
        return idMovimientoIngreso;
    }

    public void setIdMovimientoIngreso(GpMovimiento idMovimientoIngreso) {
        this.idMovimientoIngreso = idMovimientoIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimiento != null ? idMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpMovimiento)) {
            return false;
        }
        GpMovimiento other = (GpMovimiento) object;
        if ((this.idMovimiento == null && other.idMovimiento != null) || (this.idMovimiento != null && !this.idMovimiento.equals(other.idMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento[ idMovimiento=" + idMovimiento + " ]";
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

    public GpAlmacen getIdAlmacenDestino() {
        return idAlmacenDestino;
    }

    public void setIdAlmacenDestino(GpAlmacen idAlmacenDestino) {
        this.idAlmacenDestino = idAlmacenDestino;
    }

    public GpAlmacen getIdAlmacenOrigen() {
        return idAlmacenOrigen;
    }

    public void setIdAlmacenOrigen(GpAlmacen idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    /**
     * @return the concepto
     */
    public GpConcepto getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(GpConcepto concepto) {
        this.concepto = concepto;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public String getNumeroDocumentoOrigen() {
        return numeroDocumentoOrigen;
    }

    public void setNumeroDocumentoOrigen(String numeroDocumentoOrigen) {
        this.numeroDocumentoOrigen = numeroDocumentoOrigen;
    }

    public Date getFechaDocumentoOrigen() {
        return fechaDocumentoOrigen;
    }

    public void setFechaDocumentoOrigen(Date fechaDocumentoOrigen) {
        this.fechaDocumentoOrigen = fechaDocumentoOrigen;
    }

    public String getNumeroProceso() {
        return numeroProceso;
    }

    public void setNumeroProceso(String numeroProceso) {
        this.numeroProceso = numeroProceso;
    }

    public GpTipoProceso getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(GpTipoProceso idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public GpTipoDocumentoMov getIdTipoDocumentoMov() {
        return idTipoDocumentoMov;
    }

    public void setIdTipoDocumentoMov(GpTipoDocumentoMov idTipoDocumentoMov) {
        this.idTipoDocumentoMov = idTipoDocumentoMov;
    }

    public GpTipoCompra getIdTipoCompra() {
        return idTipoCompra;
    }

    public void setIdTipoCompra(GpTipoCompra idTipoCompra) {
        this.idTipoCompra = idTipoCompra;
    }

    public GpProveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(GpProveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public GpPeriodo getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(GpPeriodo idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public GpDocumentoOrigen getIdDocumentoOrigen() {
        return idDocumentoOrigen;
    }

    public void setIdDocumentoOrigen(GpDocumentoOrigen idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
    }
    
}
