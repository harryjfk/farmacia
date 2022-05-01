
package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Ubigeo extends BaseDomain implements Serializable{

    private String idUbigeo;
    private String nombreUbigeo;

    public String getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(String idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public String getNombreUbigeo() {
        return nombreUbigeo;
    }

    public void setNombreUbigeo(String nombreUbigeo) {
        this.nombreUbigeo = nombreUbigeo;
    }
    
}
