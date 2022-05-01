package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class ProductoAlertaVencimientoDto {
    
    private String almacen;
    private String codigoSismed;
    private String producto;
    private String tipoProducto;
    private String lote;
    private Timestamp fechaVencimiento;
    private BigDecimal precio;
    private int stock;
    private String estado;

    public ProductoAlertaVencimientoDto(){
        precio = BigDecimal.ZERO;
    }
    
    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getCodigoSismed() {
        return codigoSismed;
    }

    public void setCodigoSismed(String codigoSismed) {
        this.codigoSismed = codigoSismed;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }    

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getTotal() {
        return precio.multiply(new BigDecimal(stock));
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
