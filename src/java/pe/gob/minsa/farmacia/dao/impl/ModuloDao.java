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
import pe.gob.minsa.farmacia.domain.Modulo;

public class ModuloDao implements NavegacionManager<Modulo> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Modulo> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Modulo> modulos = jdbcTemplate.query("{call Far_Modulo_Listar}", new ModuloRowMapper());

        return modulos;
    }

    @Override
    public List<Modulo> listarParaSession(int idUsuario) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Modulo> modulos = jdbcTemplate.query("{call Far_Modulo_ListarParaSession(?)}", new Object[]{idUsuario}, new ModuloDao.ModuloRowMapper());

        return modulos;
    }

    @Override
    public Modulo obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Modulo modulo = jdbcTemplate.queryForObject("{call Far_Modulo_ListarPorId(?)}", new Object[]{id}, new ModuloRowMapper());
            return modulo;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void actualizar(Modulo modulo) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Modulo_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdModulo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreModulo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Orden", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdModulo", modulo.getIdModulo());
        maps.addValue("@NombreModulo", modulo.getNombreModulo());
        maps.addValue("@Orden", modulo.getOrden());
        maps.addValue("@Activo", modulo.getActivo());
        maps.addValue("@UsuarioModificacion", modulo.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void cambiarOrden(int idModulo, boolean subida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Modulo_CambiarOrden");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdModulo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Subida", java.sql.Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdModulo", idModulo);
        maps.addValue("@Subida", subida);

        jdbcCall.execute(maps);
    }

    public class ModuloRowMapper implements RowMapper<Modulo> {

        @Override
        public Modulo mapRow(ResultSet rs, int i) throws SQLException {

            Modulo modulo = new Modulo();

            modulo.setIdModulo(rs.getInt("IdModulo"));
            modulo.setNombreModulo(rs.getString("NombreModulo"));
            modulo.setOrden(rs.getInt("Orden"));
            modulo.setActivo(rs.getInt("Activo"));

            return modulo;
        }
    }
}
