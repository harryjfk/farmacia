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
public class BalanceSemestral extends BaseDomain implements Serializable {
    
    private int idSolicitud;
    private int idSolicitudDetalleProducto;
    private Timestamp fechaAprobacion;
    private Timestamp fecha;
    private String descripcionProducto;
    private String concentracion;
    private String nombreFormaFarmaceutica;
    private String formaPresentacion;
    private int cantidad;
    private String precio;
    private String descripcionMedicamento;
    private String institucion;
    private String establecimiento;
    private String motivo;
    private String condicion;
    private String aprobado;
    private String tipoMedicamento;
    private int cantidadSol;

    public int getCantidadSol() {
        return cantidadSol;
    }

    public void setCantidadSol(int cantidadSol) {
        this.cantidadSol = cantidadSol;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
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

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdSolicitudDetalleProducto() {
        return idSolicitudDetalleProducto;
    }

    public void setIdSolicitudDetalleProducto(int idSolicitudDetalleProducto) {
        this.idSolicitudDetalleProducto = idSolicitudDetalleProducto;
    }

    public Timestamp getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Timestamp fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getNombreFormaFarmaceutica() {
        return nombreFormaFarmaceutica;
    }

    public void setNombreFormaFarmaceutica(String nombreFormaFarmaceutica) {
        this.nombreFormaFarmaceutica = nombreFormaFarmaceutica;
    }

    public String getFormaPresentacion() {
        return formaPresentacion;
    }

    public void setFormaPresentacion(String formaPresentacion) {
        this.formaPresentacion = formaPresentacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcionMedicamento() {
        return descripcionMedicamento;
    }

    public void setDescripcionMedicamento(String descripcionMedicamento) {
        this.descripcionMedicamento = descripcionMedicamento;
    }
    
}
