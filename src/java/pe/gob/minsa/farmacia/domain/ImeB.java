/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class ImeB implements Serializable{
    
    private String numSerie, numBoletaDe, numBoletaA;
    private int recetasDemandaE, recetasSisE, recetasIntsanE, recetasSoatE, recetasExoneracionE, recetasExternasE;
    private int recetasDemandaD, recetasSisD, recetasIntsanD, recetasSoatD, recetasExoneracionD, recetasExternasD;
    private BigDecimal AVenta, AIntSan, BCredito, BSoat, BOtros, BSis, BIntSan, Bdn,exonNegativo, totalGastosAbastecimiento, totalGAdmin;

    private Float DSoat;
    private Float ESoat;
    private Float DSis;
    private Float ESis;
    private Float DIntsan;
    private Float EIntsan;
    
    private Float CuentaCobrarAcumMesAnt_Ventas;
    private Float CuentaCobrarAcumMesAnt_Soat;
    private Float CuentaCobrarAcumMesAnt_Sis;
    private Float CuentaCobrarAcumMesAnt_InterSanit;
    private Float SaldoDisponibleMesAnt_Medicamentos;
    private Float SaldoDisponibleMesAnt_GastosAdmin;
    private Float Exoneraciones_negativo;
    private Float TotalAbastecimientoMes;

    public Float getDSoat() {
        return DSoat;
    }

    public void setDSoat(Float DSoat) {
        this.DSoat = DSoat;
    }

    public Float getESoat() {
        return ESoat;
    }

    public void setESoat(Float ESoat) {
        this.ESoat = ESoat;
    }

    public Float getDSis() {
        return DSis;
    }

    public void setDSis(Float DSis) {
        this.DSis = DSis;
    }

    public Float getESis() {
        return ESis;
    }

    public void setESis(Float ESis) {
        this.ESis = ESis;
    }

    public Float getDIntsan() {
        return DIntsan;
    }

    public void setDIntsan(Float DIntsan) {
        this.DIntsan = DIntsan;
    }

    public Float getEIntsan() {
        return EIntsan;
    }

    public void setEIntsan(Float EIntsan) {
        this.EIntsan = EIntsan;
    }

    public Float getCuentaCobrarAcumMesAnt_Ventas() {
        return CuentaCobrarAcumMesAnt_Ventas;
    }

    public void setCuentaCobrarAcumMesAnt_Ventas(Float CuentaCobrarAcumMesAnt_Ventas) {
        this.CuentaCobrarAcumMesAnt_Ventas = CuentaCobrarAcumMesAnt_Ventas;
    }

    public Float getCuentaCobrarAcumMesAnt_Soat() {
        return CuentaCobrarAcumMesAnt_Soat;
    }

    public void setCuentaCobrarAcumMesAnt_Soat(Float CuentaCobrarAcumMesAnt_Soat) {
        this.CuentaCobrarAcumMesAnt_Soat = CuentaCobrarAcumMesAnt_Soat;
    }

    public Float getCuentaCobrarAcumMesAnt_Sis() {
        return CuentaCobrarAcumMesAnt_Sis;
    }

    public void setCuentaCobrarAcumMesAnt_Sis(Float CuentaCobrarAcumMesAnt_Sis) {
        this.CuentaCobrarAcumMesAnt_Sis = CuentaCobrarAcumMesAnt_Sis;
    }

    public Float getCuentaCobrarAcumMesAnt_InterSanit() {
        return CuentaCobrarAcumMesAnt_InterSanit;
    }

    public void setCuentaCobrarAcumMesAnt_InterSanit(Float CuentaCobrarAcumMesAnt_InterSanit) {
        this.CuentaCobrarAcumMesAnt_InterSanit = CuentaCobrarAcumMesAnt_InterSanit;
    }

    public Float getSaldoDisponibleMesAnt_Medicamentos() {
        return SaldoDisponibleMesAnt_Medicamentos;
    }

    public void setSaldoDisponibleMesAnt_Medicamentos(Float SaldoDisponibleMesAnt_Medicamentos) {
        this.SaldoDisponibleMesAnt_Medicamentos = SaldoDisponibleMesAnt_Medicamentos;
    }

    public Float getSaldoDisponibleMesAnt_GastosAdmin() {
        return SaldoDisponibleMesAnt_GastosAdmin;
    }

    public void setSaldoDisponibleMesAnt_GastosAdmin(Float SaldoDisponibleMesAnt_GastosAdmin) {
        this.SaldoDisponibleMesAnt_GastosAdmin = SaldoDisponibleMesAnt_GastosAdmin;
    }

    public Float getExoneraciones_negativo() {
        return Exoneraciones_negativo;
    }

    public void setExoneraciones_negativo(Float Exoneraciones_negativo) {
        this.Exoneraciones_negativo = Exoneraciones_negativo;
    }

    public Float getTotalAbastecimientoMes() {
        return TotalAbastecimientoMes;
    }

    public void setTotalAbastecimientoMes(Float TotalAbastecimientoMes) {
        this.TotalAbastecimientoMes = TotalAbastecimientoMes;
    }

    
    
    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getNumBoletaDe() {
        return numBoletaDe;
    }

    public void setNumBoletaDe(String numBoletaDe) {
        this.numBoletaDe = numBoletaDe;
    }

    public String getNumBoletaA() {
        return numBoletaA;
    }

    public void setNumBoletaA(String numBoletaA) {
        this.numBoletaA = numBoletaA;
    }

    public int getRecetasDemandaE() {
        return recetasDemandaE;
    }

    public void setRecetasDemandaE(int recetasDemandaE) {
        this.recetasDemandaE = recetasDemandaE;
    }

    public int getRecetasSisE() {
        return recetasSisE;
    }

    public void setRecetasSisE(int recetasSisE) {
        this.recetasSisE = recetasSisE;
    }

    public int getRecetasIntsanE() {
        return recetasIntsanE;
    }

    public void setRecetasIntsanE(int recetasIntsanE) {
        this.recetasIntsanE = recetasIntsanE;
    }

    public int getRecetasSoatE() {
        return recetasSoatE;
    }

    public void setRecetasSoatE(int recetasSoatE) {
        this.recetasSoatE = recetasSoatE;
    }

    public int getRecetasExoneracionE() {
        return recetasExoneracionE;
    }

    public void setRecetasExoneracionE(int recetasExoneracionE) {
        this.recetasExoneracionE = recetasExoneracionE;
    }

    public int getRecetasExternasE() {
        return recetasExternasE;
    }

    public void setRecetasExternasE(int recetasExternasE) {
        this.recetasExternasE = recetasExternasE;
    }

    public int getRecetasDemandaD() {
        return recetasDemandaD;
    }

    public void setRecetasDemandaD(int recetasDemandaD) {
        this.recetasDemandaD = recetasDemandaD;
    }

    public int getRecetasSisD() {
        return recetasSisD;
    }

    public void setRecetasSisD(int recetasSisD) {
        this.recetasSisD = recetasSisD;
    }

    public int getRecetasIntsanD() {
        return recetasIntsanD;
    }

    public void setRecetasIntsanD(int recetasIntsanD) {
        this.recetasIntsanD = recetasIntsanD;
    }

    public int getRecetasSoatD() {
        return recetasSoatD;
    }

    public void setRecetasSoatD(int recetasSoatD) {
        this.recetasSoatD = recetasSoatD;
    }

    public int getRecetasExoneracionD() {
        return recetasExoneracionD;
    }

    public void setRecetasExoneracionD(int recetasExoneracionD) {
        this.recetasExoneracionD = recetasExoneracionD;
    }

    public int getRecetasExternasD() {
        return recetasExternasD;
    }

    public void setRecetasExternasD(int recetasExternasD) {
        this.recetasExternasD = recetasExternasD;
    }

    
    
    public BigDecimal getAVenta() {
        return AVenta;
    }
    
    public void setAVenta(BigDecimal aVenta) {
        this.AVenta = aVenta;
    }

    public void setAVenta(String aVenta) {
        this.AVenta = new BigDecimal(aVenta);
    }

    public BigDecimal getBCredito() {
        return BCredito;
    }

    public BigDecimal getBSoat() {
        return BSoat;
    }

    public BigDecimal getBOtros() {
        return BOtros;
    }

    public BigDecimal getBSis() {
        return BSis;
    }

    public BigDecimal getBIntSan() {
        return BIntSan;
    }

    public BigDecimal getBdn() {
        return Bdn;
    }

    public BigDecimal getTotalGastosAbastecimiento() {
        return totalGastosAbastecimiento;
    }

    public BigDecimal getTotalGAdmin() {
        return totalGAdmin;
    }

    public void setBCredito(BigDecimal BCredito) {
        this.BCredito = BCredito;
    }

    public void setBSoat(BigDecimal BSoat) {
        this.BSoat = BSoat;
    }

    public void setBOtros(BigDecimal BOtros) {
        this.BOtros = BOtros;
    }

    public void setBSis(BigDecimal BSis) {
        this.BSis = BSis;
    }

    public void setBIntSan(BigDecimal BIntSan) {
        this.BIntSan = BIntSan;
    }

    public void setBdn(BigDecimal Bdn) {
        this.Bdn = Bdn;
    }

    public void setTotalGastosAbastecimiento(BigDecimal totalGastosAbastecimiento) {
        this.totalGastosAbastecimiento = totalGastosAbastecimiento;
    }

    public void setTotalGAdmin(BigDecimal totalGAdmin) {
        this.totalGAdmin = totalGAdmin;
    }
    
    public void setBCredito(String BCredito) {
        this.BCredito = new BigDecimal(BCredito);
    }

    public void setBSoat(String BSoat) {
        this.BSoat = new BigDecimal(BSoat);
    }

    public void setBOtros(String BOtros) {
        this.BOtros = new BigDecimal(BOtros);
    }

    public void setBSis(String BSis) {
        this.BSis = new BigDecimal(BSis);
    }

    public void setBIntSan(String BIntSan) {
        this.BIntSan = new BigDecimal(BIntSan);
    }

    public void setBdn(String Bdn) {
        this.Bdn = new BigDecimal(Bdn);
    }

    public void setTotalGastosAbastecimiento(String totalGastosAbastecimiento) {
        this.totalGastosAbastecimiento = new BigDecimal(totalGastosAbastecimiento);
    }

    public void setTotalGAdmin(String totalGAdmin) {
        this.totalGAdmin = new BigDecimal(totalGAdmin);
    }

    public BigDecimal getExonNegativo() {
        return exonNegativo;
    }

    public void setExonNegativo(BigDecimal exonNegativo) {
        this.exonNegativo = exonNegativo;
    }
    public void setExonNegativo(String exonNegativo) {
        this.exonNegativo = new BigDecimal(exonNegativo);
    }

    public BigDecimal getAIntSan() {
        return AIntSan;
    }

    public void setAIntSan(BigDecimal AIntSan) {
        this.AIntSan = AIntSan;
    }
    public void setAIntSan(String AIntSan) {
        this.AIntSan = new BigDecimal(AIntSan);
    }
    
}
