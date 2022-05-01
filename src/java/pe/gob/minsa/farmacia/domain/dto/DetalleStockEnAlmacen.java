package pe.gob.minsa.farmacia.domain.dto;

import java.sql.Timestamp;

public class DetalleStockEnAlmacen {
    private int cantidad;
    private String lote;
    private Timestamp fechaVencimiento;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
    
    
}
