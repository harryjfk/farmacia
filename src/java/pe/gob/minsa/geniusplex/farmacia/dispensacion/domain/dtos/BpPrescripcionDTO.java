/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;

/**
 *
 * @author armando
 */
public class BpPrescripcionDTO {

    private Cliente paciente;
    private DiagnosticoCIE diagnostico;
    private Date fecha;
    private String nroReceta;
    private List<Venta> ventas;
    private List<BpPrescDetDTO> detalles = new ArrayList<BpPrescDetDTO>();

    public BpPrescripcionDTO(Cliente paciente, DiagnosticoCIE diagnostico, Date fecha, String nroReceta, List<BpPrescDetDTO> detalles) {
        this.paciente = paciente;
        this.diagnostico = diagnostico;
        this.fecha = fecha;
        this.nroReceta = nroReceta;
        this.detalles = detalles;
    }

    public BpPrescripcionDTO(Cliente paciente, DiagnosticoCIE diagnostico, Date fecha, String nroReceta) {
        this.paciente = paciente;
        this.diagnostico = diagnostico;
        this.fecha = fecha;
        this.nroReceta = nroReceta;
    }

    public BpPrescripcionDTO() {
    }

    public BpPrescripcionDTO(List<Venta> ventas) {
        this.ventas = ventas;
        for (Venta venta : ventas) {
            if (this.paciente == null) {
                this.paciente = venta.getCliente();
            }
            for (VentaProducto vp : venta.getVentaProductos()) {
                String cod = vp.getIdProducto() + "-" + vp.getIdVenta();
                BpPrescDetDTO dtoDet = new BpPrescDetDTO(cod , venta.getNroVenta(), vp.getProducto().getDescripcion(), vp.getProducto(), venta.getPrescriptor());
                detalles.add(dtoDet);
            }
        }
    }

    /**
     * @return the paciente
     */
    public Cliente getCliente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setCliente(Cliente paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the diagnostico
     */
    public DiagnosticoCIE getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(DiagnosticoCIE diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the nroReceta
     */
    public String getNroReceta() {
        return nroReceta;
    }

    /**
     * @param nroReceta the nroReceta to set
     */
    public void setNroReceta(String nroReceta) {
        this.nroReceta = nroReceta;
    }

    /**
     * @return the detalles
     */
    public List<BpPrescDetDTO> getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(List<BpPrescDetDTO> detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the ventas
     */
    public List<Venta> getVentas() {
        return ventas;
    }

    /**
     * @param ventas the ventas to set
     */
    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}
