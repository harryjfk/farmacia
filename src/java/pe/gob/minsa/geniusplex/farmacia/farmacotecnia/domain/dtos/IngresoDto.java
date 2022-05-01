/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos;

import java.util.Date;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;

/**
 *
 * @author armando
 */
public class IngresoDto {
    private Date fechaRegistro;
    private int codigo;
    private String producto;

    public IngresoDto(Date fechaRegistro, int codigo, String producto) {
        this.fechaRegistro = fechaRegistro;
        this.codigo = codigo;
        this.producto = producto;
    }
    
    public  IngresoDto(GpMovimientoProducto mp) {
        this.fechaRegistro = mp.getIdMovimiento().getFechaRegistro();
        this.codigo = mp.getIdProducto().getIdProducto();
        this.producto = mp.getIdProducto().getDescripcion();
    }


    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    
    
}
