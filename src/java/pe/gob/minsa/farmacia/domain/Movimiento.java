package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import pe.gob.minsa.farmacia.domain.lazyload.IAlmacen;
import pe.gob.minsa.farmacia.domain.lazyload.IConcepto;
import pe.gob.minsa.farmacia.domain.lazyload.IDocumentoOrigen;
import pe.gob.minsa.farmacia.domain.lazyload.ITipoCompra;
import pe.gob.minsa.farmacia.domain.lazyload.ITipoDocumentoMov;
import pe.gob.minsa.farmacia.domain.lazyload.ITipoProceso;

public class Movimiento extends BaseDomain implements Serializable {

    private int idMovimiento;
    private int idPeriodo;
    private TipoMovimiento tipoMovimiento;
    private int numeroMovimiento;
    private Timestamp fechaRegistro;
    private Integer idAlmacenOrigen;
    private Integer idAlmacenDestino;
    private int idConcepto;
    private Integer idTipoDocumentoMov;    
    private String numeroDocumentoMov;
    private Timestamp fechaRecepcion;
    private Integer idDocumentoOrigen;    
    private String numeroDocumentoOrigen;
    private Timestamp fechaDocumentoOrigen;
    private Integer idProveedor;    
    private Integer idMovimientoIngreso;
    private Integer idTipoCompra;
    private Integer idTipoProceso;    
    private String numeroProceso;
    private String referencia;
    private BigDecimal total;
    
    private IAlmacen almacenOrigen;
    private IAlmacen almacenDestino;
    private IConcepto concepto;
    private ITipoDocumentoMov tipoDocumentoMov;
    private IDocumentoOrigen documentoOrigen;
    private Proveedor proveedor;
    private ITipoCompra tipoCompra;
    private ITipoProceso tipoProceso;

    public Movimiento(){
        proveedor = new Proveedor();
    }
    
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdAlmacenOrigen() {
        return idAlmacenOrigen;
    }

    public void setIdAlmacenOrigen(Integer idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    public Integer getIdAlmacenDestino() {
        return idAlmacenDestino;
    }

    public void setIdAlmacenDestino(Integer idAlmacenDestino) {
        this.idAlmacenDestino = idAlmacenDestino;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdTipoDocumentoMov() {
        return idTipoDocumentoMov;
    }

    public void setIdTipoDocumentoMov(Integer idTipoDocumentoMov) {
        this.idTipoDocumentoMov = idTipoDocumentoMov;
    }

    public String getNumeroDocumentoMov() {
        return numeroDocumentoMov;
    }

    public void setNumeroDocumentoMov(String numeroDocumentoMov) {
        this.numeroDocumentoMov = numeroDocumentoMov;
    }

    public Timestamp getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Timestamp fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Integer getIdDocumentoOrigen() {
        return idDocumentoOrigen;
    }

    public void setIdDocumentoOrigen(Integer idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
    }

    public String getNumeroDocumentoOrigen() {
        return numeroDocumentoOrigen;
    }

    public void setNumeroDocumentoOrigen(String numeroDocumentoOrigen) {
        this.numeroDocumentoOrigen = numeroDocumentoOrigen;
    }

    public Timestamp getFechaDocumentoOrigen() {
        return fechaDocumentoOrigen;
    }

    public void setFechaDocumentoOrigen(Timestamp fechaDocumentoOrigen) {
        this.fechaDocumentoOrigen = fechaDocumentoOrigen;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getIdMovimientoIngreso() {
        return idMovimientoIngreso;
    }

    public void setIdMovimientoIngreso(Integer idMovimientoIngreso) {
        this.idMovimientoIngreso = idMovimientoIngreso;
    }

    public Integer getIdTipoCompra() {
        return idTipoCompra;
    }

    public void setIdTipoCompra(Integer idTipoCompra) {
        this.idTipoCompra = idTipoCompra;
    }

    public Integer getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public String getNumeroProceso() {
        return numeroProceso;
    }

    public void setNumeroProceso(String numeroProceso) {
        this.numeroProceso = numeroProceso;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public IAlmacen getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(IAlmacen almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public IAlmacen getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(IAlmacen almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public IConcepto getConcepto() {
        return concepto;
    }

    public void setConcepto(IConcepto concepto) {
        this.concepto = concepto;
    }

    public ITipoDocumentoMov getTipoDocumentoMov() {
        return tipoDocumentoMov;
    }

    public void setTipoDocumentoMov(ITipoDocumentoMov tipoDocumentoMov) {
        this.tipoDocumentoMov = tipoDocumentoMov;
    }

    public IDocumentoOrigen getDocumentoOrigen() {
        return documentoOrigen;
    }

    public void setDocumentoOrigen(IDocumentoOrigen documentoOrigen) {
        this.documentoOrigen = documentoOrigen;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ITipoCompra getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(ITipoCompra tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public ITipoProceso getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(ITipoProceso tipoProceso) {
        this.tipoProceso = tipoProceso;
    }
}