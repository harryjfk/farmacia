package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class FormaFarmaceutica extends BaseDomain implements Serializable {

    private int idFormaFarmaceutica;
    private String nombreFormaFarmaceutica;
    private String abreviatura;

    public int getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(int idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public String getNombreFormaFarmaceutica() {
        return nombreFormaFarmaceutica;
    }

    public void setNombreFormaFarmaceutica(String nombreFormaFarmaceutica) {
        this.nombreFormaFarmaceutica = nombreFormaFarmaceutica;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
