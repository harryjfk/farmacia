package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos;

import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;

public class TarjetaControlDto {

    private Materias insumo;
    private int ingresos;
    private int salidas;
    private int saldo;

    public TarjetaControlDto(Materias insumo, Integer salida) {
        this.insumo = insumo;
        this.ingresos = insumo.getCantidad();
        this.salidas = salida;
        this.saldo = ingresos - salidas;
    }

    public TarjetaControlDto() {
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }

    public int getSalidas() {
        return salidas;
    }

    public void setSalidas(int salidas) {
        this.salidas = salidas;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Materias getInsumo() {
        return insumo;
    }

    public void setInsumo(Materias insumo) {
        this.insumo = insumo;
    }
}
