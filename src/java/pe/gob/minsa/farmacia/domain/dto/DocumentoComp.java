package pe.gob.minsa.farmacia.domain.dto;

import java.sql.Timestamp;


public class DocumentoComp extends BaseEstado {
    
    private int idDocumento;
    private String numeracionInterna;
    private int idUsuario;
    private Timestamp fechaDocumento;
    private Timestamp fechaSalida;
    private int idTipoAccion;
    private String nombreTipoAccion;
    private int idTipoDocumento;
    private String nombreTipoDocumento;
    private String nroDocumento;
    private String asunto;
    private String numeracionDireccion;
    private String destino;
    private int despacho;
    private String observacion;
    private String remitente;
    private Timestamp fechaDespacho;
    private String extension;

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
    
    public int getIdDocumento() {        
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
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

    public String getNombreTipoAccion() {
        return nombreTipoAccion;
    }

    public void setNombreTipoAccion(String nombreTipoAccion) {
        this.nombreTipoAccion = nombreTipoAccion;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
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

    public String getNumeracionDireccion() {
        return numeracionDireccion;
    }

    public void setNumeracionDireccion(String numeracionDireccion) {
        this.numeracionDireccion = numeracionDireccion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }
}
