package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.dto.IciDetalleDto;
import pe.gob.minsa.farmacia.util.UtilDao;

public class IciDetalleDao {

    @Autowired
    DataSource dataSource;

    public List<IciDetalleDto> listarPorIdIci(int idIci) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("Far_ICI_DET_ListarPorIci");

            jdbcCall.addDeclaredParameter(new SqlParameter("@IdICI", java.sql.Types.INTEGER));

            jdbcCall.declareParameters(new SqlReturnResultSet("#iciDetalleRowMapper", new IciDetalleDtoRowMapper()));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@IdICI", idIci);

            Map<String, Object> res = jdbcCall.execute(maps);
            return (List<IciDetalleDto>) res.get("#iciDetalleRowMapper");
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

     public List<IciDetalleDto> listarPorFechas(Timestamp fechaDesde, Timestamp fechaHasta, int idAlmacen, int idTipoSuministro) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("Far_ICI_DET_Consulta");
            
            jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
            jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));
            jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.DATE));
            jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.DATE));

            jdbcCall.declareParameters(new SqlReturnResultSet("#iciDetalleRowMapper", new IciDetalleDtoRowMapper()));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@IdAlmacen", idAlmacen);
            maps.addValue("@IdTipoSuministro", idTipoSuministro);
            maps.addValue("@FechaDesde", fechaDesde);
            maps.addValue("@FechaHasta", fechaHasta);

            Map<String, Object> res = jdbcCall.execute(maps);
            return (List<IciDetalleDto>) res.get("#iciDetalleRowMapper");
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    public void procesar(int idAlmacen, int idPeriodo, int idPeriodoAnt/**/, int idTipoSuministro) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_ICI_DET_Proceso");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodoAnterior", java.sql.Types.INTEGER));
        /**/jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdPeriodoAnterior", idPeriodoAnt);
        /**/maps.addValue("@IdTipoSuministro", idTipoSuministro);

        jdbcCall.execute(maps);
    }

    public class IciDetalleDtoRowMapper implements RowMapper<IciDetalleDto> {

        @Override
        public IciDetalleDto mapRow(ResultSet rs, int i) throws SQLException {
            IciDetalleDto iciDetalle = new IciDetalleDto();

            iciDetalle.setIdIciDetalle(rs.getInt("IdICI_Detalle"));
            iciDetalle.setIdIci(rs.getInt("IdICI"));
            iciDetalle.setIdProducto(rs.getInt("IdProducto"));
            iciDetalle.setDescripcionProducto(rs.getString("Descripcion"));
            iciDetalle.setNombreUnidadMedida(rs.getString("NombreUnidadMedida"));
            iciDetalle.setPrecioOperacion(UtilDao.getBigDecimalFromNull(rs, "PrecioOperacion"));
            iciDetalle.setSaldoAnterior(UtilDao.getBigDecimalFromNull(rs, "SaldoAnterior"));
            iciDetalle.setIngresos(UtilDao.getBigDecimalFromNull(rs, "Ingresos"));
            iciDetalle.setVentas(UtilDao.getBigDecimalFromNull(rs, "Ventas"));
            iciDetalle.setSis(UtilDao.getBigDecimalFromNull(rs, "SIS"));
            iciDetalle.setIntervSanit(UtilDao.getBigDecimalFromNull(rs,"IntervSanit"));
            iciDetalle.setFactPerd(UtilDao.getBigDecimalFromNull(rs, "FactPerd"));
            iciDetalle.setDefensaNacional(UtilDao.getBigDecimalFromNull(rs, "DefensaNacional"));
            iciDetalle.setExoneracion(UtilDao.getBigDecimalFromNull(rs, "Exoneracion"));
            iciDetalle.setSoat(UtilDao.getBigDecimalFromNull(rs, "SOAT"));
            iciDetalle.setCreditoHospitalario(UtilDao.getBigDecimalFromNull(rs, "CreditoHospitalario"));
            iciDetalle.setOtrosConvenios(UtilDao.getBigDecimalFromNull(rs, "OtrosConvenios"));
            iciDetalle.setDevolucion(UtilDao.getBigDecimalFromNull(rs, "Devolucion"));
            iciDetalle.setVencido(UtilDao.getBigDecimalFromNull(rs, "Vencido"));
            iciDetalle.setDevolucionQuiebreStock(UtilDao.getBigDecimalFromNull(rs, "QuiebreStock"));
            iciDetalle.setMerma(UtilDao.getBigDecimalFromNull(rs, "Merma"));
            iciDetalle.setSaldoFinal(UtilDao.getBigDecimalFromNull(rs, "SaldoFinal"));
            iciDetalle.setVencimiento(rs.getTimestamp("Vencimiento"));
            iciDetalle.setRequerimiento(UtilDao.getBigDecimalFromNull(rs, "Requerimiento"));
            iciDetalle.setActivo(rs.getInt("Activo"));

            return iciDetalle;
        }
    }
}
