package pe.gob.minsa.farmacia.domain;

public enum TipoMovimientoConcepto {

    TODOS("M"),
    INGRESO("I"),
    SALIDA("S");

    private final String value;

    private TipoMovimientoConcepto(String value) {
        this.value = value;
    }

    public static TipoMovimientoConcepto fromString(String text) {
        if (text == null) {
            return null;
        } else {
            for (TipoMovimientoConcepto t : TipoMovimientoConcepto.values()) {
                if (text.equalsIgnoreCase(t.value)) {
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
