package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;

public class DetalleStockPorAlmacen {

    private String almacen;
    private String codigoAlmacen;
    private BigDecimal precio;
    private int cantidad;
    
    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public BigDecimal getImporte(){
        return precio.multiply(new BigDecimal(cantidad));
    }
}
