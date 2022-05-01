package pe.gob.minsa.farmacia.domain.dto;

import java.sql.Timestamp;

public class SalidaDto {

    private int idMovimiento;
    private int numeroMovimiento;
    private Timestamp fechaRegistro;
    private String almacenOrigen;
    private String almacenDestino;
    private String tipoDocumento;
    private String numeroDocumento;    

    public SalidaDto(int idMovimiento, int numeroMovimiento, Timestamp fechaRegistro, String almacenOrigen, String almacenDestino, String tipoDocumento, String numeroDocumento) {
        this.idMovimiento = idMovimiento;
        this.numeroMovimiento = numeroMovimiento;
        this.fechaRegistro = fechaRegistro;
        this.almacenOrigen = almacenOrigen;
        this.almacenDestino = almacenDestino;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;        
    }
    
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}