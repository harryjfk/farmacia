/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import javax.sql.DataSource;
import pe.gob.minsa.farmacia.domain.Ime;
import pe.gob.minsa.farmacia.domain.ImeB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.Ime_III;
import pe.gob.minsa.farmacia.util.UtilDao;


/**
 *
 * @author admin
 */
public class ImeDao {
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    Ime_IIIDao ime_iiiDao;
    
    public int procesar(Timestamp fechaDesde, Timestamp fechaHasta, int idAlmacen, ImeB imeB, ArrayList<Ime_III> detalleGasto){
        
        
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_IME_Procesar");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumSerie", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumBoletaDe", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumBoletaA", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasDemandaE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSisE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasIntsanE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSoatE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasExoneracionE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasExternasE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasDemandaD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSisD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasIntsanD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSoatD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasExoneracionD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasExternasD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@AVenta", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@AIntsan", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@BCredito", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@BSoat", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@BOtros", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@BSis", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@BIntSan", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Bdn", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@ExonNegativo", java.sql.Types.DECIMAL));

        
        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdIme", java.sql.Types.INTEGER));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NumSerie", imeB.getNumSerie());
        maps.addValue("@NumBoletaDe", imeB.getNumBoletaDe());
        maps.addValue("@NumBoletaA", imeB.getNumBoletaA());
        maps.addValue("@FechaDesde", fechaDesde);
        maps.addValue("@FechaHasta", fechaHasta);
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@RecetasDemandaE", imeB.getRecetasDemandaE());
        maps.addValue("@RecetasSisE", imeB.getRecetasSisE());
        maps.addValue("@RecetasIntsanE", imeB.getRecetasIntsanE());
        maps.addValue("@RecetasSoatE", imeB.getRecetasSoatE());
        maps.addValue("@RecetasExoneracionE", imeB.getRecetasExoneracionE());
        maps.addValue("@RecetasExternasE", imeB.getRecetasExternasE());
        
        maps.addValue("@RecetasDemandaD", imeB.getRecetasDemandaD());
        maps.addValue("@RecetasSisD", imeB.getRecetasSisD());
        maps.addValue("@RecetasIntsanD", imeB.getRecetasIntsanD());
        maps.addValue("@RecetasSoatD", imeB.getRecetasSoatD());
        maps.addValue("@RecetasExoneracionD", imeB.getRecetasExoneracionD());
        maps.addValue("@RecetasExternasD", imeB.getRecetasExternasD());
        
        maps.addValue("@AVenta", imeB.getAVenta());
        maps.addValue("@AIntsan", imeB.getAIntSan());
        maps.addValue("@BCredito", imeB.getBCredito());
        maps.addValue("@BSoat", imeB.getBSoat());
        maps.addValue("@BOtros", imeB.getBOtros());
        maps.addValue("@BSis", imeB.getBSis());
        maps.addValue("@BIntsan", imeB.getBIntSan());
        maps.addValue("@Bdn", imeB.getBdn());
        maps.addValue("@ExonNegativo", imeB.getExonNegativo());
        
        Map<String,Object> res = jdbcCall.execute(maps);
        int idIme = (Integer)res.get("@IdIme");
        
        for(Ime_III imeiii : detalleGasto){
            imeiii.setIdIme(idIme);
            ime_iiiDao.insertar(imeiii);
        }
        
        actualizar(idIme);
        
        return idIme;
    }
    
    public int procesar2(Timestamp fechaDesde, Timestamp fechaHasta, ImeB imeB, ArrayList<Ime_III> detalleGasto){
        
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_IME_InsertarTemp");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumSerie", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumBoletaDe", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumBoletaA", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@AVenta", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasDemandaD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasDemandaE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSisD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSisE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasIntsanD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasIntsanE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSoatD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasSoatE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasExoneracionD", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RecetasExoneracionE", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DSoat", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@ESoat", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DSis", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@ESis", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DIntsan", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EIntsan", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CuentaCobrarAcumMesAnt_Ventas", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CuentaCobrarAcumMesAnt_Soat", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CuentaCobrarAcumMesAnt_Sis", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CuentaCobrarAcumMesAnt_InterSanit", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@SaldoDisponibleMesAnt_Medicamentos", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@SaldoDisponibleMesAnt_GastosAdmin", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Exoneraciones_negativo", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TotalAbastecimientoMes", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdIme", java.sql.Types.INTEGER));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NumSerie", imeB.getNumSerie());
        maps.addValue("@NumBoletaDe", imeB.getNumBoletaDe());
        maps.addValue("@NumBoletaA", imeB.getNumBoletaA());
        maps.addValue("@FechaDesde", fechaDesde);
        maps.addValue("@FechaHasta", fechaHasta);
        maps.addValue("@AVenta", imeB.getAVenta());
        maps.addValue("@RecetasDemandaD", imeB.getRecetasDemandaD());
        maps.addValue("@RecetasDemandaE", imeB.getRecetasDemandaE());
        maps.addValue("@RecetasSisD", imeB.getRecetasSisD());
        maps.addValue("@RecetasSisE", imeB.getRecetasSisE());
        maps.addValue("@RecetasIntsanD", imeB.getRecetasIntsanD());
        maps.addValue("@RecetasIntsanE", imeB.getRecetasIntsanE());
        maps.addValue("@RecetasSoatD", imeB.getRecetasSoatD());
        maps.addValue("@RecetasSoatE", imeB.getRecetasSoatE());
        maps.addValue("@RecetasExoneracionD", imeB.getRecetasExoneracionD());
        maps.addValue("@RecetasExoneracionE", imeB.getRecetasExoneracionE());
        maps.addValue("@DSoat", imeB.getDSoat());
        maps.addValue("@ESoat", imeB.getESoat());
        maps.addValue("@DSis", imeB.getDSis());
        maps.addValue("@ESis", imeB.getESis());
        maps.addValue("@DIntsan", imeB.getDIntsan());
        maps.addValue("@EIntsan", imeB.getEIntsan());
        maps.addValue("@CuentaCobrarAcumMesAnt_Ventas", imeB.getCuentaCobrarAcumMesAnt_Ventas());
        maps.addValue("@CuentaCobrarAcumMesAnt_Soat", imeB.getCuentaCobrarAcumMesAnt_Soat());
        maps.addValue("@CuentaCobrarAcumMesAnt_Sis", imeB.getCuentaCobrarAcumMesAnt_Sis());
        maps.addValue("@CuentaCobrarAcumMesAnt_InterSanit", imeB.getCuentaCobrarAcumMesAnt_InterSanit());
        maps.addValue("@SaldoDisponibleMesAnt_Medicamentos", imeB.getSaldoDisponibleMesAnt_Medicamentos());
        maps.addValue("@SaldoDisponibleMesAnt_GastosAdmin", imeB.getSaldoDisponibleMesAnt_GastosAdmin());
        maps.addValue("@Exoneraciones_negativo", imeB.getExoneraciones_negativo());
        maps.addValue("@TotalAbastecimientoMes", imeB.getTotalAbastecimientoMes());
        
        Map<String,Object> res = jdbcCall.execute(maps);
        int idIme = (Integer)res.get("@IdIme");
        
        for(Ime_III imeiii : detalleGasto){
            imeiii.setIdIme(idIme);
            ime_iiiDao.insertar(imeiii);
        }
        
        actualizar(idIme);
        
        return idIme;
    }
    
    public void actualizar(int idIme){
        try {
            SimpleJdbcCall    jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_IME_ACTUALIZAR");

            jdbcCall.addDeclaredParameter(new SqlParameter("@IdIme", java.sql.Types.INTEGER));
            
            MapSqlParameterSource maps = new MapSqlParameterSource();
            
            maps.addValue("@IdIme", idIme);
            
            jdbcCall.execute(maps);
        } catch (EmptyResultDataAccessException ex) {
            throw ex;
        }
    }
    
    public Ime obtener(int idAlmacen){
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Ime ime = jdbcTemplate.queryForObject("{call Far_Ime_Obtener(?)}", new Object[]{idAlmacen}, new ImeRowMapper());
            return ime;
        } catch (EmptyResultDataAccessException ex) {
            return new Ime();
        }
    }
    
    public class ImeRowMapper implements RowMapper<Ime>{

        @Override
        public Ime mapRow(ResultSet rs, int i) throws SQLException {
            Ime ime = new Ime();
            
            ime.setIdIme(rs.getInt("IdIme"));
            ime.setIdAlmacen(rs.getInt("IdAlmacen"));
            ime.setNumeroSerie(rs.getString("NumSerie"));
            ime.setNumBoletaDe(rs.getString("NumBoletaDe"));
            ime.setNumBoletaA(rs.getString("NumBoletaA"));
            ime.setFechaDesde(rs.getTimestamp("FechaDesde"));
            ime.setFechaHasta(rs.getTimestamp("FechaHasta"));
            
            ime.setRecetasDemandaEmitidas(rs.getInt("RecetasDemandaE"));
            ime.setRecetasSisEmitidas(rs.getInt("RecetasSisE"));
            ime.setRecetasIntervSanitEmitidas(rs.getInt("RecetasIntsanE"));
            ime.setRecetasSoatEmitidas(rs.getInt("RecetasSoatE"));
            ime.setRecetasExoneracionEmitidas(rs.getInt("RecetasExoneracionE"));
            ime.setRecetasExternasEmitidas(rs.getInt("RecetasExternasE"));
            
            ime.setRecetasDemandaDispensadas(rs.getInt("RecetasDemandaD"));
            ime.setRecetasSisDispensadas(rs.getInt("RecetasSisD"));
            ime.setRecetasIntervSanitDispensadas(rs.getInt("RecetasIntsanD"));
            ime.setRecetasSoatDispensadas(rs.getInt("RecetasSoatD"));
            ime.setRecetasExoneracionDispensadas(rs.getInt("RecetasExoneracionD"));
            ime.setRecetasExternasDispensadas(rs.getInt("RecetasExternasD"));
            
            
            ime.setAVenta(UtilDao.getBigDecimalFromNull(rs, "AVenta"));
            ime.setACredito(UtilDao.getBigDecimalFromNull(rs, "ACredito"));
            ime.setASoat(UtilDao.getBigDecimalFromNull(rs, "ASoat"));
            ime.setAOtros(UtilDao.getBigDecimalFromNull(rs, "AOtros"));
            ime.setASis(UtilDao.getBigDecimalFromNull(rs, "ASis"));
            ime.setAIntSan(UtilDao.getBigDecimalFromNull(rs, "AIntsan"));
            ime.setAdn(UtilDao.getBigDecimalFromNull(rs, "Adn"));
            
            ime.setBVenta(UtilDao.getBigDecimalFromNull(rs, "BVenta"));
            ime.setBCredito(UtilDao.getBigDecimalFromNull(rs, "BCredito"));
            ime.setBSoat(UtilDao.getBigDecimalFromNull(rs, "BSoat"));
            ime.setBOtros(UtilDao.getBigDecimalFromNull(rs, "BOtros"));
            ime.setBSis(UtilDao.getBigDecimalFromNull(rs, "BSis"));
            ime.setBIntSan(UtilDao.getBigDecimalFromNull(rs, "BIntsan"));
            ime.setBdn(UtilDao.getBigDecimalFromNull(rs, "Bdn"));
            
            ime.setDCredito(UtilDao.getBigDecimalFromNull(rs, "DCredito"));
            ime.setDSoat(UtilDao.getBigDecimalFromNull(rs, "DSoat"));
            ime.setDOtros(UtilDao.getBigDecimalFromNull(rs, "DOtros"));
            ime.setDSis(UtilDao.getBigDecimalFromNull(rs, "DSis"));
            ime.setDIntSan(UtilDao.getBigDecimalFromNull(rs, "DIntsan"));
            ime.setDdn(UtilDao.getBigDecimalFromNull(rs, "Ddn"));
            
            ime.setECredito(UtilDao.getBigDecimalFromNull(rs, "ECredito"));
            ime.setESoat(UtilDao.getBigDecimalFromNull(rs, "ESoat"));
            ime.setEOtros(UtilDao.getBigDecimalFromNull(rs, "EOtros"));
            ime.setESis(UtilDao.getBigDecimalFromNull(rs, "ESis"));
            ime.setEIntSan(UtilDao.getBigDecimalFromNull(rs, "EIntsan"));
            ime.setEdn(UtilDao.getBigDecimalFromNull(rs, "Edn"));
            
            
            ime.setSaldoMesAnteriorGAdmin(UtilDao.getBigDecimalFromNull(rs, "GTotalGadm"));
            ime.setSaldoMesAnteriorMed(UtilDao.getBigDecimalFromNull(rs, "GSaldoAntMed"));
    
            ime.setH10positivo(UtilDao.getBigDecimalFromNull(rs, "H10Positivo"));            
            ime.setFortNegativo(UtilDao.getBigDecimalFromNull(rs, "FortNegativo"));
            ime.setExonPositivo(UtilDao.getBigDecimalFromNull(rs, "ExonPositivo"));
            ime.setExonNegativo(UtilDao.getBigDecimalFromNull(rs, "ExonNegativo"));
            
            ime.setTotalGAdmin(UtilDao.getBigDecimalFromNull(rs, "TotalGadmMesNeg"));
            ime.setTotalAbastecimientoMed(UtilDao.getBigDecimalFromNull(rs, "TotalAbastMesNeg"));
            ime.setSaldoDispMedMesSiguiente(UtilDao.getBigDecimalFromNull(rs, "SalDispMesSigMed"));
            ime.setSaldoDispGAdmMesSiguiente(UtilDao.getBigDecimalFromNull(rs, "SalDispMesSigGadm"));
            ime.setActivo(rs.getInt("Activo"));
            ime.setUsuarioCreacion(rs.getInt("UsuarioCreacion"));
            ime.setFechaCreacion(rs.getTimestamp("FechaCreacion"));
            ime.setUsuarioModificacion(rs.getInt("UsuarioModificacion"));
            ime.setFechaModificacion(rs.getTimestamp("FechaModificacion"));
            
            ime.setCuentaCobrarAcumMesAnt_Ventas(rs.getFloat("CuentaCobrarAcumMesAnt_Ventas"));
            ime.setCuentaCobrarAcumMesAnt_Soat(rs.getFloat("CuentaCobrarAcumMesAnt_Soat"));
            ime.setCuentaCobrarAcumMesAnt_Sis(rs.getFloat("CuentaCobrarAcumMesAnt_Sis"));
            ime.setCuentaCobrarAcumMesAnt_InterSanit(rs.getFloat("CuentaCobrarAcumMesAnt_InterSanit"));
            ime.setSaldoDisponibleMesAnt_Medicamentos(rs.getFloat("SaldoDisponibleMesAnt_Medicamentos"));
            ime.setSaldoDisponibleMesAnt_GastosAdmin(rs.getFloat("SaldoDisponibleMesAnt_GastosAdmin"));
            ime.setExoneraciones_negativo(rs.getFloat("Exoneraciones_negativo"));
            ime.setTotalAbastecimientoMes(rs.getFloat("TotalAbastecimientoMes"));
            return ime;
            }
        
    }
    
}