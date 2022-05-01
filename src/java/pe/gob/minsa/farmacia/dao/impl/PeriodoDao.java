package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.Periodo;

public class PeriodoDao{

    @Autowired
    DataSource dataSource;

    public List<Periodo> listar() {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Periodo> periodos = jdbcTemplate.query("{call Far_Periodo_Listar}", new PeriodoRowMapper());

        return periodos;
    }
    
    public List<Periodo> listarPorAnio(int anio) {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Periodo> periodos = jdbcTemplate.query("{call Far_Periodo_ListarPorAnio(?)}", new Object[]{anio}, new PeriodoRowMapper());

        return periodos;
    }
    
    public List<Integer> AperturarAlmacen(int idPeriodoCerrar){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Integer> idsAlmacenes = jdbcTemplate.queryForList("{call Far_Periodo_AperturarAlmacen(?)}", new Object[]{idPeriodoCerrar}, Integer.class);
        return idsAlmacenes;
    }
    
    public Periodo obtenerPeriodoActivo() {        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Periodo periodo = jdbcTemplate.queryForObject("{call Far_Periodo_Activo}", new PeriodoRowMapper());

        return periodo;
    }    
    
    public void insertar(Periodo periodo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Periodo_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", periodo.getIdPeriodo());
        maps.addValue("@Activo", periodo.getActivo());
        maps.addValue("@UsuarioCreacion", periodo.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }
    
    public void cambiarEstado(Periodo periodo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Periodo_CambiarEstado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", periodo.getIdPeriodo());        
        maps.addValue("@UsuarioModificacion", periodo.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }
    
    public boolean Existe(int idPeriodo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Periodo_Existe");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }
    
    public class PeriodoRowMapper implements RowMapper<Periodo> {

        @Override
        public Periodo mapRow(ResultSet rs, int i) throws SQLException {

            Periodo periodo = new Periodo();

            periodo.setIdPeriodo(rs.getInt("IdPeriodo"));            
            periodo.setActivo(rs.getInt("Activo"));

            return periodo;
        }
    }
}
