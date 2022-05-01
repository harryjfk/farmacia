package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import pe.gob.minsa.farmacia.util.UtilDto;

public class IciDetalleDto {

    private int idIciDetalle;
    private int idIci;
    private int idProducto;
    private String descripcionProducto;
    private String nombreUnidadMedida;
    private BigDecimal precioOperacion;
    private BigDecimal saldoAnterior;
    private BigDecimal ingresos;
    private BigDecimal ventas;
    private BigDecimal sis;
    private BigDecimal intervSanit;
    private BigDecimal factPerd;
    private BigDecimal defensaNacional;
    private BigDecimal exoneracion;
    private BigDecimal soat;
    private BigDecimal creditoHospitalario;
    private BigDecimal otrosConvenios;
    private BigDecimal devolucion;
    private BigDecimal vencido;
    private BigDecimal devolucionQuiebreStock;
    private BigDecimal merma;
    private BigDecimal saldoFinal;
    private Timestamp vencimiento;
    private BigDecimal requerimiento;
    private int activo;
    
    
    public double getTotalSalidas(){
        if(creditoHospitalario!=null)
        return creditoHospitalario.add(defensaNacional)
                                    .add(devolucion)
                                    .add(devolucionQuiebreStock)
                                    .add(exoneracion)
                                    .add(factPerd)
                                    .add(intervSanit)
                                    .add(merma)
                                    .add(otrosConvenios)
                                    .add(sis)
                                    .add(soat)
                                    .add(vencido)
                                    .add(ventas).doubleValue();
        else return 0;
												

    }

    public int getIdIciDetalle() {
        return idIciDetalle;
    }

    public void setIdIciDetalle(int idIciDetalle) {
        this.idIciDetalle = idIciDetalle;
    }

    public int getIdIci() {
        return idIci;
    }

    public void setIdIci(int idIci) {
        this.idIci = idIci;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public BigDecimal getPrecioOperacion() {
        return UtilDto.getBigDecimalFromNull(precioOperacion);
    }

    public void setPrecioOperacion(BigDecimal precioOperacion) {
        this.precioOperacion = precioOperacion;
    }

    public BigDecimal getSaldoAnterior() {
        return UtilDto.getBigDecimalFromNull(saldoAnterior);
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getIngresos() {
        return UtilDto.getBigDecimalFromNull(ingresos);
    }

    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public BigDecimal getVentas() {
        return UtilDto.getBigDecimalFromNull(ventas);
    }

    public void setVentas(BigDecimal ventas) {
        this.ventas = ventas;
    }

    public BigDecimal getSis() {
        return UtilDto.getBigDecimalFromNull(sis);
    }

    public void setSis(BigDecimal sis) {
        this.sis = sis;
    }

    public BigDecimal getIntervSanit() {
        return UtilDto.getBigDecimalFromNull(intervSanit);
    }

    public void setIntervSanit(BigDecimal intervSanit) {
        this.intervSanit = intervSanit;
    }

    public BigDecimal getFactPerd() {
        return UtilDto.getBigDecimalFromNull(factPerd);
    }

    public void setFactPerd(BigDecimal factPerd) {
        this.factPerd = factPerd;
    }

    public BigDecimal getDefensaNacional() {
        return UtilDto.getBigDecimalFromNull(defensaNacional);
    }

    public void setDefensaNacional(BigDecimal defensaNacional) {
        this.defensaNacional = defensaNacional;
    }

    public BigDecimal getExoneracion() {
        return UtilDto.getBigDecimalFromNull(exoneracion);
    }

    public void setExoneracion(BigDecimal exoneracion) {
        this.exoneracion = exoneracion;
    }

    public BigDecimal getSoat() {
        return UtilDto.getBigDecimalFromNull(soat);
    }

    public void setSoat(BigDecimal soat) {
        this.soat = soat;
    }

    public BigDecimal getCreditoHospitalario() {
        return UtilDto.getBigDecimalFromNull(creditoHospitalario);
    }

    public void setCreditoHospitalario(BigDecimal creditoHospitalario) {
        this.creditoHospitalario = creditoHospitalario;
    }

    public BigDecimal getOtrosConvenios() {
        return UtilDto.getBigDecimalFromNull(otrosConvenios);
    }

    public void setOtrosConvenios(BigDecimal otrosConvenios) {
        this.otrosConvenios = otrosConvenios;
    }

    public BigDecimal getDevolucion() {
        return UtilDto.getBigDecimalFromNull(devolucion);
    }

    public void setDevolucion(BigDecimal devolucion) {
        this.devolucion = devolucion;
    }

    public BigDecimal getVencido() {
        return UtilDto.getBigDecimalFromNull(vencido);
    }

    public void setVencido(BigDecimal vencido) {
        this.vencido = vencido;
    }

    public BigDecimal getDevolucionQuiebreStock() {
        return UtilDto.getBigDecimalFromNull(devolucionQuiebreStock);
    }

    public void setDevolucionQuiebreStock(BigDecimal devolucionQuiebreStock) {
        this.devolucionQuiebreStock = devolucionQuiebreStock;
    }

    public BigDecimal getMerma() {
        return UtilDto.getBigDecimalFromNull(merma);
    }

    public void setMerma(BigDecimal merma) {
        this.merma = merma;
    }

    public Timestamp getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Timestamp vencimiento) {
        this.vencimiento = vencimiento;
    }

    public BigDecimal getRequerimiento() {
        return UtilDto.getBigDecimalFromNull(requerimiento);
    }

    public void setRequerimiento(BigDecimal requerimiento) {
        this.requerimiento = requerimiento;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }   

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
    
    
}