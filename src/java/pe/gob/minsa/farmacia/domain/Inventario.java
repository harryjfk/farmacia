package pe.gob.minsa.farmacia.domain;

import java.sql.Timestamp;

public class Inventario extends BaseDomain {

    private int idInventario;
    private int idPeriodo;
    private int NumeroInventario;
    private int idAlmacen;
    private int idTipoProceso;
    private Timestamp fechaProceso;
    private Timestamp fechaCierre;

    public int getIdTipoProceso(){
        return idTipoProceso;
    }
    public void setIdTipoProceso(int idTipoProceso)
    {
        this.idTipoProceso = idTipoProceso;
    }
    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getNumeroInventario() {
        return NumeroInventario;
    }

    public void setNumeroInventario(int NumeroInventario) {
        this.NumeroInventario = NumeroInventario;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Timestamp getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Timestamp fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }    
}
