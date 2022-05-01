/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author stark
 */
public class Consumo {

    private final List<Integer> ids;
    private final List<String> cod;
    private final List<String> producto;
    private final List<List<Double>> days;
    private final List<Double> total;
    private final List<BigDecimal> price;
    private final List<BigDecimal> importe;
    private final String monthName;

    public Consumo(String month) {
        ids = new ArrayList<Integer>();
        cod = new ArrayList<String>();
        producto = new ArrayList<String>();
        days = new ArrayList<List<Double>>();
        total = new ArrayList<Double>();
        price = new ArrayList<BigDecimal>();
        importe = new ArrayList<BigDecimal>();
        this.monthName = month;
    }

    public Consumo() {
        monthName = "";
        ids = new ArrayList<Integer>();
        cod = new ArrayList<String>();
        producto = new ArrayList<String>();
        days = new ArrayList<List<Double>>();
        total = new ArrayList<Double>();
        price = new ArrayList<BigDecimal>();
        importe = new ArrayList<BigDecimal>();
    }

    public void AddVenta(Venta venta) {

        int pos;

        for (VentaProducto ventas : venta.getVentaProductos()) {
            pos = ids.indexOf(ventas.getIdProducto());
            if (pos < 0) {
                ids.add(ventas.getIdProducto());
                pos = ids.size() - 1;
                days.add(pos, new ArrayList<Double>(32));
                for (int i = 1; i < 33; i++) {
                    days.get(pos).add(0.0);
                }
                total.add(pos, 0.0);
                cod.add(pos, ventas.getProducto().getIdProducto().toString());
                producto.add(pos, ventas.getProducto().getDescripcion());
                price.add(pos, ventas.getPrecio());
                importe.add(pos, new BigDecimal(0));
            }

            total.set(pos, total.get(pos) + ventas.getCantidad());

            BigDecimal bigTotal = new BigDecimal(total.get(pos));
            importe.set(pos, price.get(pos).multiply(bigTotal));
            Date ventafechaRegistro = venta.getVentafechaRegistro();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(ventafechaRegistro);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            days.get(pos).set(day, days.get(pos).get(day) + ventas.getCantidad());
        }
    }

    public String getMonthName() {
        return monthName;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public List<String> getCod() {
        return cod;
    }

    public List<String> getProducto() {
        return producto;
    }

    public List<List<Double>> getDays() {
        return days;
    }

    public List<Double> getTotal() {
        return total;
    }

    public List<BigDecimal> getPrice() {
        return price;
    }

    public List<BigDecimal> getImporte() {
        return importe;
    }

    private String obtenerDescProducto(GpProducto producto) {
        return String.format("%s - %s - %s - %s - %s",
                producto.getDescripcion(),
                producto.getConcentracion(),
                producto.getIdFormaFarmaceutica().getNombreFormaFarmaceutica(),
                producto.getIdUnidadMedida().getNombreUnidadMedida(),
                producto.getPresentacion());
    }
}
