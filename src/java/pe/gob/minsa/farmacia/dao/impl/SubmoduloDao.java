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
import pe.gob.minsa.farmacia.dao.NavegacionManager;
import pe.gob.minsa.farmacia.domain.Submodulo;

public class SubmoduloDao implements NavegacionManager<Submodulo>{

    @Autowired
    DataSource dataSource;
    
    @Override
    public List<Submodulo> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Submodulo> submodulos = jdbcTemplate.query("{call Far_Submodulo_Listar}", new SubmoduloRowMapper());
        
        return submodulos;
    }
    
    @Override
    public List<Submodulo> listarParaSession(int idUsuario) {        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Submodulo> submodulos = jdbcTemplate.query("{call Far_Submodulo_ListarParaSession(?)}" , new Object[]{idUsuario}, new SubmoduloRowMapper());

        return submodulos;
    }

    @Override
    public Submodulo obtenerPorId(int id) {
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Submodulo submodulo = jdbcTemplate.queryForObject("{call Far_Submodulo_ListarPorId(?)}" , new Object[]{id}, new SubmoduloRowMapper());
            return submodulo;
        }catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public void actualizar(Submodulo submodulo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Submodulo_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmodulo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreSubmodulo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdModulo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Orden", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSubmodulo", submodulo.getIdSubmodulo());
        maps.addValue("@NombreSubmodulo", submodulo.getNombreSubmodulo());
        maps.addValue("@IdModulo", submodulo.getIdModulo());
        maps.addValue("@Orden", submodulo.getOrden());
        maps.addValue("@Activo", submodulo.getActivo());
        maps.addValue("@UsuarioModificacion", submodulo.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void cambiarOrden(int idSubmodulo, boolean subida) {
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Submodulo_CambiarOrden");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmodulo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Subida", java.sql.Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSubmodulo", idSubmodulo);
        maps.addValue("@Subida", subida);

        jdbcCall.execute(maps);
    }
    
    public class SubmoduloRowMapper implements RowMapper<Submodulo> {

        @Override
        public Submodulo mapRow(ResultSet rs, int i) throws SQLException {

            Submodulo submodulo = new Submodulo();

            submodulo.setIdSubmodulo(rs.getInt("IdSubmodulo"));
            submodulo.setNombreSubmodulo(rs.getString("NombreSubmodulo"));
            submodulo.setIdModulo(rs.getInt("IdModulo"));
            submodulo.setOrden(rs.getInt("Orden"));
            submodulo.setActivo(rs.getInt("Activo"));

            return submodulo;
        }        
    }
}
