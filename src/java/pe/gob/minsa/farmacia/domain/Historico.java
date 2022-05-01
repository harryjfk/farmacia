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
public class Historico extends BaseDomain implements Serializable {
    
    private Timestamp fechaAprobacion;
    private String descripcionProducto;
    private String concentracion;
    private String nombreFormaFarmaceutica;
    private String formaPresentacion;
    private int anio;
    private int cantEnero;
    private int cantFebrero;
    private int cantMarzo;
    private int cantAbril;
    private int cantMayo;
    private int cantJunio;
    private int cantJulio;
    private int cantAgosto;
    private int cantSetiembre;
    private int cantOctubre;
    private int cantNoviembre;
    private int cantDiciembre;
    
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    public int getCantEnero() {
        return cantEnero;
    }

    public void setCantEnero(int cantEnero) {
        this.cantEnero = cantEnero;
    }

    public int getCantFebrero() {
        return cantFebrero;
    }

    public void setCantFebrero(int cantFebrero) {
        this.cantFebrero = cantFebrero;
    }

    public int getCantMarzo() {
        return cantMarzo;
    }

    public void setCantMarzo(int cantMarzo) {
        this.cantMarzo = cantMarzo;
    }

    public int getCantAbril() {
        return cantAbril;
    }

    public void setCantAbril(int cantAbril) {
        this.cantAbril = cantAbril;
    }

    public int getCantMayo() {
        return cantMayo;
    }

    public void setCantMayo(int cantMayo) {
        this.cantMayo = cantMayo;
    }

    public int getCantJunio() {
        return cantJunio;
    }

    public void setCantJunio(int cantJunio) {
        this.cantJunio = cantJunio;
    }

    public int getCantJulio() {
        return cantJulio;
    }

    public void setCantJulio(int cantJulio) {
        this.cantJulio = cantJulio;
    }

    public int getCantAgosto() {
        return cantAgosto;
    }

    public void setCantAgosto(int cantAgosto) {
        this.cantAgosto = cantAgosto;
    }

    public int getCantSetiembre() {
        return cantSetiembre;
    }

    public void setCantSetiembre(int cantSetiembre) {
        this.cantSetiembre = cantSetiembre;
    }

    public int getCantOctubre() {
        return cantOctubre;
    }

    public void setCantOctubre(int cantOctubre) {
        this.cantOctubre = cantOctubre;
    }

    public int getCantNoviembre() {
        return cantNoviembre;
    }

    public void setCantNoviembre(int cantNoviembre) {
        this.cantNoviembre = cantNoviembre;
    }

    public int getCantDiciembre() {
        return cantDiciembre;
    }

    public void setCantDiciembre(int cantDiciembre) {
        this.cantDiciembre = cantDiciembre;
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
    
}
