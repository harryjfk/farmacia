package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;

public class PrecioUltimoDto {
    
    private int idProducto;
    private String nombreProducto;
    private String codigoSismed;
    private BigDecimal precioAdquisicion;
    private BigDecimal precioDistribucion;
    private BigDecimal precioOperacion;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCodigoSismed() {
        return codigoSismed;
    }

    public void setCodigoSismed(String codigoSismed) {
        this.codigoSismed = codigoSismed;
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