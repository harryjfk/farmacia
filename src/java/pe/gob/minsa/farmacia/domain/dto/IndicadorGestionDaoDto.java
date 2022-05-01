package pe.gob.minsa.farmacia.domain.dto;

import java.sql.Timestamp;

public class IndicadorGestionDaoDto {
    private int idProducto;
    private String descripcion;
    private String nombreFormaFarmaceutica;
    private Timestamp fechaRegistro;
    private int estrSop;
    private int petitorio;
    private int cantidad;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreFormaFarmaceutica() {
        return nombreFormaFarmaceutica;
    }

    public void setNombreFormaFarmaceutica(String nombreFormaFarmaceutica) {
        this.nombreFormaFarmaceutica = nombreFormaFarmaceutica;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }    
    
    public int getEstrSop() {
        return estrSop;
    }

    public void setEstrSop(int estrSop) {
        this.estrSop = estrSop;
    }

    public int getPetitorio() {
        return petitorio;
    }

    public void setPetitorio(int petitorio) {
        this.petitorio = petitorio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

   
}