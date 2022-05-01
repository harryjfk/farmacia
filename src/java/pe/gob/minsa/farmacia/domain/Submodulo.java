package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Submodulo extends BaseDomain implements Serializable {
    
    private int idSubmodulo;
    private String nombreSubmodulo;
    private int idModulo;
    private int orden;

    public int getIdSubmodulo() {
        return idSubmodulo;
    }

    public void setIdSubmodulo(int idSubmodulo) {
        this.idSubmodulo = idSubmodulo;
    }

    public String getNombreSubmodulo() {
        return nombreSubmodulo;
    }

    public void setNombreSubmodulo(String nombreSubmodulo) {
        this.nombreSubmodulo = nombreSubmodulo;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }        

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idSubmodulo;
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
        final Submodulo other = (Submodulo) obj;
        if (this.idSubmodulo != other.idSubmodulo) {
            return false;
        }
        return true;
    }
}
