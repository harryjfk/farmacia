package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Opcion extends BaseDomain implements Serializable {

    private int idOpcion;
    private String appOpcion;
    private String nombreOpcion;
    private int idSubmenu;   

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getAppOpcion() {
        return appOpcion;
    }

    public void setAppOpcion(String appOpcion) {
        this.appOpcion = appOpcion;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public int getIdSubmenu() {
        return idSubmenu;
    }

    public void setIdSubmenu(int idSubmenu) {
        this.idSubmenu = idSubmenu;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idOpcion;
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
        final Opcion other = (Opcion) obj;
        if (this.idOpcion != other.idOpcion) {
            return false;
        }
        return true;
    }
}
