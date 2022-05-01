package pe.gob.minsa.farmacia.domain.dto;

import java.sql.Timestamp;

public class IngresoAlmacenDto {

    private Timestamp FechaRegistro;
    private String codigoSismed;
    private String descripcion;

    public Timestamp getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(Timestamp FechaRegistro) {
        this.FechaRegistro = FechaRegistro;
    }

    public String getCodigoSismed() {
        return codigoSismed;
    }

    public void setCodigoSismed(String codigoSismed) {
        this.codigoSismed = codigoSismed;
    }    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
