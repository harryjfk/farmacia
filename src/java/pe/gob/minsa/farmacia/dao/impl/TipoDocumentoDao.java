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
import pe.gob.minsa.farmacia.domain.TipoDocumento;

public class TipoDocumentoDao implements DaoManager<TipoDocumento> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoDocumento> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoDocumento> tiposDocumentos = jdbcTemplate.query("{call Far_Tipo_Documento_Listar}", new TipoDocumentoRowMapper());

        return tiposDocumentos;
    }

    @Override
    public TipoDocumento obtenerPorId(int id) {
        List<TipoDocumento> tiposDocumentos = listar();
        Integer indice = null;

        for (int i = 0; i <= tiposDocumentos.size() - 1; ++i) {
            if (tiposDocumentos.get(i).getIdTipoDocumento() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return tiposDocumentos.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(TipoDocumento tipoDocumento) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoDocumento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoDocumento", tipoDocumento.getNombreTipoDocumento());
        maps.addValue("@Activo", tipoDocumento.getActivo());
        maps.addValue("@UsuarioCreacion", tipoDocumento.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(TipoDocumento tipoDocumento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoDocumento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoDocumento", tipoDocumento.getIdTipoDocumento());
        maps.addValue("@NombreTipoDocumento", tipoDocumento.getNombreTipoDocumento());
        maps.addValue("@Activo", tipoDocumento.getActivo());
        maps.addValue("@UsuarioModificacion", tipoDocumento.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }
    
    public boolean esUsado(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_EsUsado");            
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumento", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoDocumento", id);
        
        Map<String, Object> resultMap = jdbcCall.execute(maps);        
        return ((Boolean) resultMap.get("@EsUsado"));
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Documento_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumento", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoDocumento", id);

        jdbcCall.execute(maps);
    }

    public class TipoDocumentoRowMapper implements RowMapper<TipoDocumento> {

        @Override
        public TipoDocumento mapRow(ResultSet rs, int i) throws SQLException {

            TipoDocumento tipoDocumento = new TipoDocumento();

            tipoDocumento.setIdTipoDocumento(rs.getInt("IdTipoDocumento"));
            tipoDocumento.setNombreTipoDocumento(rs.getString("NombreTipoDocumento"));
            tipoDocumento.setActivo(rs.getInt("Activo"));

            return tipoDocumento;
        }
    }

}
