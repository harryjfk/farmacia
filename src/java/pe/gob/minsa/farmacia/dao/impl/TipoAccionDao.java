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
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.TipoAccion;

public class TipoAccionDao implements DaoManager<TipoAccion> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoAccion> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoAccion> tiposAcciones = jdbcTemplate.query("{call Far_Tipo_Accion_Listar}", new TipoAccionRowMapper());

        return tiposAcciones;
    }

    @Override
    public TipoAccion obtenerPorId(int id) {
        List<TipoAccion> tiposAcciones = listar();
        Integer indice = null;

        for (int i = 0; i <= tiposAcciones.size() - 1; ++i) {
            if (tiposAcciones.get(i).getIdTipoAccion() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return tiposAcciones.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(TipoAccion tipoAccion) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Accion_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoAccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoAccion", tipoAccion.getNombreTipoAccion());
        maps.addValue("@Activo", tipoAccion.getActivo());
        maps.addValue("@UsuarioCreacion", tipoAccion.getUsuarioCreacion());

        jdbcCall.execute(maps);

    }

    @Override
    public void actualizar(TipoAccion tipoAccion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Accion_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAccion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoAccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoAccion", tipoAccion.getIdTipoAccion());
        maps.addValue("@NombreTipoAccion", tipoAccion.getNombreTipoAccion());
        maps.addValue("@Activo", tipoAccion.getActivo());
        maps.addValue("@UsuarioModificacion", tipoAccion.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Accion_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAccion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoAccion", id);

        jdbcCall.execute(maps);
    }

    public boolean esUsado(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Accion_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAccion", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoAccion", id);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }

    public class TipoAccionRowMapper implements RowMapper<TipoAccion> {

        @Override
        public TipoAccion mapRow(ResultSet rs, int i) throws SQLException {

            TipoAccion tipoAccion = new TipoAccion();

            tipoAccion.setIdTipoAccion(rs.getInt("IdTipoAccion"));
            tipoAccion.setNombreTipoAccion(rs.getString("NombreTipoAccion"));
            tipoAccion.setActivo(rs.getInt("Activo"));

            return tipoAccion;
        }
    }

}
