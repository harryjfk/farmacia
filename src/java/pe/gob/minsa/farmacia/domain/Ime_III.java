/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import pe.gob.minsa.farmacia.util.UtilDto;

/**
 *
 * @author admin
 */
public class Ime_III implements Serializable{
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    private int id;
    private int idIme;
    private Timestamp fecha;
    private String fechaString;
    private String partida, detalleGasto,docFuente;
    private BigDecimal importe;


    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
        this.fechaString = sdf.format(getFecha());
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getDetalleGasto() {
        return detalleGasto;
    }

    public void setDetalleGasto(String detalleGasto) {
        this.detalleGasto = detalleGasto;
    }

    public String getDocFuente() {
        return docFuente;
    }

    public void setDocFuente(String docFuente) {
        this.docFuente = docFuente;
    }

    public BigDecimal getImporte() {
        return UtilDto.getBigDecimalFromNull(importe);
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public int getIdIme() {
        return idIme;
    }

    public void setIdIme(int idIme) {
        this.idIme = idIme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public String getFechaString(){
        return fechaString;
    }
    
}
