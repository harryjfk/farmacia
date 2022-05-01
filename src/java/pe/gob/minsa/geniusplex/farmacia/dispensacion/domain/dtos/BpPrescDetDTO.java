/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */
public class BpPrescDetDTO {
    private String codigo;
    private String nroVenta;
    private String medicamento;
    private GpProducto producto;
    private Prescriptor prescriptor;

    public BpPrescDetDTO(String codigo, String nroVenta, String medicamento, GpProducto producto, Prescriptor prescriptor) {
        this.codigo = codigo;
        this.nroVenta = nroVenta;
        this.medicamento = medicamento;
        this.producto = producto;
        this.prescriptor = prescriptor;
    }

    public BpPrescDetDTO() {
    }
    
    

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nroVenta
     */
    public String getNroVenta() {
        return nroVenta;
    }

    /**
     * @param nroVenta the nroVenta to set
     */
    public void setNroVenta(String nroVenta) {
        this.nroVenta = nroVenta;
    }

    /**
     * @return the medicamento
     */
    public String getMedicamento() {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     */
    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    /**
     * @return the producto
     */
    public GpProducto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }

    /**
     * @return the prescriptor
     */
    public Prescriptor getPrescriptor() {
        return prescriptor;
    }

    /**
     * @param prescriptor the prescriptor to set
     */
    public void setPrescriptor(Prescriptor prescriptor) {
        this.prescriptor = prescriptor;
    }
}
