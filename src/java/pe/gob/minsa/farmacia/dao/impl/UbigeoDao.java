package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.TipoUbigeoDaoManager;
import pe.gob.minsa.farmacia.domain.Ubigeo;

public class UbigeoDao implements TipoUbigeoDaoManager{
    
    @Autowired
    DataSource dataSource;
    
    @Override
    public List<Ubigeo> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Ubigeo> tiposUbigeo = jdbcTemplate.query("{call Far_Ubigeo_Listar}", new UbigeoRowMapper());

        return tiposUbigeo;
    }
    
    @Override
    public List<Ubigeo> listarDepartamentos() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Ubigeo> tiposUbigeo = jdbcTemplate.query("{call Far_Ubigeo_ListarDepartamento}", new UbigeoRowMapper());
        
        return tiposUbigeo;
    }
    
    @Override
    public List<Ubigeo> listarPronvincias(String idDepartamento) {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Ubigeo> tiposUbigeo = jdbcTemplate.query("{call Far_Ubigeo_ListarProvincia(?)}", new Object[]{ idDepartamento } , new UbigeoRowMapper());
        
        return tiposUbigeo;
    }

    @Override
    public List<Ubigeo> listarDistritos(String idProvincia) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Ubigeo> tiposUbigeo = jdbcTemplate.query("{call Far_Ubigeo_ListarDistrito(?)}", new Object[]{ idProvincia } , new UbigeoRowMapper());
        
        return tiposUbigeo;
    }

    @Override
    public Ubigeo obtenerPorId(String id) {
        List<Ubigeo> tiposUbigeos = listar();
        Integer indice = null;

        for (int i = 0; i <= tiposUbigeos.size() - 1; ++i) {
            if (tiposUbigeos.get(i).getIdUbigeo().equalsIgnoreCase(id)) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return tiposUbigeos.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(Ubigeo ubigeo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Ubigeo_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUbigeo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUbigeo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUbigeo", ubigeo.getIdUbigeo());
        maps.addValue("@NombreUbigeo", ubigeo.getNombreUbigeo());
        maps.addValue("@Activo", ubigeo.getActivo());
        maps.addValue("@UsuarioCreacion", ubigeo.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(Ubigeo ubigeo) {
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Ubigeo_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUbigeo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUbigeo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUbigeo", ubigeo.getIdUbigeo());
        maps.addValue("@NombreUbigeo", ubigeo.getNombreUbigeo());
        maps.addValue("@Activo", ubigeo.getActivo());
        maps.addValue("@UsuarioModificacion", ubigeo.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(String id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Ubigeo_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUbigeo", java.sql.Types.VARCHAR));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUbigeo", id);

        jdbcCall.execute(maps);        
    }   
    
    public class UbigeoRowMapper implements RowMapper<Ubigeo> {

        @Override
        public Ubigeo mapRow(ResultSet rs, int i) throws SQLException {

            Ubigeo ubigeo = new Ubigeo();

            ubigeo.setIdUbigeo(rs.getString("IdUbigeo"));
            ubigeo.setNombreUbigeo(rs.getString("NombreUbigeo"));
            ubigeo.setActivo(rs.getInt("Activo"));

            return ubigeo;
        }
    }
    
}
