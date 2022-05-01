package pe.gob.minsa.farmacia.domain.param;

import java.sql.Timestamp;

public class IngresoAlmacenParam {

    private int idAlmacenOrigen;
    private int idAlmacenDestino;
    private Timestamp fechaDesde;
    private Timestamp fechaHasta;
    private int idPeriodo;
    private int idProveedor;
    
    public IngresoAlmacenParam(){
        idAlmacenOrigen = 0;
        fechaDesde = null;
        fechaHasta = null;
        idProveedor = 0;
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

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    
}
