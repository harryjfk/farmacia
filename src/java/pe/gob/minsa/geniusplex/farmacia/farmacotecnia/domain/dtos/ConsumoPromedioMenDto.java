/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;

/**
 *
 * @author armando
 */
public class ConsumoPromedioMenDto {

    private long codigo;
    private String descripcion;
    private int enero;
    private int febrero;
    private int marzo;
    private int abril;
    private int mayo;
    private int junio;
    private int julio;
    private int agosto;
    private int septiembre;
    private int octubre;
    private int noviembre;
    private int diciembre;
    private int promedio;
    @JsonIgnore
    private final Materias m;
    @JsonIgnore
    private final int cantidadInicial;

    public ConsumoPromedioMenDto(Materias m, Integer cantidad) {
        this.m = m;
        this.descripcion = m.getNombre();
        this.codigo = m.getId();
        if(cantidad == null)
            cantidad = 0;
        this.cantidadInicial = cantidad;
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the enero
     */
    public int getEnero() {
        return enero;
    }

    /**
     * @param enero the enero to set
     */
    public void setEnero(int enero) {
        this.enero = enero;
    }

    /**
     * @return the febrero
     */
    public int getFebrero() {
        return febrero;
    }

    /**
     * @param febrero the febrero to set
     */
    public void setFebrero(int febrero) {
        this.febrero = febrero;
    }

    /**
     * @return the marzo
     */
    public int getMarzo() {
        return marzo;
    }

    /**
     * @param marzo the marzo to set
     */
    public void setMarzo(int marzo) {
        this.marzo = marzo;
    }

    /**
     * @return the abril
     */
    public int getAbril() {
        return abril;
    }

    /**
     * @param abril the abril to set
     */
    public void setAbril(int abril) {
        this.abril = abril;
    }

    /**
     * @return the mayo
     */
    public int getMayo() {
        return mayo;
    }

    /**
     * @param mayo the mayo to set
     */
    public void setMayo(int mayo) {
        this.mayo = mayo;
    }

    /**
     * @return the junio
     */
    public int getJunio() {
        return junio;
    }

    /**
     * @param junio the junio to set
     */
    public void setJunio(int junio) {
        this.junio = junio;
    }

    /**
     * @return the julio
     */
    public int getJulio() {
        return julio;
    }

    /**
     * @param julio the julio to set
     */
    public void setJulio(int julio) {
        this.julio = julio;
    }

    /**
     * @return the agosto
     */
    public int getAgosto() {
        return agosto;
    }

    /**
     * @param agosto the agosto to set
     */
    public void setAgosto(int agosto) {
        this.agosto = agosto;
    }

    /**
     * @return the septiembre
     */
    public int getSeptiembre() {
        return septiembre;
    }

    /**
     * @param septiembre the septiembre to set
     */
    public void setSeptiembre(int septiembre) {
        this.septiembre = septiembre;
    }

    /**
     * @return the octubre
     */
    public int getOctubre() {
        return octubre;
    }

    /**
     * @param octubre the octubre to set
     */
    public void setOctubre(int octubre) {
        this.octubre = octubre;
    }

    /**
     * @return the noviembre
     */
    public int getNoviembre() {
        return noviembre;
    }

    /**
     * @param noviembre the noviembre to set
     */
    public void setNoviembre(int noviembre) {
        this.noviembre = noviembre;
    }

    /**
     * @return the diciembre
     */
    public int getDiciembre() {
        return diciembre;
    }

    /**
     * @param diciembre the diciembre to set
     */
    public void setDiciembre(int diciembre) {
        this.diciembre = diciembre;
    }

    /**
     * @return the promedio
     */
    public String getPromedio() {
        DecimalFormat df = new DecimalFormat("0.00");
        double prom = promedio / 12.0;
        return df.format(prom);
    }

    /**
     * @return the saldoActual
     */
    public String getSaldoActual() {
        return String.valueOf(promedio);
    }

    /**
     * @return the mp
     */
    public int getCantidadInicial() {
        return cantidadInicial;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.codigo ^ (this.codigo >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConsumoPromedioMenDto other = (ConsumoPromedioMenDto) obj;
        return this.codigo == other.codigo;
    }

    public void procesar(ConsumoPromedioMenDto dto) {
        Date fechaRegistro = dto.getM().getFechaCreacion();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fechaRegistro);
        int mes = calendar.get(Calendar.MONTH);
        int cantidad = dto.getCantidadInicial();
        actualizarMeses(mes, cantidad);
        promedio+=cantidad;
    }
    
    public void procesar() {
        Date fechaRegistro = this.getM().getFechaCreacion();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fechaRegistro);
        int mes = calendar.get(Calendar.MONTH);
        int cantidad = this.getCantidadInicial();
        actualizarMeses(mes, cantidad);
        promedio+=cantidad;
    }
    

    private void actualizarMeses(int mes, int cantidad) {
        switch (mes) {
            case 0:
                this.enero += cantidad;
                break;
            case 1:
                this.febrero += cantidad;
                break;
            case 2:
                this.marzo += cantidad;
                break;
            case 3:
                this.abril += cantidad;
                break;
            case 4:
                this.mayo += cantidad;
                break;
            case 5:
                this.junio += cantidad;
                break;
            case 6:
                this.julio += cantidad;
                break;
            case 7:
                this.agosto += cantidad;
                break;
            case 8:
                this.septiembre += cantidad;
                break;
            case 9:
                this.octubre += cantidad;
                break;
            case 10:
                this.noviembre += cantidad;
                break;
            case 11:
                this.diciembre += cantidad;
                break;
        }
    }

    public Materias getM() {
        return m;
    }

}
