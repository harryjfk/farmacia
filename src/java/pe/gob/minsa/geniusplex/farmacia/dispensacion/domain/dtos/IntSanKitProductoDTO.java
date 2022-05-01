/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import java.util.ArrayList;
import java.util.List;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitariaProducto;

/**
 *
 * @author armando
 */
public class IntSanKitProductoDTO {
    private List<IntervSanitariaProducto> intervSanitariaProductos = new ArrayList<IntervSanitariaProducto>();
    private boolean hasMessage;
    private String message;

    public IntSanKitProductoDTO(List<IntervSanitariaProducto> intervSanitariaProductos) {
        this.intervSanitariaProductos = intervSanitariaProductos;
    }
    
    public IntSanKitProductoDTO() {
    }


    /**
     * @return the intervSanitariaProductos
     */
    public List<IntervSanitariaProducto> getIntervSaniProductos() {
        return intervSanitariaProductos;
    }

    /**
     * @param intervSanitariaProductos the intervSanitariaProductos to set
     */
    public void setVentaProductos(List<IntervSanitariaProducto> intervSanitariaProductos) {
        this.intervSanitariaProductos = intervSanitariaProductos;
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
