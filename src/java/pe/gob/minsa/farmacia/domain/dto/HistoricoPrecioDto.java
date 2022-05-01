package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class HistoricoPrecioDto {

    private Timestamp fechaRegistro;
    private String tipo;
    private BigDecimal precioAdquisicion;
    private BigDecimal precioDistribucion;
    private BigDecimal precioOperacion;  

    public HistoricoPrecioDto(Timestamp fechaRegistro, String tipo, BigDecimal precioAdquisicion, BigDecimal precioDistribucion, BigDecimal precioOperacion) {
        this.fechaRegistro = fechaRegistro;
        this.tipo = tipo;
        this.precioAdquisicion = precioAdquisicion;
        this.precioDistribucion = precioDistribucion;
        this.precioOperacion = precioOperacion;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecioAdquisicion() {
        return precioAdquisicion;
    }

    public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
        this.precioAdquisicion = precioAdquisicion;
    }

    public BigDecimal getPrecioDistribucion() {
        return precioDistribucion;
    }

    public void setPrecioDistribucion(BigDecimal precioDistribucion) {
        this.precioDistribucion = precioDistribucion;
    }

    public BigDecimal getPrecioOperacion() {
        return precioOperacion;
    }

    public void setPrecioOperacion(BigDecimal precioOperacion) {
        this.precioOperacion = precioOperacion;
    }
}
