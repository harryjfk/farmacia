package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class MovimientoProducto extends BaseDomain implements Serializable {
    
    private int idMovimientoProducto;
    private int idMovimiento;
    private int idProducto;
    private int cantidad;
    private BigDecimal precio;
    private BigDecimal total;
    private String lote;
    private Timestamp fechaVencimiento;
    private String registroSanitario;

    private String nombreProducto;
    
    public MovimientoProducto() {
    }
    
    public MovimientoProducto(int idMovimientoProducto, int idMovimiento, int idProducto, int cantidad, BigDecimal precio, BigDecimal total, String lote, Timestamp fechaVencimiento, String registroSanitario, String nombreProducto) {
        this.idMovimientoProducto = idMovimientoProducto;
        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.lote = lote;
        this.fechaVencimiento = fechaVencimiento;
        this.registroSanitario = registroSanitario;
        this.nombreProducto = nombreProducto;
    }    

    public int getIdMovimientoProducto() {
        return idMovimientoProducto;
    }

    public void setIdMovimientoProducto(int idMovimientoProducto) {
        this.idMovimientoProducto = idMovimientoProducto;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public void setPrecio(BigDecimal Precio) {
        this.precio = Precio;
    }
    
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal Total) {
        this.total = Total;
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

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }    
}