package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Solicitud extends BaseDomain implements Serializable {

    private int idSolicitud;
    private String idMedico;
    private String establecimiento;
    private String institucion;
    private String justificacion;
    private String motivo;
    private int existeMedicamento;
    private Timestamp fecha;
    
    private String medico;
    private String extension;

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idMedico
     */
    public String getMedico() {
        return medico;
    }

    /**
     * @param idMedico the idMedico to set
     */
    public void setMedico(String medico) {
        this.medico = medico;
    }

    /**
     * @return the establecimiento
     */
    public String getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    /**
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the justificacion
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * @param justificacion the justificacion to set
     */
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the fecha
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }
    
    public int getExisteMedicamento() {
        return existeMedicamento;
    }

    public void setExisteMedicamento(int existeMedicamento) {
        this.existeMedicamento = existeMedicamento;
    }
    
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
