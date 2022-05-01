/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;

/**
 *
 * @author armando Encapsula los datos referentes a un corte de caja. Esta clase
 * es una especie de DTO
 */
public class CorteCajaDTO implements Serializable {

    private List<Venta> ventas = new ArrayList<Venta>();
    private List<CorteCajaVentaDTO> tblVentas = new ArrayList<CorteCajaVentaDTO>();
    private HashMap<String, String> resumenFormPago = new HashMap<String, String>();
    private BigDecimal totalVenta = BigDecimal.ZERO;
    private BigDecimal totalImpuestoIGV = BigDecimal.ZERO;
    private BigDecimal overallTotal = BigDecimal.ZERO;
    private int cantidadAnulados;
    private BigDecimal montoAnulados = BigDecimal.ZERO;

    public CorteCajaDTO(List<Venta> ventas, BigDecimal totalVenta, BigDecimal totalImpuestoIGV, BigDecimal overallTotal, int cantidadAnulados, BigDecimal montoAnulados) {
        this.totalVenta = totalVenta;
        this.totalImpuestoIGV = totalImpuestoIGV;
        this.overallTotal = overallTotal;
        this.cantidadAnulados = cantidadAnulados;
        this.montoAnulados = montoAnulados;
        this.ventas = ventas;
    }

    public CorteCajaDTO(List<Venta> ventas) {
        this.ventas = ventas;
        procesar();
    }

    /**
     * @return the ventas
     */
    public List<Venta> getVentas() {
        return ventas;
    }

    /**
     * @param ventas the ventas to set
     */
    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    /**
     * @return the resumenFormPago
     */
    public HashMap<String, String> getResumenFormPago() {
        return resumenFormPago;
    }

    /**
     * @param resumenFormPago the resumenFormPago to set
     */
    public void setResumenFormPago(HashMap<String, String> resumenFormPago) {
        this.resumenFormPago = resumenFormPago;
    }

    /**
     * @return the totalVenta
     */
    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    /**
     * @param totalVenta the totalVenta to set
     */
    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * @return the totalImpuestoIGV
     */
    public BigDecimal getTotalImpuestoIGV() {
        return totalImpuestoIGV;
    }

    /**
     * @param totalImpuestoIGV the totalImpuestoIGV to set
     */
    public void setTotalImpuestoIGV(BigDecimal totalImpuestoIGV) {
        this.totalImpuestoIGV = totalImpuestoIGV;
    }

    /**
     * @return the overallTotal
     */
    public BigDecimal getOverallTotal() {
        return overallTotal;
    }

    /**
     * @param overallTotal the overallTotal to set
     */
    public void setOverallTotal(BigDecimal overallTotal) {
        this.overallTotal = overallTotal;
    }

    /**
     * @return the cantidadAnulados
     */
    public int getCantidadAnulados() {
        return cantidadAnulados;
    }

    /**
     * @param cantidadAnulados the cantidadAnulados to set
     */
    public void setCantidadAnulados(int cantidadAnulados) {
        this.cantidadAnulados = cantidadAnulados;
    }

    /**
     * @return the montoAnulados
     */
    public BigDecimal getMontoAnulados() {
        return montoAnulados;
    }

    /**
     * @param montoAnulados the montoAnulados to set
     */
    public void setMontoAnulados(BigDecimal montoAnulados) {
        this.montoAnulados = montoAnulados;
    }

    private void procesar() {
        for (Venta venta : ventas) {
            actualizarResumenFP(venta);
            actualizarAnulados(venta);
            BigDecimal ventaSubTotal = venta.getSubTotalPreventa().add(venta.getRedondeoPreventa());
            totalVenta = totalVenta.add(ventaSubTotal);
            totalImpuestoIGV = totalImpuestoIGV.add(venta.getImpuestoPreventa());
            overallTotal = overallTotal
                    .add(ventaSubTotal)
                    .add(venta.getImpuestoPreventa());

            crearVentaDTO(venta);
        }
    }

    private void actualizarAnulados(Venta venta) {
        if (venta.isAnulada() != null && venta.isAnulada()) {
            cantidadAnulados++;
            BigDecimal total = getNetoAPagar(venta);
            montoAnulados = montoAnulados.add(total);
        }
    }

    private BigDecimal getNetoAPagar(Venta venta) {
        BigDecimal total = venta.getSubTotalPreventa()
                .add(venta.getImpuestoPreventa())
                .add(new BigDecimal(venta.getRedondeoPreventa().toString()))
                .setScale(2, RoundingMode.UP);
        return total;
    }

    private void actualizarResumenFP(Venta venta) throws NumberFormatException {
        String fpDesc = venta.getFormaDePago().getDescripcion();
        if (resumenFormPago.containsKey(fpDesc.toUpperCase())) {
            String cantMonto = resumenFormPago.get(fpDesc.toUpperCase());
            String[] cantMontoArr = cantMonto.split("-");
            int cant = Integer.parseInt(cantMontoArr[0]);
            BigDecimal monto = new BigDecimal(cantMontoArr[1]);
            cant++;
            BigDecimal ventaTotal = this.getNetoAPagar(venta);
            monto = monto.add(ventaTotal);
            cantMonto = cant + "-" + monto;
            resumenFormPago.put(fpDesc.toUpperCase(), cantMonto);
        } else {
            BigDecimal ventaTotal = this.getNetoAPagar(venta);
            String cantMonto = 1 + "-" + ventaTotal;
            resumenFormPago.put(fpDesc.toUpperCase(), cantMonto);
        }
    }

    /**
     * @return the tblVentas
     */
    public List<CorteCajaVentaDTO> getTblVentas() {
        return tblVentas;
    }

    /**
     * @param tblVentas the tblVentas to set
     */
    public void setTblVentas(List<CorteCajaVentaDTO> tblVentas) {
        this.tblVentas = tblVentas;
    }

    public void checkPeriodo(String periodo) {
        List<Venta> tmp = new ArrayList<Venta>();
        tblVentas = new ArrayList<CorteCajaVentaDTO>();
        for (Venta venta : ventas) {
            Date ventafechaRegistro = venta.getVentafechaRegistro();
            Calendar date = GregorianCalendar.getInstance();
            date.setTime(ventafechaRegistro);
            int year = date.get(GregorianCalendar.YEAR);
            if (year == Integer.parseInt(periodo)) {
                tmp.add(venta);
                crearVentaDTO(venta);
            }
        }
        ventas = tmp;
    }

    private void crearVentaDTO(Venta venta) {
        CorteCajaVentaDTO ventaDTO = new CorteCajaVentaDTO(venta);
        getTblVentas().add(ventaDTO);
    }
}
