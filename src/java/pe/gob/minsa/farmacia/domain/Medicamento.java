/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.domain;

/**
 *
 * @author User
 */
public class Medicamento {
    
    private String idSol;
    private String idSolicitudDetalle;
    private String nomProducto;
    private String nomFarmaceutica;
    private String idProducto;
    private String concentracion;
    private String forma;
    private String via;
    private String dosis;
    private String costo;
    private String duracion;
    private String costotrat;
    private String aprobadoTexto;
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAprobadoTexto() {
        return aprobadoTexto;
    }

    public void setAprobadoTexto(String aprobadoTexto) {
        this.aprobadoTexto = aprobadoTexto;
    }

    public String getNomFarmaceutica() {
        return nomFarmaceutica;
    }

    public void setNomFarmaceutica(String nomFarmaceutica) {
        this.nomFarmaceutica = nomFarmaceutica;
    }

    public String getIdSolicitudDetalle() {
        return idSolicitudDetalle;
    }

    public void setIdSolicitudDetalle(String idSolicitudDetalle) {
        this.idSolicitudDetalle = idSolicitudDetalle;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }
    
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    
    public String getIdSol() {
        return idSol;
    }

    public void setIdSol(String idSol) {
        this.idSol = idSol;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCostotrat() {
        return costotrat;
    }

    public void setCostotrat(String costotrat) {
        this.costotrat = costotrat;
    }
}
