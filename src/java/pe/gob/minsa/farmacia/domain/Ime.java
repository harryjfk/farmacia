/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author admin
 */
public class Ime extends BaseDomain implements Serializable{
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    private int idIme;
    private Timestamp fechaDesde;
    private Timestamp fechaHasta;
    private String fechaDesdeString, fechaHastaString;
    private String numeroSerie, numBoletaDe, numBoletaA;
    private int idAlmacen;
    private int recetasDemandaEmitidas, recetasDemandaDispensadas;
    private int recetasSisEmitidas, recetasSisDispensadas;
    private int recetasIntervSanitEmitidas, recetasIntervSanitDispensadas;
    private int recetasSoatEmitidas, recetasSoatDispensadas;
    private int recetasExoneracionEmitidas, recetasExoneracionDispensadas;
    private int recetasExternasEmitidas, recetasExternasDispensadas;
    
    private BigDecimal AVenta, ACredito, ASoat, AOtros, ASis, AIntSan, Adn;
    private BigDecimal BVenta, BCredito, BSoat, BOtros, BSis, BIntSan, Bdn;
    private BigDecimal DCredito, DSoat, DOtros, DSis, DIntSan, Ddn;
    private BigDecimal ECredito, ESoat, EOtros, ESis, EIntSan, Edn;

    private BigDecimal h10positivo, fortNegativo;
    private BigDecimal exonPositivo, exonNegativo;
    
    private BigDecimal saldoMesAnteriorMed, saldoMesAnteriorGAdmin;
    private BigDecimal totalAbastecimientoMed, totalGAdmin;
    private BigDecimal saldoDispMedMesSiguiente, saldoDispGAdmMesSiguiente;
    
    
    //private Float DSoat;
    //private Float ESoat;
    //private Float DSis;
    //private Float ESis;
    //private Float DIntsan;
    //private Float EIntsan;
    
    private Float CuentaCobrarAcumMesAnt_Ventas;
    private Float CuentaCobrarAcumMesAnt_Soat;
    private Float CuentaCobrarAcumMesAnt_Sis;
    private Float CuentaCobrarAcumMesAnt_InterSanit;
    private Float SaldoDisponibleMesAnt_Medicamentos;
    private Float SaldoDisponibleMesAnt_GastosAdmin;
    private Float Exoneraciones_negativo;
    private Float TotalAbastecimientoMes;

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
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
    
    public BigDecimal rdrA(){
        try{
            return AVenta.add(ACredito).add(ASoat).add(AOtros);
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal rdrB(){
        try{
        return BVenta.add(BCredito).add(BSoat).add(BOtros);
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal ventas80(){
        try{
        return BVenta.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
        
    }
    public BigDecimal credito80(){
        try{
        return BCredito.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal soat80(){
        try{
        return BSoat.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal otros80(){
        try{
        return BOtros.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal rdr80(){
        try{
        return rdrB().multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal sis80(){
        try{
        return BSis.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal intervSan80(){
        try{
        return BIntSan.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal defensaNacional80(){
        try{
        return Bdn.multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    
    
    public BigDecimal ventas20(){
        try{
        return BVenta.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal credito20(){
        try{
        return BCredito.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal soat20(){
        try{
        return BSoat.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal otros20(){
        try{
        return BOtros.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal rdr20(){
        try{
        return rdrB().multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal sis20(){
        try{
        return BSis.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal intervSan20(){
        try{
        return BIntSan.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal defensaNacional20(){
        try{
        return Bdn.multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal CCredito(){
        try{
        return ACredito.subtract(BCredito);
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal CSoat(){
        try{
        return ASoat.subtract(BSoat);
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal COtros(){
        try{
        return AOtros.subtract(BOtros);
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal CSis(){
        try{
        return ASis.subtract(BSis);
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal CIntSan(){
        try{
        return AIntSan.subtract(BIntSan);
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal CDn(){
        try{
        return Adn.subtract(Bdn);
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal ERdr(){
        try{
        return ECredito.add(ESoat).add(EOtros);
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal ASubtotal(){
        try{
        return rdrA().add(ASis).add(AIntSan).add(Adn);
        }catch(Exception e){
            return null;
        }
    }
    public BigDecimal BSubtotal(){
        try{
        return rdrB().add(BSis).add(BIntSan).add(Bdn);
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal BSubtotal80(){
        try{
        return BSubtotal().multiply(new BigDecimal(0.8));
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal Bsubtotal20(){
        try{
        return BSubtotal().multiply(new BigDecimal(0.2));
        }catch(Exception e){
            return null;
        }
    }
    
    public BigDecimal ESubtotal(){
        try{
        return ERdr().add(ESis).add(EIntSan).add(Edn);
        }catch(Exception e){
            return null;
        }
    }
    
    public int getIdIme() {
        return idIme;
    }

    public void setIdIme(int idIme) {
        this.idIme = idIme;
    }

    public Timestamp getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Timestamp fechaDesde) {
        this.fechaDesde = fechaDesde;
        this.fechaDesdeString = sdf.format(fechaDesde);
    }

    public Timestamp getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Timestamp fechaHasta) {
        this.fechaHasta = fechaHasta;
        this.fechaHastaString = sdf.format(fechaHasta);
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getRecetasDemandaEmitidas() {
        return recetasDemandaEmitidas;
    }

    public void setRecetasDemandaEmitidas(int recetasDemandaEmitidas) {
        this.recetasDemandaEmitidas = recetasDemandaEmitidas;
    }

    public int getRecetasDemandaDispensadas() {
        return recetasDemandaDispensadas;
    }

    public void setRecetasDemandaDispensadas(int recetasDemandaDispensadas) {
        this.recetasDemandaDispensadas = recetasDemandaDispensadas;
    }

    public int getRecetasSisEmitidas() {
        return recetasSisEmitidas;
    }

    public void setRecetasSisEmitidas(int recetasSisEmitidas) {
        this.recetasSisEmitidas = recetasSisEmitidas;
    }

    public int getRecetasSisDispensadas() {
        return recetasSisDispensadas;
    }

    public void setRecetasSisDispensadas(int recetasSisDispensadas) {
        this.recetasSisDispensadas = recetasSisDispensadas;
    }

    public int getRecetasIntervSanitEmitidas() {
        return recetasIntervSanitEmitidas;
    }

    public void setRecetasIntervSanitEmitidas(int recetasIntervSanitEmitidas) {
        this.recetasIntervSanitEmitidas = recetasIntervSanitEmitidas;
    }

    public int getRecetasIntervSanitDispensadas() {
        return recetasIntervSanitDispensadas;
    }

    public void setRecetasIntervSanitDispensadas(int recetasIntervSanitDispensadas) {
        this.recetasIntervSanitDispensadas = recetasIntervSanitDispensadas;
    }

    public int getRecetasSoatEmitidas() {
        return recetasSoatEmitidas;
    }

    public void setRecetasSoatEmitidas(int recetasSoatEmitidas) {
        this.recetasSoatEmitidas = recetasSoatEmitidas;
    }

    public int getRecetasSoatDispensadas() {
        return recetasSoatDispensadas;
    }

    public void setRecetasSoatDispensadas(int recetasSoatDispensadas) {
        this.recetasSoatDispensadas = recetasSoatDispensadas;
    }

    public int getRecetasExoneracionEmitidas() {
        return recetasExoneracionEmitidas;
    }

    public void setRecetasExoneracionEmitidas(int recetasExoneracionEmitidas) {
        this.recetasExoneracionEmitidas = recetasExoneracionEmitidas;
    }

    public int getRecetasExoneracionDispensadas() {
        return recetasExoneracionDispensadas;
    }

    public void setRecetasExoneracionDispensadas(int recetasExoneracionDispensadas) {
        this.recetasExoneracionDispensadas = recetasExoneracionDispensadas;
    }

    public int getRecetasExternasEmitidas() {
        return recetasExternasEmitidas;
    }

    public void setRecetasExternasEmitidas(int recetasExternasEmitidas) {
        this.recetasExternasEmitidas = recetasExternasEmitidas;
    }

    public int getRecetasExternasDispensadas() {
        return recetasExternasDispensadas;
    }

    public void setRecetasExternasDispensadas(int recetasExternasDispensadas) {
        this.recetasExternasDispensadas = recetasExternasDispensadas;
    }

    public BigDecimal getAVenta() {
        return AVenta;
    }

    public void setAVenta(BigDecimal AVenta) {
        this.AVenta = AVenta;
    }

    public BigDecimal getACredito() {
        return ACredito;
    }

    public void setACredito(BigDecimal ACredito) {
        this.ACredito = ACredito;
    }

    public BigDecimal getASoat() {
        return ASoat;
    }

    public void setASoat(BigDecimal ASoat) {
        this.ASoat = ASoat;
    }

    public BigDecimal getAOtros() {
        return AOtros;
    }

    public void setAOtros(BigDecimal AOtros) {
        this.AOtros = AOtros;
    }

    public BigDecimal getASis() {
        return ASis;
    }

    public void setASis(BigDecimal ASis) {
        this.ASis = ASis;
    }

    public BigDecimal getAIntSan() {
        return AIntSan;
    }

    public void setAIntSan(BigDecimal AIntSan) {
        this.AIntSan = AIntSan;
    }

    public BigDecimal getAdn() {
        return Adn;
    }

    public void setAdn(BigDecimal Adn) {
        this.Adn = Adn;
    }

    public BigDecimal getBVenta() {
        return BVenta;
    }

    public void setBVenta(BigDecimal BVenta) {
        this.BVenta = BVenta;
    }

    public BigDecimal getBCredito() {
        return BCredito;
    }

    public void setBCredito(BigDecimal BCredito) {
        this.BCredito = BCredito;
    }

    public BigDecimal getBSoat() {
        return BSoat;
    }

    public void setBSoat(BigDecimal BSoat) {
        this.BSoat = BSoat;
    }

    public BigDecimal getBOtros() {
        return BOtros;
    }

    public void setBOtros(BigDecimal BOtros) {
        this.BOtros = BOtros;
    }

    public BigDecimal getBSis() {
        return BSis;
    }

    public void setBSis(BigDecimal BSis) {
        this.BSis = BSis;
    }

    public BigDecimal getBIntSan() {
        return BIntSan;
    }

    public void setBIntSan(BigDecimal BIntSan) {
        this.BIntSan = BIntSan;
    }

    public BigDecimal getBdn() {
        return Bdn;
    }

    public void setBdn(BigDecimal Bdn) {
        this.Bdn = Bdn;
    }

    public BigDecimal getDCredito() {
        return DCredito;
    }

    public void setDCredito(BigDecimal DCredito) {
        this.DCredito = DCredito;
    }

    public BigDecimal getDSoat() {
        return DSoat;
    }

    public void setDSoat(BigDecimal DSoat) {
        this.DSoat = DSoat;
    }

    public BigDecimal getDOtros() {
        return DOtros;
    }

    public void setDOtros(BigDecimal DOtros) {
        this.DOtros = DOtros;
    }

    public BigDecimal getDSis() {
        return DSis;
    }

    public void setDSis(BigDecimal DSis) {
        this.DSis = DSis;
    }

    public BigDecimal getDIntSan() {
        return DIntSan;
    }

    public void setDIntSan(BigDecimal DIntSan) {
        this.DIntSan = DIntSan;
    }

    public BigDecimal getDdn() {
        return Ddn;
    }

    public void setDdn(BigDecimal Ddn) {
        this.Ddn = Ddn;
    }

    public BigDecimal getECredito() {
        return ECredito;
    }

    public void setECredito(BigDecimal ECredito) {
        this.ECredito = ECredito;
    }

    public BigDecimal getESoat() {
        return ESoat;
    }

    public void setESoat(BigDecimal ESoat) {
        this.ESoat = ESoat;
    }

    public BigDecimal getEOtros() {
        return EOtros;
    }

    public void setEOtros(BigDecimal EOtros) {
        this.EOtros = EOtros;
    }

    public BigDecimal getESis() {
        return ESis;
    }

    public void setESis(BigDecimal ESis) {
        this.ESis = ESis;
    }

    public BigDecimal getEIntSan() {
        return EIntSan;
    }

    public void setEIntSan(BigDecimal EIntSan) {
        this.EIntSan = EIntSan;
    }

    public BigDecimal getEdn() {
        return Edn;
    }

    public void setEdn(BigDecimal Edn) {
        this.Edn = Edn;
    }

    public BigDecimal getH10positivo() {
        return h10positivo;
    }

    public void setH10positivo(BigDecimal h10positivo) {
        this.h10positivo = h10positivo;
    }

    public BigDecimal getFortNegativo() {
        return fortNegativo;
    }

    public void setFortNegativo(BigDecimal fortNegativo) {
        this.fortNegativo = fortNegativo;
    }

    public BigDecimal getExonPositivo() {
        return exonPositivo;
    }

    public void setExonPositivo(BigDecimal exonPositivo) {
        this.exonPositivo = exonPositivo;
    }

    public BigDecimal getExonNegativo() {
        return exonNegativo;
    }

    public void setExonNegativo(BigDecimal exonNegativo) {
        this.exonNegativo = exonNegativo;
    }

    public BigDecimal getSaldoMesAnteriorMed() {
        return saldoMesAnteriorMed;
    }

    public void setSaldoMesAnteriorMed(BigDecimal saldoMesAnteriorMed) {
        this.saldoMesAnteriorMed = saldoMesAnteriorMed;
    }

    public BigDecimal getSaldoMesAnteriorGAdmin() {
        return saldoMesAnteriorGAdmin;
    }

    public void setSaldoMesAnteriorGAdmin(BigDecimal saldoMesAnteriorGAdmin) {
        this.saldoMesAnteriorGAdmin = saldoMesAnteriorGAdmin;
    }

    public BigDecimal getTotalAbastecimientoMed() {
        return totalAbastecimientoMed;
    }

    public void setTotalAbastecimientoMed(BigDecimal totalAbastecimientoMed) {
        this.totalAbastecimientoMed = totalAbastecimientoMed;
    }

    public BigDecimal getTotalGAdmin() {
        return totalGAdmin;
    }

    public void setTotalGAdmin(BigDecimal totalGAdmin) {
        this.totalGAdmin = totalGAdmin;
    }

    public BigDecimal getSaldoDispMedMesSiguiente() {
        return saldoDispMedMesSiguiente;
    }

    public void setSaldoDispMedMesSiguiente(BigDecimal saldoDispMedMesSiguiente) {
        this.saldoDispMedMesSiguiente = saldoDispMedMesSiguiente;
    }

    public BigDecimal getSaldoDispGAdmMesSiguiente() {
        return saldoDispGAdmMesSiguiente;
    }

    public void setSaldoDispGAdmMesSiguiente(BigDecimal saldoDispGAdmMesSiguiente) {
        this.saldoDispGAdmMesSiguiente = saldoDispGAdmMesSiguiente;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
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

    public String getFechaDesdeString() {
        return fechaDesdeString;
    }

    public void setFechaDesdeString(String fechaDesdeString) {
        this.fechaDesdeString = fechaDesdeString;
    }

    public String getFechaHastaString() {
        return fechaHastaString;
    }

    public void setFechaHastaString(String fechaHastaString) {
        this.fechaHastaString = fechaHastaString;
    }

    
}
