package pe.gob.minsa.farmacia.domain;

public enum TipoPrecioConcepto {

    ADQUISICION("A"),
    OPERACION("O"),
    DISTRIBUCION("D");

    private final String value;

    private TipoPrecioConcepto(String value) {        
        this.value = value;
    }

    public static TipoPrecioConcepto fromString(String text) {
        if (text == null) {
            return null;
        } else {
            for (TipoPrecioConcepto t : TipoPrecioConcepto.values()) {
                if (text.equalsIgnoreCase(t.value)) {
                    return t;
                }
            }
        }
        return null;
    }
    
    public static String toValue(TipoPrecioConcepto tipoPrecioConcepto){
        if (tipoPrecioConcepto == null) {
            return null;
        } else {
            return tipoPrecioConcepto.value;            
        }        
    }
}
