package pe.gob.minsa.farmacia.domain.param;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import pe.gob.minsa.farmacia.domain.Periodo;

public class IndicadorGestionParam {

    private int idPeriodo;
    private int ultimosMeses;
    private int idTipoProducto;

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getUltimosMeses() {        
        return ultimosMeses;
    }

    public void setUltimosMeses(int ultimosMeses) {
        if (ultimosMeses == 3 || ultimosMeses == 6) {
        } else {
            throw new IllegalArgumentException("Ultimos meses puede ser sólo de 3 o 6 meses");
        }
        this.ultimosMeses = ultimosMeses;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public Timestamp getFechaDesde() {
        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(idPeriodo);
        int mes = periodo.getMesEntero() - (ultimosMeses - 1);
        
        if( mes <= 0 ){
            mes = 1;
        }
        
        Calendar calendar = new GregorianCalendar(periodo.getAnio(), mes - 1, 1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public Timestamp getFechaHasta() {
        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(idPeriodo);
        int dias = diasDelMes(periodo.getMesEntero(), periodo.getAnio());
        Calendar calendar = new GregorianCalendar(periodo.getAnio(), periodo.getMesEntero() - 1, dias);
        return new Timestamp(calendar.getTimeInMillis());
    }

    private int diasDelMes(int mes, int año) {
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((año % 100 == 0) && (año % 400 == 0))
                        || ((año % 100 != 0) && (año % 4 == 0))) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
    }
}
