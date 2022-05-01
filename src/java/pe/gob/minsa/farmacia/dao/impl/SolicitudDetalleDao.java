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
import pe.gob.minsa.farmacia.domain.BalanceSemestral;
import pe.gob.minsa.farmacia.domain.Historico;
import pe.gob.minsa.farmacia.domain.SolicitudDetalle;

public class SolicitudDetalleDao implements DaoManager<SolicitudDetalle> {

    @Autowired
    DataSource dataSource;

    public List<SolicitudDetalle> listar(int idSolicitud, int tipoMedicamento) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<SolicitudDetalle> solicitudes = jdbcTemplate.query("{call Far_MedicamentoSolicitud_Listar(?,?)}", new Object[]{idSolicitud,tipoMedicamento}, new SolicitudRowMapper());
        
        return solicitudes;
    }
    
    /*public List<BalanceSemestral> listarBalance(int idSolicitud, int tipoMedicamento) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<BalanceSemestral> solicitudes = jdbcTemplate.query("{call Far_BalanceSemestral_Listar(?,?)}", new Object[]{idSolicitud,tipoMedicamento}, new BalanceRowMapper());
        
        return solicitudes;
    }*/
    public List<BalanceSemestral> listarBalance(String fecha) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<BalanceSemestral> solicitudes = jdbcTemplate.query("{call Far_BalanceSemestral_Listar(?)}", new Object[]{fecha}, new BalanceRowMapper());
        
        return solicitudes;
    }
    
    public List<BalanceSemestral> listarConsultaMedicamentos() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<BalanceSemestral> solicitudes = jdbcTemplate.query("{call Far_ConsultaSolMedicamentos_Listar}", new BalanceRowMapper());
        
        return solicitudes;
    }
    
    public List<BalanceSemestral> listarConsultaNoAprobados() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<BalanceSemestral> solicitudes = jdbcTemplate.query("{call Far_ConsultaSolNoAprobados_Listar}", new BalanceRowMapper());
        
        return solicitudes;
    }
    
    public List<Historico> listarConsultaHistoricos(String fecIni, String fecFin) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Historico> solicitudes = jdbcTemplate.query("{call Far_ConsultaSolHistoricos_Listar(?,?)}", new Object[]{fecIni,fecFin}, new HistoricoRowMapper());
        
        return solicitudes;
    }

    public SolicitudDetalle obtenerPorId(int id, int idSolicitud, int tipoMedicamento) {
        List<SolicitudDetalle> solicitud = listar(idSolicitud,tipoMedicamento);
        Integer indice = null;

        for (int i = 0; i <= solicitud.size() - 1; ++i) {
            if (solicitud.get(i).getIdSolicitudDetalle() == id) {
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

    public Integer insertarSolicitudDetalle(SolicitudDetalle solicitud) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_SolicitudDetalle_Insertar");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitud", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DescripcionMedicamento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMedicamento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@IdSolicitudDetalle", Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSolicitud", solicitud.getIdSolicitud());
        maps.addValue("@IdProducto", solicitud.getIdProducto());
        maps.addValue("@DescripcionMedicamento", (solicitud.getDescripcion()!=null)?((!solicitud.getDescripcion().equals(""))?solicitud.getDescripcion().trim().toUpperCase():""):"");
        maps.addValue("@TipoMedicamento", solicitud.getTipoMedicamento());
        maps.addValue("@Activo", solicitud.getActivo());
        maps.addValue("@UsuarioCreacion", solicitud.getUsuarioCreacion());

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Integer) resultMap.get("@IdSolicitudDetalle"));
    }

    @Override
    public void actualizar(SolicitudDetalle solicitud) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_SolicitudDetalle_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitudDetalleProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitud", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@DescripcionMedicamento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMedicamento", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSolicitudDetalleProducto", solicitud.getIdSolicitudDetalle());
        maps.addValue("@IdSolicitud", solicitud.getIdSolicitud());
        maps.addValue("@IdProducto", solicitud.getIdProducto());
        maps.addValue("@DescripcionMedicamento", (solicitud.getDescripcion()!=null)?((!solicitud.getDescripcion().equals(""))?solicitud.getDescripcion().trim().toUpperCase():""):"");
        maps.addValue("@TipoMedicamento", solicitud.getTipoMedicamento());
        maps.addValue("@Activo", solicitud.getActivo());
        maps.addValue("@UsuarioModificacion", solicitud.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }
    
    public void aprobar(SolicitudDetalle solicitud) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_SolicitudDetalle_Aprobar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitudDetalleProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitud", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CondicionAprobado", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@MotivoAprobado", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Extension", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Aprobado", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadAprobada", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSolicitudDetalleProducto", solicitud.getIdSolicitudDetalle());
        maps.addValue("@IdSolicitud", solicitud.getIdSolicitud());
        maps.addValue("@CondicionAprobado", solicitud.getCondicion().trim().toUpperCase());
        maps.addValue("@MotivoAprobado", solicitud.getMotivo().trim().toUpperCase());
        maps.addValue("@Extension", solicitud.getExtension().trim().toLowerCase());
        maps.addValue("@Aprobado", solicitud.getAprobado());
        maps.addValue("@CantidadAprobada", (Integer.valueOf(solicitud.getCantidad())!=null)?solicitud.getCantidad():0);
        maps.addValue("@UsuarioModificacion", solicitud.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_SolicitudDetalle_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSolicitudDetalleProducto", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSolicitudDetalleProducto", id);

        jdbcCall.execute(maps);
    }

    @Override
    public List<SolicitudDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SolicitudDetalle obtenerPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class SolicitudRowMapper implements RowMapper<SolicitudDetalle> {

        @Override
        public SolicitudDetalle mapRow(ResultSet rs, int i) throws SQLException {

            SolicitudDetalle solicitud = new SolicitudDetalle();

            solicitud.setIdSolicitud(rs.getInt("IdSolicitud"));
            solicitud.setIdSolicitudDetalle(rs.getInt("IdSolicitudDetalleProducto"));
            solicitud.setDescripcion(rs.getString("DescripcionMedicamento"));
            solicitud.setIdProducto(rs.getInt("IdProducto"));
            solicitud.setDescripcionProd(rs.getString("Descripcion"));
            solicitud.setActivo(rs.getInt("Activo"));
            solicitud.setDescripcionFarm(rs.getString("NombreFormaFarmaceutica"));
            solicitud.setConcentracion(rs.getString("Concentracion"));
            solicitud.setAprobado(rs.getInt("Aprobado"));
            solicitud.setCantidad(rs.getInt("CantidadAprobada"));
            solicitud.setMotivo(rs.getString("MotivoAprobado"));
            solicitud.setCondicion(rs.getString("CondicionAprobado"));
            solicitud.setExtension(rs.getString("Extension"));

            return solicitud;
        }
    }
    
    public class BalanceRowMapper implements RowMapper<BalanceSemestral> {

        @Override
        public BalanceSemestral mapRow(ResultSet rs, int i) throws SQLException {

            BalanceSemestral solicitud = new BalanceSemestral();

            solicitud.setIdSolicitud(rs.getInt("IdSolicitud"));
            solicitud.setIdSolicitudDetalleProducto(rs.getInt("IdSolicitudDetalleProducto"));
            solicitud.setInstitucion(rs.getString("Institucion"));
            solicitud.setEstablecimiento(rs.getString("Establecimiento"));
            solicitud.setFechaAprobacion(rs.getTimestamp("FechaAprobacion"));
            solicitud.setFecha(rs.getTimestamp("FechaCreacion"));
            solicitud.setConcentracion(rs.getString("Concentracion"));
            solicitud.setNombreFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            solicitud.setDescripcionProducto(rs.getString("Descripcion"));
            solicitud.setDescripcionMedicamento(rs.getString("DescripcionMedicamento"));
            solicitud.setFormaPresentacion(rs.getString("Presentacion"));
            solicitud.setCantidad(rs.getInt("CantidadAprobada"));
            solicitud.setMotivo(rs.getString("MotivoAprobado"));
            solicitud.setCondicion(rs.getString("CondicionAprobado"));
            solicitud.setAprobado(rs.getString("Aprobado"));
            solicitud.setTipoMedicamento(rs.getString("TipoMedicamento"));

            return solicitud;
        }
    }
    
    public class HistoricoRowMapper implements RowMapper<Historico> {

        @Override
        public Historico mapRow(ResultSet rs, int i) throws SQLException {

            Historico solicitud = new Historico();

            solicitud.setFechaAprobacion(rs.getTimestamp("FechaAprobacion"));
            solicitud.setConcentracion(rs.getString("Concentracion"));
            solicitud.setNombreFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            solicitud.setDescripcionProducto(rs.getString("descripcion"));
            solicitud.setFormaPresentacion(rs.getString("Presentacion"));
            solicitud.setAnio(rs.getInt("anio"));
            solicitud.setCantEnero(rs.getInt("enero"));
            solicitud.setCantFebrero(rs.getInt("febrero"));
            solicitud.setCantMarzo(rs.getInt("marzo"));
            solicitud.setCantAbril(rs.getInt("abril"));
            solicitud.setCantMayo(rs.getInt("mayo"));
            solicitud.setCantJunio(rs.getInt("junio"));
            solicitud.setCantJulio(rs.getInt("julio"));
            solicitud.setCantAgosto(rs.getInt("agosto"));
            solicitud.setCantSetiembre(rs.getInt("setiembre"));
            solicitud.setCantOctubre(rs.getInt("octubre"));
            solicitud.setCantNoviembre(rs.getInt("noviembre"));
            solicitud.setCantDiciembre(rs.getInt("diciembre"));

            return solicitud;
        }
    }
    
    @Override
    public void insertar(SolicitudDetalle t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
