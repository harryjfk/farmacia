package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Documento extends BaseDomain implements Serializable {

    private int idDocumento;
    private String numeracionInterna;
    private int idUsuario;
    private Timestamp fechaDocumento;
    private Timestamp fechaSalida;    
    private int idTipoAccion;
    private int idTipoDocumento;
    private String nroDocumento;
    private String asunto;
    private String remitente;
    private String destino;
    private String numeracionDireccion;
    private String observacion;
    private int despacho;
    private Timestamp fechaDespacho;
    private String extension;

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNumeracionInterna() {
        return numeracionInterna;
    }

    public void setNumeracionInterna(String numeracionInterna) {
        this.numeracionInterna = numeracionInterna;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Timestamp getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Timestamp fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Timestamp getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
    public int getIdTipoAccion() {
        return idTipoAccion;
    }

    public void setIdTipoAccion(int idTipoAccion) {
        this.idTipoAccion = idTipoAccion;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNumeracionDireccion() {
        return numeracionDireccion;
    }

    public void setNumeracionDireccion(String numeracionDireccion) {
        this.numeracionDireccion = numeracionDireccion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getDespacho() {
        return despacho;
    }

    public void setDespacho(int despacho) {
        this.despacho = despacho;
    }

    public Timestamp getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(Timestamp fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
