package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Parametro;

public class ParametroDao implements DaoManager<Parametro> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Parametro> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Parametro> parametros = jdbcTemplate.query("{call Far_Parametro_Listar}", new ParametroRowMapper());

        return parametros;
    }
    
    public List<Parametro> listarSinDependencia() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Parametro> parametros = jdbcTemplate.query("{call Far_Parametro_ListarSinDependencia}", new ParametroRowMapper());

        return parametros;
    }
    
    public List<Parametro> listarDependencia() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Parametro> parametros = jdbcTemplate.query("{call Far_Parametro_ListarDependencia}", new ParametroRowMapper());

        return parametros;
    }

    @Override
    public Parametro obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        Parametro parametro = null;
        try{
         parametro = jdbcTemplate.queryForObject("{call Far_Parametro_ListarPorId(?)}", new Object[]{ id }, new ParametroRowMapper());
        }
        catch(EmptyResultDataAccessException ex){            
        }
        
        return parametro;
    }
    
    public Parametro obtenerPorNombre(String nombreParametro){        
        
        if(nombreParametro == null){
            return null;
        }else{
            nombreParametro = nombreParametro.trim();
        }
        
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Parametro parametro = jdbcTemplate.queryForObject("{call Far_Parametro_PorNombre(?)}", new Object[]{ nombreParametro }, new ParametroRowMapper());
            return parametro;
        }
        catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public void insertar(Parametro parametro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Parametro_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreParametro", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DescripcionParametro", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Valor", java.sql.Types.VARCHAR));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreParametro", parametro.getNombreParametro());
        maps.addValue("@DescripcionParametro", parametro.getDescripcionParametro());
        maps.addValue("@Valor", parametro.getValor());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(Parametro parametro) {
     
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Parametro_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdParametro", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreParametro", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DescripcionParametro", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Valor", java.sql.Types.VARCHAR));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdParametro", parametro.getIdParametro());
        maps.addValue("@NombreParametro", parametro.getNombreParametro());
        maps.addValue("@DescripcionParametro", parametro.getDescripcionParametro());
        maps.addValue("@Valor", parametro.getValor());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("No se ha creado");
    }
    
    private static class ParametroRowMapper implements RowMapper<Parametro> {

        @Override
        public Parametro mapRow(ResultSet rs, int i) throws SQLException {

            Parametro parametro = new Parametro();

            parametro.setIdParametro(rs.getInt("IdParametro"));
            parametro.setNombreParametro(rs.getString("NombreParametro"));
            parametro.setDescripcionParametro(rs.getString("DescripcionParametro"));
            parametro.setValor(rs.getString("Valor"));

            return parametro;
        }
    }

}
