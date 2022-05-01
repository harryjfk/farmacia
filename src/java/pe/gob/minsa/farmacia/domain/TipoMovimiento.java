package pe.gob.minsa.farmacia.domain;

public enum TipoMovimiento {

    INGRESO("I"),
    SALIDA("S");

    private final String value;

    private TipoMovimiento(String value) {
        this.value = value;
    }

    public static TipoMovimiento fromString(String text) {
        if (text == null) {
            return null;
        } else {
            for (TipoMovimiento t : TipoMovimiento.values()) {
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
