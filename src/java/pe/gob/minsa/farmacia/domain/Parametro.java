package pe.gob.minsa.farmacia.domain;

public class Parametro {
    
    private int idParametro;
    private String nombreParametro;
    private String descripcionParametro;
    private String valor;

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }        

    public String getNombreParametro() {        
        return (nombreParametro == null) ? "" : nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public String getDescripcionParametro() {
        return (descripcionParametro == null) ? "" : descripcionParametro;        
    }

    public void setDescripcionParametro(String descripcionParametro) {
        this.descripcionParametro = descripcionParametro;
    }   

    public String getValor() {        
        return (valor == null) ? "" : valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }    
}
