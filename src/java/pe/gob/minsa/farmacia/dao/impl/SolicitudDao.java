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
import pe.gob.minsa.farmacia.domain.Solicitud;

public class SolicitudDao implements DaoManager<Solicitud> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Solicitud> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Solicitud> solicitudes = jdbcTemplate.query("{call Far_Solicitud_Listar}", new SolicitudRowMapper());

        return solicitudes;
    }

    @Override
    public Solicitud obtenerPorId(int id) {
        List<Solicitud> solicitud = listar();
        Integer indice = null;

        for (int i = 0; i <= solicitud.size() - 1; ++i) {
            if (solicitud.get(i).getIdSolicitud() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return solicitud.get(indice);
        } else {
            return null;
        }
    }

    public Integer insertarSolicitud(Solicitud solicitud) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Solicitud_Insertar");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMedico", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Motivo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Justificacion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Institucion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Establecimiento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fecha", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Extension", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@ExisteMedicamento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@IdSolicitud", Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdMedico", solicitud.getIdMedico().trim());
        maps.addValue("@Motivo", (solicitud.getMotivo()!=null)?((!solicitud.getMotivo().equals(""))?solicitud.getMotivo().trim().toUpperCase():""):"");
        maps.addValue("@Justificacion", (solicitud.getJustificacion()!=null)?((!solicitud.getJustificacion().equals(""))?solicitud.getJustificacion().trim().toUpperCase():""):"");
        maps.addValue("@Institucion", (solicitud.getInstitucion()!=null)?((!solicitud.getInstitucion().equals(""))?solicitud.getInstitucion().trim().toUpperCase():""):"");
        maps.addValue("@Establecimiento", (solicitud.getEstablecimiento()!=null)?((!solicitud.getEstablecimiento().equals(""))?solicitud.getEstablecimiento().trim().toUpperCase():""):"");
        maps.addValue("@Fecha", solicitud.getFecha());
        maps.addValue("@Extension", solicitud.getExtension());
        maps.addValue("@Activo", solicitud.getActivo());
        maps.addValue("@ExisteMedicamento", solicitud.getExisteMedicamento());
        maps.addValue("@UsuarioCreacion", solicitud.getUsuarioCreacion());

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Integer) resultMap.get("@IdSolicitud"));
    }

    @Override
    public void actualizar(Solicitud solicitud) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Solicitud_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitud", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMedico", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Motivo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Justificacion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Institucion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Establecimiento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fecha", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Extension", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@ExisteMedicamento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSolicitud", solicitud.getIdSolicitud());
        maps.addValue("@IdMedico", solicitud.getIdMedico().trim());
        maps.addValue("@Motivo", (solicitud.getMotivo()!=null)?((!solicitud.getMotivo().equals(""))?solicitud.getMotivo().trim().toUpperCase():""):"");
        maps.addValue("@Justificacion", (solicitud.getJustificacion()!=null)?((!solicitud.getJustificacion().equals(""))?solicitud.getJustificacion().trim().toUpperCase():""):"");
        maps.addValue("@Institucion", (solicitud.getInstitucion()!=null)?((!solicitud.getInstitucion().equals(""))?solicitud.getInstitucion().trim().toUpperCase():""):"");
        maps.addValue("@Establecimiento", (solicitud.getEstablecimiento()!=null)?((!solicitud.getEstablecimiento().equals(""))?solicitud.getEstablecimiento().trim().toUpperCase():""):"");
        maps.addValue("@Fecha", solicitud.getFecha());
        maps.addValue("@Extension", solicitud.getExtension());
        maps.addValue("@Activo", solicitud.getActivo());
        maps.addValue("@ExisteMedicamento", solicitud.getExisteMedicamento());
        maps.addValue("@UsuarioModificacion", solicitud.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Solicitud_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitud", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSolicitud", id);

        jdbcCall.execute(maps);
    }

    @Override
    public void insertar(Solicitud t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class SolicitudRowMapper implements RowMapper<Solicitud> {

        @Override
        public Solicitud mapRow(ResultSet rs, int i) throws SQLException {

            Solicitud solicitud = new Solicitud();

            solicitud.setIdSolicitud(rs.getInt("IdSolicitud"));
            solicitud.setIdMedico(rs.getString("IdMedico"));
            solicitud.setMedico(rs.getString("Medico"));
            solicitud.setEstablecimiento(rs.getString("Establecimiento"));
            solicitud.setInstitucion(rs.getString("Institucion"));
            solicitud.setFecha(rs.getTimestamp("Fecha"));
            solicitud.setExtension(rs.getString("Extension"));
            solicitud.setActivo(rs.getInt("Activo"));
            solicitud.setMotivo(rs.getString("Motivo"));
            solicitud.setJustificacion(rs.getString("Justificacion"));
            solicitud.setExisteMedicamento(rs.getInt("ExisteMedicamento"));

            return solicitud;
        }
    }

}
