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
import pe.gob.minsa.farmacia.domain.TipoDocumentoMov;

public class TipoDocumentoMovDao implements DaoManager<TipoDocumentoMov> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoDocumentoMov> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoDocumentoMov> tiposDocumentoMov = jdbcTemplate.query("{call Far_Tipo_Documento_Mov_Listar}", new TipoDocumentoMovRowMapper());

        return tiposDocumentoMov;
    }

    @Override
    public TipoDocumentoMov obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TipoDocumentoMov tipoDocumentoMov = jdbcTemplate.queryForObject("{call Far_Tipo_Documento_Mov_ListarPorId(?)}", new Object[]{id}, new TipoDocumentoMovRowMapper());

        return tipoDocumentoMov;
    }

    @Override
    public void insertar(TipoDocumentoMov tipoDocumentoMov) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_Mov_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoDocumentoMov", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoDocumentoMov", tipoDocumentoMov.getNombreTipoDocumentoMov());
        maps.addValue("@Activo", tipoDocumentoMov.getActivo());
        maps.addValue("@UsuarioCreacion", tipoDocumentoMov.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(TipoDocumentoMov tipoDocumentoMov) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_Mov_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumentoMov", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoDocumentoMov", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoDocumentoMov", tipoDocumentoMov.getIdTipoDocumentoMov());
        maps.addValue("@NombreTipoDocumentoMov", tipoDocumentoMov.getNombreTipoDocumentoMov());
        maps.addValue("@Activo", tipoDocumentoMov.getActivo());
        maps.addValue("@UsuarioModificacion", tipoDocumentoMov.getUsuarioModificacion());
        
        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_Mov_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumentoMov", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoDocumentoMov", id);

        jdbcCall.execute(maps);
    }

    public class TipoDocumentoMovRowMapper implements RowMapper<TipoDocumentoMov> {

        @Override
        public TipoDocumentoMov mapRow(ResultSet rs, int i) throws SQLException {
            TipoDocumentoMov tipoDocumentoMov = new TipoDocumentoMov();

            tipoDocumentoMov.setIdTipoDocumentoMov(rs.getInt("IdTipoDocumentoMov"));
            tipoDocumentoMov.setNombreTipoDocumentoMov(rs.getString("NombreTipoDocumentoMov"));
            tipoDocumentoMov.setActivo(rs.getInt("Activo"));

            return tipoDocumentoMov;
        }
    }
}
