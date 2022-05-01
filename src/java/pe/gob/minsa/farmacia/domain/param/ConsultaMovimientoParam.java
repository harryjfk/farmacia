package pe.gob.minsa.farmacia.domain.param;

import pe.gob.minsa.farmacia.domain.TipoMovimiento;
import java.sql.Timestamp;

public class ConsultaMovimientoParam {

    private int idPeriodo;
    private TipoMovimiento tipoMovimiento;
    private int idAlmacenOrigen;
    private int idAlmacenDestino;
    private int idConcepto;
    private Timestamp fechaDesde;
    private Timestamp fechaHasta;
    private int activo;

    public ConsultaMovimientoParam() {
        tipoMovimiento = null;
        idAlmacenOrigen = 0;
        idAlmacenDestino = 0;
        idConcepto = 0;
        fechaDesde = null;
        fechaHasta = null;
        activo = -1;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public int getIdAlmacenOrigen() {
        return idAlmacenOrigen;
    }

    public void setIdAlmacenOrigen(int idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    public int getIdAlmacenDestino() {
        return idAlmacenDestino;
    }

    public void setIdAlmacenDestino(int idAlmacenDestino) {
        this.idAlmacenDestino = idAlmacenDestino;
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

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}