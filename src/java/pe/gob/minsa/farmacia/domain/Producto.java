package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Producto extends BaseDomain implements Serializable {

    private int idProducto;
    private String descripcion;
    private Integer idProductoSismed;
    private Integer idProductoSiga;
    private String abreviatura;
    private int idFormaFarmaceutica;
    private int idTipoProducto;
    private int idUnidadMedida;
    private String presentacion;
    private String concentracion;
    private int petitorio;
    private int estrSop;
    private int estrVta;
    private int traNac;
    private int traLoc;
    private int narcotico;
    private BigDecimal stockMin;
    private BigDecimal stockMax;
    private BigDecimal requerimiento;
    private int adscrito;

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

    public Integer getIdProductoSismed() {
        return idProductoSismed;
    }

    public void setIdProductoSismed(Integer idProductoSismed) {
        this.idProductoSismed = idProductoSismed;
    }

    public Integer getIdProductoSiga() {
        return idProductoSiga;
    }

    public void setIdProductoSiga(Integer idProductoSiga) {
        this.idProductoSiga = idProductoSiga;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(int idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public int getPetitorio() {
        return petitorio;
    }

    public void setPetitorio(int petitorio) {
        this.petitorio = petitorio;
    }

    public int getEstrSop() {
        return estrSop;
    }

    public void setEstrSop(int estrSop) {
        this.estrSop = estrSop;
    }

    public int getEstrVta() {
        return estrVta;
    }

    public void setEstrVta(int estrVta) {
        this.estrVta = estrVta;
    }

    public int getTraNac() {
        return traNac;
    }

    public void setTraNac(int traNac) {
        this.traNac = traNac;
    }

    public int getTraLoc() {
        return traLoc;
    }

    public void setTraLoc(int traLoc) {
        this.traLoc = traLoc;
    }

    public int getNarcotico() {
        return narcotico;
    }

    public void setNarcotico(int narcotico) {
        this.narcotico = narcotico;
    }

    public BigDecimal getStockMin() {
        return stockMin;
    }

    public void setStockMin(BigDecimal stockMin) {
        this.stockMin = stockMin;
    }

    public BigDecimal getStockMax() {
        return stockMax;
    }

    public void setStockMax(BigDecimal stockMax) {
        this.stockMax = stockMax;
    }

    public BigDecimal getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(BigDecimal requerimiento) {
        this.requerimiento = requerimiento;
    }

    public int getAdscrito() {
        return adscrito;
    }

    public void setAdscrito(int adscrito) {
        this.adscrito = adscrito;
    }    
}
