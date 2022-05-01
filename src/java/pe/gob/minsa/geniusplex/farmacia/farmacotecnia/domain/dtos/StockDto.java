/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos;

import java.math.BigDecimal;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpUnidadMedida;

/**
 *
 * @author armando
 */
public class StockDto {

    private long stock;
    private Integer salidas;
    private String nombre;
    private Long id;
    private GpUnidadMedida unidad;
    private Integer ingreso;
    private BigDecimal precio;
    
    //Long, String, GpUnidadMedida, 
   //BigDecimal, int, int
    
    public StockDto(Long id, String nombre, GpUnidadMedida unidad,
            BigDecimal precio, Integer ingreso, Integer salidas) {
        if (salidas == null) {
            salidas = 0;
        }
        this.salidas = salidas;
        this.nombre = nombre;
        this.id = id;
        this.unidad = unidad;
        this.ingreso = ingreso;
        this.precio = precio;
        this.stock = ingreso - salidas;
        this.salidas = salidas;
    }

    /**
     * @return the stock
     */
    public long getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getSalidas() {
        return salidas;
    }

    public void setSalidas(Integer salidas) {
        this.salidas = salidas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GpUnidadMedida getUnidad() {
        return unidad;
    }

    public void setUnidad(GpUnidadMedida unidad) {
        this.unidad = unidad;
    }

    public Integer getIngreso() {
        return ingreso;
    }

    public void setIngreso(Integer ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
