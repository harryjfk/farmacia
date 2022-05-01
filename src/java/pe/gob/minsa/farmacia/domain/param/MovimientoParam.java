package pe.gob.minsa.farmacia.domain.param;

import java.sql.Timestamp;
import pe.gob.minsa.farmacia.domain.TipoMovimiento;

public class MovimientoParam {
    
    private TipoMovimiento tipoMovimento;
    private Timestamp fechaDesde;
    private Timestamp fechaHasta;

    public TipoMovimiento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimiento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public Timestamp getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Timestamp fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Timestamp getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Timestamp fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
}
