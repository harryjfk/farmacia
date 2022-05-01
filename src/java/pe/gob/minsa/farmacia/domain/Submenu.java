package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Submenu extends BaseDomain implements Serializable {

    private int idSubmenu;
    private String nombreSubmenu;
    private String enlace;
    private int idMenu;
    private int orden;

    public int getIdSubmenu() {
        return idSubmenu;
    }

    public void setIdSubmenu(int idSubmenu) {
        this.idSubmenu = idSubmenu;
    }

    public String getNombreSubmenu() {
        return nombreSubmenu;
    }

    public void setNombreSubmenu(String nombreSubmenu) {
        this.nombreSubmenu = nombreSubmenu;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
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
        hash = 41 * hash + this.idSubmenu;
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
        final Submenu other = (Submenu) obj;
        if (this.idSubmenu != other.idSubmenu) {
            return false;
        }
        return true;
    }
    
    
}
