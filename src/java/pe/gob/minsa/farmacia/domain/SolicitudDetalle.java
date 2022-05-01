/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class SolicitudDetalle extends BaseDomain implements Serializable {
    
    private int idSolicitudDetalle;
    private int idSolicitud;
    private int idProducto;
    private String descripcion;
    private int tipoMedicamento;
    private String descripcionProd;
    private String descripcionFarm;
    private int aprobado;
    private int cantidad;
    private String motivo;
    private String condicion;
    private String concentracion;
    private Timestamp fechaAprobacion;
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Timestamp getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Timestamp fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getDescripcionFarm() {
        return descripcionFarm;
    }

    public void setDescripcionFarm(String descripcionFarm) {
        this.descripcionFarm = descripcionFarm;
    }

    public String getDescripcionProd() {
        return descripcionProd;
    }

    public void setDescripcionProd(String descripcionProd) {
        this.descripcionProd = descripcionProd;
    }

    public int getIdSolicitudDetalle() {
        return idSolicitudDetalle;
    }

    public void setIdSolicitudDetalle(int idSolicitudDetalle) {
        this.idSolicitudDetalle = idSolicitudDetalle;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

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

    public int getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(int tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }
    
}
