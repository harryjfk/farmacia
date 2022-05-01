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
import pe.gob.minsa.farmacia.domain.TipoProceso;

import pe.gob.minsa.farmacia.domain.TipoSuministro;

public class TipoSuministroDao implements DaoManager<TipoSuministro>{

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoSuministro> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoSuministro> tiposSuministros = jdbcTemplate.query("{call Far_Tipo_Suministro_Listar}", new TipoSuministroDao.TipoSuministroRowMapper());

        return tiposSuministros;
    }
    
    public boolean existe(String nombreTipoSuministro) {
        return this.existe(nombreTipoSuministro, 0);
    }
    
    
    public boolean existe(String nombreTipoSuministro, int idTipoSuministroPertenece) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Suministro_Existe");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoSuministro", idTipoSuministroPertenece);
        maps.addValue("@Descripcion", nombreTipoSuministro);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }
    
    @Override
    public TipoSuministro obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            TipoSuministro tipoSuministro = jdbcTemplate.queryForObject("{call Far_Tipo_Suministro_ListarPorId(?)}", new Object[]{id}, new TipoSuministroDao.TipoSuministroRowMapper());
            return tipoSuministro;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void insertar(TipoSuministro tipoSuministro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Suministro_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoProceso", tipoSuministro.getDescripcion());
        maps.addValue("@Activo", tipoSuministro.getActivo());
        maps.addValue("@UsuarioCreacion", tipoSuministro.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(TipoSuministro tipoSuministro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Suministro_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProceso", tipoSuministro.getIdTipoSuministro());
        maps.addValue("@NombreTipoProceso", tipoSuministro.getDescripcion());
        maps.addValue("@Activo", tipoSuministro.getActivo());
        maps.addValue("@UsuarioModificacion", tipoSuministro.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Suministro_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoSuministro", id);

        jdbcCall.execute(maps);
    }

    public boolean esUsado(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class TipoSuministroRowMapper implements RowMapper<TipoSuministro> {

        @Override
        public TipoSuministro mapRow(ResultSet rs, int i) throws SQLException {

            TipoSuministro tipoSuministro = new TipoSuministro();

            tipoSuministro.setIdTipoSuministro(rs.getInt("IdTipoSuministro"));
            tipoSuministro.setDescripcion(rs.getString("Descripcion"));
            tipoSuministro.setActivo(rs.getInt("Activo"));

            return tipoSuministro;
        }
    public boolean esUsado(int IdTipoSuministro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Suministro_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoSuministro", IdTipoSuministro);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }
    
    }
}
