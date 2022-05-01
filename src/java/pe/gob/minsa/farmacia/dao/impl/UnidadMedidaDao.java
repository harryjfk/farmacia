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
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.UnidadMedida;

public class UnidadMedidaDao implements DaoManager<UnidadMedida> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<UnidadMedida> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<UnidadMedida> unidadesMedida = jdbcTemplate.query("{call Far_Unidad_Medida_Listar}", new UnidadMedidaRowMapper());

        return unidadesMedida;
    }

    @Override
    public UnidadMedida obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UnidadMedida unidadMedida = jdbcTemplate.queryForObject("{call Far_Unidad_Medida_ListarPorId(?)}", new Object[]{id}, new UnidadMedidaRowMapper());

        return unidadMedida;
    }

    @Override
    public void insertar(UnidadMedida unidadMedida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Unidad_Medida_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUnidadMedida", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreUnidadMedida", unidadMedida.getNombreUnidadMedida());
        maps.addValue("@Abreviatura", unidadMedida.getAbreviatura());
        maps.addValue("@Activo", unidadMedida.getActivo());
        maps.addValue("@UsuarioCreacion", unidadMedida.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(UnidadMedida unidadMedida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Unidad_Medida_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUnidadMedida", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUnidadMedida", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUnidadMedida", unidadMedida.getIdUnidadMedida());
        maps.addValue("@NombreUnidadMedida", unidadMedida.getNombreUnidadMedida());
        maps.addValue("@Abreviatura", unidadMedida.getAbreviatura());
        maps.addValue("@Activo", unidadMedida.getActivo());
        maps.addValue("@UsuarioModificacion", unidadMedida.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Unidad_Medida_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUnidadMedida", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUnidadMedida", id);

        jdbcCall.execute(maps);
    }

    public class UnidadMedidaRowMapper implements RowMapper<UnidadMedida> {

        @Override
        public UnidadMedida mapRow(ResultSet rs, int i) throws SQLException {
            UnidadMedida unidadMedida = new UnidadMedida();

            unidadMedida.setIdUnidadMedida(rs.getInt("IdUnidadMedida"));
            unidadMedida.setNombreUnidadMedida(rs.getString("NombreUnidadMedida"));
            unidadMedida.setAbreviatura(rs.getString("Abreviatura"));
            unidadMedida.setActivo(rs.getInt("Activo"));

            return unidadMedida;
        }
    }
}
