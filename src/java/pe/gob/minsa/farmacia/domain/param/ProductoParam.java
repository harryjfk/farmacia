package pe.gob.minsa.farmacia.domain.param;

/**
 * Clase que proporiona los parámetros usados en el listado del mantenimiento de productos.
 * @author Bryan Chauca
 */

public class ProductoParam {

    private String descripcion;
    private int idFormaFarmaceutica;
    private int idTipoProducto;
    private int idUnidadMedida;
    private int estrSop;
    private int estrVta;
    private int traNac;
    private int traLoc;
    private int narcotico;

    public ProductoParam(){
        descripcion = "";
        idFormaFarmaceutica = 0;
        idTipoProducto = 0;
        idUnidadMedida = 0;
        estrSop = 2;
        estrVta = 2;
        traNac = 2;
        traLoc = 2;
        narcotico = 2;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(int idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getEstrSop() {
        return estrSop;
    }

    public void setEstrSop(int estrSop) {
        this.estrSop = estrSop;
    }

    public int getEstrVta() {
        return estrVta;
    }

    public void setEstrVta(int estrVta) {
        this.estrVta = estrVta;
    }

    public int getTraNac() {
        return traNac;
    }

    public void setTraNac(int traNac) {
        this.traNac = traNac;
    }

    public int getTraLoc() {
        return traLoc;
    }

    public void setTraLoc(int traLoc) {
        this.traLoc = traLoc;
    }

    public int getNarcotico() {
        return narcotico;
    }

    public void setNarcotico(int narcotico) {
        this.narcotico = narcotico;
    }
}