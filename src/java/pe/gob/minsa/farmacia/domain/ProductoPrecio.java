package pe.gob.minsa.farmacia.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductoPrecio extends BaseDomain {
    
    private int idProductoPrecio;
    private String tipoPrecio;
    private BigDecimal precioAdquisicion; 
    private BigDecimal precioDistribucion;
    private BigDecimal precioOperacion;
    private int idProducto;
    private Timestamp fechaRegistro;
    private Timestamp fechaVigencia;

    public int getIdProductoPrecio() {
        return idProductoPrecio;
    }

    public void setIdProductoPrecio(int idProductoPrecio) {
        this.idProductoPrecio = idProductoPrecio;
    }

    public String getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(String tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
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

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Timestamp getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Timestamp fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
}
