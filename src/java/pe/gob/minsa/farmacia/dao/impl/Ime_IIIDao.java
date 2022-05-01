/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.Ime_III;
import pe.gob.minsa.farmacia.util.UtilDao;

/**
 *
 * @author admin
 */
public class Ime_IIIDao {
    
    @Autowired
    DataSource dataSource;
    
    public void insertar(Ime_III detIme){
        
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("FAR_IME_III_INSERTAR");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdIme", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fecha", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Partida", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Detalle", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DocFuente", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Importe", java.sql.Types.DECIMAL));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdIme", detIme.getIdIme());
        maps.addValue("@Fecha", detIme.getFecha());
        maps.addValue("@Partida", detIme.getPartida());
        maps.addValue("@Detalle", detIme.getDetalleGasto());
        maps.addValue("@DocFuente", detIme.getDocFuente());
        maps.addValue("@Importe", detIme.getImporte());
        
        jdbcCall.execute(maps);
        
    }
    
    public List<Ime_III> listar(int idIme){
        
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_IME_III_Listar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdIme", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlReturnResultSet("#imeiiiRowMapper", new Ime_IIIRowMapper()));

        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdIme", idIme);
        
        Map<String, Object> resultado = jdbcCall.execute(maps);
        
        return (List<Ime_III>) resultado.get("#imeiiiRowMapper");
    }
    
    public class Ime_IIIRowMapper implements RowMapper<Ime_III>{

        @Override
        public Ime_III mapRow(ResultSet rs, int i) throws SQLException {
            Ime_III ime_iii = new Ime_III();
            
            ime_iii.setId(rs.getInt("IdImeIII"));
            ime_iii.setIdIme(rs.getInt("IdIme"));
            ime_iii.setFecha(rs.getTimestamp("Fecha"));
            ime_iii.setPartida(rs.getString("Partida"));
            ime_iii.setDocFuente(rs.getString("DocFuente"));
            ime_iii.setDetalleGasto(rs.getString("Detalle"));
            ime_iii.setImporte(UtilDao.getBigDecimalFromNull(rs, "importe"));
            
            return ime_iii;
        }
    
    }
}
