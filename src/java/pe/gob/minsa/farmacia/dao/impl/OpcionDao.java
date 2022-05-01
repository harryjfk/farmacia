package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.Opcion;

public class OpcionDao {

    @Autowired
    DataSource dataSource;

    public List<Opcion> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Opcion> opciones = jdbcTemplate.query("{call Far_Opcion_Listar}", new OpcionRowMapper());

        return opciones;
    }

    public List<Opcion> listarParaSession(int idUsuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Opcion_ListarParaSession");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmenu", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#opcionRowMapper", new OpcionRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuario", idUsuario);
        maps.addValue("@IdSubmenu", 0);

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<Opcion>) res.get("#opcionRowMapper");
    }

    public List<Opcion> listarParaSession(int idUsuario, int idSubmenu) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Opcion_ListarParaSession");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmenu", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#opcionRowMapper", new OpcionRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuario", idUsuario);
        maps.addValue("@IdSubmenu", idSubmenu);

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<Opcion>) res.get("#opcionRowMapper");
    }

    public List<Opcion> listarPorSubmenu(int idSubmenu) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Opcion> opciones = jdbcTemplate.query("{call Far_Opcion_ListarPorSubmenu(?)}", new Object[]{idSubmenu}, new OpcionRowMapper());

        return opciones;
    }

    public Opcion obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Opcion opcion = jdbcTemplate.queryForObject("{call Far_Opcion_ListarPorId(?)}", new OpcionRowMapper());
            return opcion;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void actualizar(Opcion opcion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Opcion_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdOpcion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@AppOpcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreOpcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdOpcion", opcion.getIdOpcion());
        maps.addValue("@AppOpcion", opcion.getAppOpcion());
        maps.addValue("@NombreOpcion", opcion.getNombreOpcion());
        maps.addValue("@IdSubmenu", opcion.getIdSubmenu());
        maps.addValue("@Activo", opcion.getActivo());
        maps.addValue("@UsuarioModificacion", opcion.getActivo());

        jdbcCall.execute(maps);
    }

    public void cambiarOrden(int id, boolean subida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class OpcionRowMapper implements RowMapper<Opcion> {

        @Override
        public Opcion mapRow(ResultSet rs, int i) throws SQLException {

            Opcion opcion = new Opcion();

            opcion.setIdOpcion(rs.getInt("IdOpcion"));
            opcion.setNombreOpcion(rs.getString("NombreOpcion"));
            opcion.setAppOpcion(rs.getString("AppOpcion"));
            opcion.setIdSubmenu(rs.getInt("IdSubmenu"));
            opcion.setActivo(rs.getInt("Activo"));

            return opcion;
        }
    }
}
