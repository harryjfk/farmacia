package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class ProductoSiga extends BaseDomain implements Serializable {
    
    private int idProductoSiga;
    private String codigoSiga;
    private String nombreProductoSiga;

    public String getCodigoSiga() {
        return codigoSiga;
    }

    public void setCodigoSiga(String codigoSiga) {
        this.codigoSiga = codigoSiga;
    }    
    
    public int getIdProductoSiga() {
        return idProductoSiga;
    }

    public void setIdProductoSiga(int idProductoSiga) {
        this.idProductoSiga = idProductoSiga;
    }

    public String getNombreProductoSiga() {
        return nombreProductoSiga;
    }

    public void setNombreProductoSiga(String nombreProductoSiga) {
        this.nombreProductoSiga = nombreProductoSiga;
    }
}
