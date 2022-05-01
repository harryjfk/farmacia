package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class IdiDetalleDto {

    private int idIdiDetalle;
    private int idIdi;
    private int idProducto;
    private String tipoProducto;
    private String descripcionProducto;
    private String formaFarmaceutica;
    private BigDecimal precioOperacion;
    private BigDecimal saldoAnterior;
    private BigDecimal ingresos;
    private BigDecimal reIngresos;
    private BigDecimal distribucion;
    private BigDecimal transferencia;
    private BigDecimal vencido;
    private BigDecimal merma;
    private BigDecimal venta;
    private BigDecimal exoneracion;
    private BigDecimal saldoFinal;
    private Timestamp vencimiento;
    private int activo;

    
    public double getTotalSalidas(){
        BigDecimal totalSalidas = distribucion.add(transferencia).add(vencido).add(merma).add(venta).add(exoneracion);
        saldoFinal = saldoAnterior.add(ingresos).add(reIngresos).subtract(totalSalidas);
        return totalSalidas.doubleValue();
    }
    public int getIdIdiDetalle() {
        return idIdiDetalle;
    }

    public void setIdIdiDetalle(int idIdiDetalle) {
        this.idIdiDetalle = idIdiDetalle;
    }

    public int getIdIdi() {
        return idIdi;
    }

    public void setIdIdi(int idIdi) {
        this.idIdi = idIdi;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }    

    public BigDecimal getPrecioOperacion() {
        return precioOperacion;
    }

    public void setPrecioOperacion(BigDecimal precioOperacion) {
        this.precioOperacion = precioOperacion;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public BigDecimal getReIngresos() {
        return reIngresos;
    }

    public void setReIngresos(BigDecimal reIngresos) {
        this.reIngresos = reIngresos;
    }

    public BigDecimal getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(BigDecimal distribucion) {
        this.distribucion = distribucion;
    }

    public BigDecimal getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(BigDecimal transferencia) {
        this.transferencia = transferencia;
    }

    public BigDecimal getVencido() {
        return vencido;
    }

    public void setVencido(BigDecimal vencido) {
        this.vencido = vencido;
    }


    public BigDecimal getMerma() {
        return merma;
    }

    public void setMerma(BigDecimal merma) {
        this.merma = merma;
    }

    public BigDecimal getVenta() {
        return venta;
    }

    public void setVenta(BigDecimal venta) {
        this.venta = venta;
    }

    public BigDecimal getExoneracion() {
        return exoneracion;
    }

    public void setExoneracion(BigDecimal exoneracion) {
        this.exoneracion = exoneracion;
    }

    public Timestamp getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Timestamp vencimiento) {
        this.vencimiento = vencimiento;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
    

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

}
