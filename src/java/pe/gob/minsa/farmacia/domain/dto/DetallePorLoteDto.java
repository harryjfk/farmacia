package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DetallePorLoteDto {
    
    private BigDecimal precio;
    private Timestamp fechaVencimiento;
    private String registroSanitario;

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }
}