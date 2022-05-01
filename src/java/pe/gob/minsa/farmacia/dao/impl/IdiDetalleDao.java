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
import pe.gob.minsa.farmacia.domain.dto.IdiDetalleDto;

public class IdiDetalleDao {

    @Autowired
    DataSource dataSource;

    public List<IdiDetalleDto> listarPorIdIdi(int idIdi) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("Far_IDI_DET_ListarPorIdi");

            jdbcCall.addDeclaredParameter(new SqlParameter("@IdIDI", java.sql.Types.INTEGER));

            jdbcCall.declareParameters(new SqlReturnResultSet("#idiDetalleRowMapper", new IdiDetalleDtoRowMapper()));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@IdIDI", idIdi);

            Map<String, Object> res = jdbcCall.execute(maps);
            return (List<IdiDetalleDto>) res.get("#idiDetalleRowMapper");
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    public List<IdiDetalleDto> listarPorFechas(Timestamp fechaDesde, Timestamp fechaHasta, int idTipoSuministro, int idTipoProceso){
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("Far_IDI_DET_Consulta");

            jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.DATE));
            jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.DATE));
            
            jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));
            jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));

            jdbcCall.declareParameters(new SqlReturnResultSet("#idiDetalleRowMapper", new IdiDetalleDtoRowMapper()));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@FechaDesde", fechaDesde);
            maps.addValue("@FechaHasta", fechaHasta);
            maps.addValue("@IdTipoSuministro", idTipoSuministro);
            maps.addValue("@IdTipoProceso", idTipoProceso);

            Map<String, Object> res = jdbcCall.execute(maps);
            return (List<IdiDetalleDto>) res.get("#idiDetalleRowMapper");
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void procesar(int idPeriodo, int idPeriodoAnt/**/, int idTipoSuministro, int idTipoProceso) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_IDI_Det_Proceso");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodoAnterior", java.sql.Types.INTEGER));
        /**/jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoSuministro", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdPeriodoAnterior", idPeriodoAnt);
        /**/maps.addValue("@IdTipoSuministro", idTipoSuministro);
        maps.addValue("@IdTipoProceso", idTipoProceso);


        jdbcCall.execute(maps);
    }
    
    public class IdiDetalleDtoRowMapper implements RowMapper<IdiDetalleDto> {

        @Override
        public IdiDetalleDto mapRow(ResultSet rs, int i) throws SQLException {
            IdiDetalleDto idiDetalleDto = new IdiDetalleDto();
            
            idiDetalleDto.setIdIdiDetalle(rs.getInt("IdIDI_Detalle"));
            idiDetalleDto.setIdIdi(rs.getInt("IdIDI"));
            idiDetalleDto.setIdProducto(rs.getInt("IdProducto"));
            idiDetalleDto.setTipoProducto(rs.getString("NombreTipoProducto"));
            idiDetalleDto.setDescripcionProducto(rs.getString("Descripcion"));
            idiDetalleDto.setFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            idiDetalleDto.setPrecioOperacion(rs.getBigDecimal("PrecioOperacion"));
            idiDetalleDto.setSaldoAnterior(rs.getBigDecimal("SaldoAnterior"));
            idiDetalleDto.setIngresos(rs.getBigDecimal("Ingresos"));
            idiDetalleDto.setReIngresos(rs.getBigDecimal("Reingresos"));
            idiDetalleDto.setDistribucion(rs.getBigDecimal("Distribucion"));
            idiDetalleDto.setTransferencia(rs.getBigDecimal("Transferecia"));
            idiDetalleDto.setVencido(rs.getBigDecimal("Vencido"));
            idiDetalleDto.setMerma(rs.getBigDecimal("Merma"));
            idiDetalleDto.setVenta(rs.getBigDecimal("Venta"));
            idiDetalleDto.setExoneracion(rs.getBigDecimal("Exoneracion"));
            idiDetalleDto.setVencimiento(rs.getTimestamp("Vencimiento"));
            idiDetalleDto.setActivo(rs.getInt("Activo"));
            
            return idiDetalleDto;
        }
        
     }
}
