package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Menu extends BaseDomain implements Serializable {

    private int idMenu;
    private String nombreMenu;
    private int idSubmodulo;
    private int orden;

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public int getIdSubmodulo() {
        return idSubmodulo;
    }

    public void setIdSubmodulo(int idSubmodulo) {
        this.idSubmodulo = idSubmodulo;
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
        hash = 17 * hash + this.idMenu;
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
        final Menu other = (Menu) obj;
        if (this.idMenu != other.idMenu) {
            return false;
        }
        return true;
    }    
}
