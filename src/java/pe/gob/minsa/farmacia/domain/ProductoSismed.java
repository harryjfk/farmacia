package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class ProductoSismed extends BaseDomain implements Serializable{
    
    private int idProductoSismed;
    private String codigoSismed;
    private String nombreProductoSismed;

    public int getIdProductoSismed() {
        return idProductoSismed;
    }

    public void setIdProductoSismed(int idProductoSismed) {
        this.idProductoSismed = idProductoSismed;
    }

    public String getCodigoSismed() {
        return codigoSismed;
    }

    public void setCodigoSismed(String codigoSismed) {
        this.codigoSismed = codigoSismed;
    }

    public String getNombreProductoSismed() {
        return nombreProductoSismed;
    }

    public void setNombreProductoSismed(String nombreProductoSismed) {
        this.nombreProductoSismed = nombreProductoSismed;
    }   
}
