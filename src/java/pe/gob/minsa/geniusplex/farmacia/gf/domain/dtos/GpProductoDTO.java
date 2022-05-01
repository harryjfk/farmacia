/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain.dtos;

import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */
public class GpProductoDTO {

    private GpProducto producto;
    private int stock;
    private GpMovimientoProducto mv;

    public GpProductoDTO() {}
    
    public GpProductoDTO(GpMovimientoProducto mv) {
        this.producto = mv.getIdProducto();
        this.stock = mv.getCantidad();
        this.mv = mv;
    }
    
    public GpProductoDTO(GpProducto producto, Integer stock) {
        this.producto = producto;
        this.stock = stock;
    }
    
    public GpProductoDTO(GpProducto producto) {
        this.producto = producto;
        this.stock = 0;
    }

    public String getDescripcion() {
        return producto.getDescripcion();
    }

    public long getIdProducto() {
        return this.producto.getIdProducto();
    }
    
    public int getStock() {
        return stock;
    }

}
