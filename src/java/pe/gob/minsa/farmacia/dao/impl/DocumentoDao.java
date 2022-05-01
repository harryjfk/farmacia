package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import pe.gob.minsa.farmacia.domain.Documento;
import pe.gob.minsa.farmacia.domain.dto.DocumentoComp;

public class DocumentoDao implements DaoManager<Documento> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Documento> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Documento> documentos = jdbcTemplate.query("{call Far_Documento_Listar}", new DocumentoRowMapper());

        return documentos;
    }
    
    public List<DocumentoComp> listarComp(int idUsuario){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<DocumentoComp> documentos = jdbcTemplate.query("{call Far_Documento_Listar_Comp (?)}", new Object[]{ idUsuario }, new DocumentoCompRowMapper());

        return documentos;
    }

    @Override
    public Documento obtenerPorId(int id) {
        List<Documento> documentos = listar();
        Integer indice = null;

        for (int i = 0; i <= documentos.size() - 1; ++i) {
            if (documentos.get(i).getIdDocumento() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return documentos.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(Documento documento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeracionInterna", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDocumento", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaSalida", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAccion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NroDocumento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Asunto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Remitente", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Destino", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeracionDireccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Observacion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Despacho", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDespacho", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Extension", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@IdDocumento", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NumeracionInterna", documento.getNumeracionInterna());
        maps.addValue("@IdUsuario", documento.getIdUsuario());
        maps.addValue("@FechaDocumento", documento.getFechaDocumento());
        maps.addValue("@FechaSalida", documento.getFechaSalida());
        maps.addValue("@IdTipoAccion", documento.getIdTipoAccion());
        maps.addValue("@IdTipoDocumento", documento.getIdTipoDocumento());
        maps.addValue("@NroDocumento", documento.getNroDocumento());
        maps.addValue("@Asunto", documento.getAsunto());
        maps.addValue("@Remitente", documento.getRemitente());
        maps.addValue("@Destino", documento.getDestino());
        maps.addValue("@NumeracionDireccion", documento.getNumeracionDireccion());
        maps.addValue("@Observacion", documento.getObservacion());
        maps.addValue("@Despacho", documento.getDespacho());
        maps.addValue("@FechaDespacho", documento.getFechaDespacho());
        maps.addValue("@Extension", documento.getExtension());
        maps.addValue("@Activo", documento.getActivo());
        maps.addValue("@UsuarioCreacion", documento.getUsuarioCreacion());

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        documento.setIdDocumento((Integer) resultMap.get("@IdDocumento"));
    }

    @Override
    public void actualizar(Documento documento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeracionInterna", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDocumento", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaSalida", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAccion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NroDocumento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Asunto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Remitente", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Destino", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeracionDireccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Observacion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Despacho", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDespacho", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Extension", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdDocumento", documento.getIdDocumento());
        maps.addValue("@NumeracionInterna", documento.getNumeracionInterna());
        maps.addValue("@IdUsuario", documento.getIdUsuario());
        maps.addValue("@FechaDocumento", documento.getFechaDocumento());
        maps.addValue("@FechaSalida", documento.getFechaSalida());
        maps.addValue("@IdTipoAccion", documento.getIdTipoAccion());
        maps.addValue("@IdTipoDocumento", documento.getIdTipoDocumento());
        maps.addValue("@NroDocumento", documento.getNroDocumento());
        maps.addValue("@Asunto", documento.getAsunto());
        maps.addValue("@Remitente", documento.getRemitente());
        maps.addValue("@Destino", documento.getDestino());
        maps.addValue("@NumeracionDireccion", documento.getNumeracionDireccion());
        maps.addValue("@Observacion", documento.getObservacion());
        maps.addValue("@Despacho", documento.getDespacho());
        maps.addValue("@FechaDespacho", documento.getFechaDespacho());
        maps.addValue("@Extension", documento.getExtension());
        maps.addValue("@Activo", documento.getActivo());
        maps.addValue("@UsuarioModificacion", documento.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumento", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdDocumento", id);

        jdbcCall.execute(maps);
    }

    public class DocumentoRowMapper implements RowMapper<Documento> {

        @Override
        public Documento mapRow(ResultSet rs, int i) throws SQLException {

            Documento documento = new Documento();

            documento.setIdDocumento(rs.getInt("IdDocumento"));
            documento.setNumeracionInterna(rs.getString("NumeracionInterna"));
            documento.setIdUsuario(rs.getInt("IdUsuario"));
            documento.setFechaDocumento(rs.getTimestamp("FechaDocumento"));
            documento.setFechaSalida(rs.getTimestamp("FechaSalida"));
            documento.setIdTipoDocumento(rs.getInt("IdTipoDocumento"));
            documento.setIdTipoAccion(rs.getInt("IdTipoAccion"));
            documento.setNroDocumento(rs.getString("NroDocumento"));
            documento.setAsunto(rs.getString("Asunto"));
            documento.setRemitente(rs.getString("Remitente"));
            documento.setDestino(rs.getString("Destino"));
            documento.setNumeracionDireccion(rs.getString("NumeracionDireccion"));
            documento.setObservacion(rs.getString("Observacion"));
            documento.setDespacho(rs.getInt("Despacho"));
            documento.setFechaDespacho(rs.getTimestamp("FechaDespacho"));
            documento.setExtension(rs.getString("Extension"));
            documento.setActivo(rs.getInt("Activo"));

            return documento;
        }
    }
    
    public class DocumentoCompRowMapper implements RowMapper<DocumentoComp> {

        @Override
        public DocumentoComp mapRow(ResultSet rs, int i) throws SQLException {

            DocumentoComp documento = new DocumentoComp();
            
            documento.setIdDocumento(rs.getInt("IdDocumento"));
            documento.setNumeracionInterna(rs.getString("NumeracionInterna"));
            documento.setIdUsuario(rs.getInt("IdUsuario"));
            documento.setFechaDocumento(rs.getTimestamp("FechaDocumento"));
            documento.setFechaSalida(rs.getTimestamp("FechaSalida"));
            documento.setIdTipoDocumento(rs.getInt("IdTipoDocumento"));
            documento.setNombreTipoDocumento(rs.getString("NombreTipoDocumento"));
            documento.setIdTipoAccion(rs.getInt("IdTipoAccion"));
            documento.setNombreTipoAccion(rs.getString("NombreTipoAccion"));
            documento.setNroDocumento(rs.getString("NroDocumento"));
            documento.setAsunto(rs.getString("Asunto"));
            documento.setDestino(rs.getString("Destino"));
            documento.setNumeracionDireccion(rs.getString("NumeracionDireccion"));
            documento.setDespacho(rs.getInt("Despacho"));
            documento.setFechaDespacho(rs.getTimestamp("FechaDespacho"));
            documento.setExtension(rs.getString("Extension"));
            documento.setActivo(rs.getInt("Activo"));
            documento.setObservacion(rs.getString("Observacion"));
            documento.setRemitente(rs.getString("Remitente"));

            return documento;
        }
    }

}
