package pe.gob.minsa.farmacia.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InventarioProducto extends BaseDomain {

    private int idInventarioProducto;
    private int idInventario;
    private int idProducto;    
    private String lote;
    private Timestamp fechaVencimiento;
    private int cantidad;
    private BigDecimal precio;
    private BigDecimal total;
    private int conteo;
    private int cantidadFaltante;
    private int cantidadSobrante;
    private int cantidadAlterado;

    public int getIdInventarioProducto() {
        return idInventarioProducto;
    }

    public void setIdInventarioProducto(int idInventarioProducto) {
        this.idInventarioProducto = idInventarioProducto;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getConteo() {
        return conteo;
    }

    public void setConteo(int conteo) {
        this.conteo = conteo;
    }

    public int getCantidadFaltante() {
        return cantidadFaltante;
    }

    public void setCantidadFaltante(int cantidadFaltante) {
        this.cantidadFaltante = cantidadFaltante;
    }

    public int getCantidadSobrante() {
        return cantidadSobrante;
    }

    public void setCantidadSobrante(int cantidadSobrante) {
        this.cantidadSobrante = cantidadSobrante;
    }

    public int getCantidadAlterado() {
        return cantidadAlterado;
    }

    public void setCantidadAlterado(int cantidadAlterado) {
        this.cantidadAlterado = cantidadAlterado;
    }
}
