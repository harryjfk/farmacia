package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Modulo extends BaseDomain implements Serializable  {

    private int idModulo;
    private String nombreModulo;
    private int orden;

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Modulo other = (Modulo) obj;
        if (this.idModulo != other.idModulo) {
            return false;
        }
        return true;
    }
    
}
