/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import java.util.List;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;

/**
 *
 * @author armando
 */
public class VentaKitProductoDTO {
    private List<VentaProducto> ventaProductos;
    private boolean hasMessage;
    private String message;

    public VentaKitProductoDTO(List<VentaProducto> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }

    /**
     * @return the ventaProductos
     */
    public List<VentaProducto> getVentaProductos() {
        return ventaProductos;
    }

    /**
     * @param ventaProductos the ventaProductos to set
     */
    public void setVentaProductos(List<VentaProducto> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }

    /**
     * @return the hasMessage
     */
    public boolean isHasMessage() {
        return hasMessage;
    }

    /**
     * @param hasMessage the hasMessage to set
     */
    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
