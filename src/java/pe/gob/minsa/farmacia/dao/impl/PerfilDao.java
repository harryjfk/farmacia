package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Perfil;

public class PerfilDao implements DaoManager<Perfil> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Perfil> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Perfil> perfiles = jdbcTemplate.query("{call Far_Perfil_Listar}", new PerfilRowMapper());

        return perfiles;
    }

    @Override
    public Perfil obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Perfil perfil = jdbcTemplate.queryForObject("{call Far_Perfil_ListarPorId(?)}", new Object[]{id}, new PerfilRowMapper());
            return perfil;
        } catch (EmptyResultDataAccessException ex) {
            return new Perfil();
        }
    }

    @Override
    public void insertar(Perfil perfil) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombrePerfil", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombrePerfil", perfil.getNombrePerfil());
        maps.addValue("@Activo", perfil.getActivo());
        maps.addValue("@UsuarioCreacion", perfil.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(Perfil perfil) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombrePerfil", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPerfil", perfil.getIdPerfil());
        maps.addValue("@NombrePerfil", perfil.getNombrePerfil());
        maps.addValue("@Activo", perfil.getActivo());
        maps.addValue("@UsuarioModificacion", perfil.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPerfil", id);

        jdbcCall.execute(maps);
    }
    
    public boolean esUsado(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPerfil", id);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }

    public class PerfilRowMapper implements RowMapper<Perfil> {

        @Override
        public Perfil mapRow(ResultSet rs, int i) throws SQLException {

            Perfil perfil = new Perfil();

            perfil.setIdPerfil(rs.getInt("IdPerfil"));
            perfil.setNombrePerfil(rs.getString("NombrePerfil"));
            perfil.setActivo(rs.getInt("Activo"));

            return perfil;
        }
    }

}
