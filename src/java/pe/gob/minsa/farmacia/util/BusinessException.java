package pe.gob.minsa.farmacia.util;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    private final List<String> errores;

    public BusinessException(List<String> errores) {
        this.errores = errores;
    }

    public List<String> getMensajesError() {
        for (int i = 0; i <= errores.size() - 1; ++i) {
            errores.set(i, errores.get(i) + ".");
        }
        return errores;
    }

}
