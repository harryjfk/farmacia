package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.dto.DetallePorLoteDto;
import pe.gob.minsa.farmacia.domain.dto.MovimientoProductoStock;

public class MovimientoProductoDao {

    @Autowired
    DataSource dataSource;
    
    public List<MovimientoProducto> listarPorMovimiento(int idMovimiento) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("{call Far_Movimiento_Producto_ListarPorMov(?)}", new Object[]{idMovimiento}, new MovimientoProductoRowMapper());
    }
    
    public MovimientoProducto obtenerPorLote(int idProducto, String lote) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("{call Far_Movimiento_Producto_ListarPorLote(?,?)}", new Object[]{idProducto,lote}, new MovimientoProductoRowMapper()).get(0);
    }
    
    public List<String> obtenerLotes(int idProducto, int idAlmacen){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> lotes = (List<String>) jdbcTemplate.queryForList("{call Far_Producto_Lote(?,?)}", new Object[]{idProducto, idAlmacen}, String.class);
        return lotes;
    }
    
    public void ingresoInicial(int idMovimiento, int idPeriodoCerrar, int idAlmacenDestino, int usuarioCreacion){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Producto_Inicial");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodoCerrar", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimiento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", java.sql.Types.INTEGER));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
                
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodoCerrar", idPeriodoCerrar);
        maps.addValue("@IdMovimiento", idMovimiento);
        maps.addValue("@IdAlmacenDestino", idAlmacenDestino);
        maps.addValue("@UsuarioCreacion", usuarioCreacion);

        jdbcCall.execute(maps);        
    }
   
    public void insertar(MovimientoProducto movimientoProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Producto_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimiento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Cantidad", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Precio", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Total", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Lote", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaVencimiento", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RegistroSanitario", java.sql.Types.VARCHAR));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
                
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdMovimiento", movimientoProducto.getIdMovimiento());
        maps.addValue("@IdProducto", movimientoProducto.getIdProducto());
        maps.addValue("@Cantidad", movimientoProducto.getCantidad());
        maps.addValue("@Precio", movimientoProducto.getPrecio().doubleValue());
        maps.addValue("@Total", movimientoProducto.getTotal().doubleValue());
        maps.addValue("@Lote", movimientoProducto.getLote());        
        maps.addValue("@FechaVencimiento", movimientoProducto.getFechaVencimiento());
        maps.addValue("@RegistroSanitario", movimientoProducto.getRegistroSanitario());
        maps.addValue("@Activo", movimientoProducto.getActivo());
        maps.addValue("@UsuarioCreacion", movimientoProducto.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }
    
    public void eliminarPorMovimiento(int idMovimiento){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Producto_EliminarPorMov");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimiento", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdMovimiento", idMovimiento);

        jdbcCall.execute(maps);
    }

    public int stockPorIdProducto(int idProducto, int idAlmacen, String lote) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_StockProd_Almacen");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Lote", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Stock", Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", idProducto);
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@Lote", lote);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Integer) resultMap.get("@Stock"));
    }
    
    public List<MovimientoProductoStock> obtenerMovimientos(int idAlmacen, int idProducto){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Producto_Stock");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        
        jdbcCall.declareParameters(new SqlReturnResultSet("#movimientoProductoStockRowMapper", new MovimientoProductoStockRowMapper()));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", idProducto);
        maps.addValue("@IdAlmacen", idAlmacen);
        
        Map<String, Object> res = jdbcCall.execute(maps);
        
        List<MovimientoProductoStock> movimientosProductoStock = (List<MovimientoProductoStock>) res.get("#movimientoProductoStockRowMapper");
        return movimientosProductoStock;
    }
    
    public DetallePorLoteDto obtenerDetallePorLote(int idProducto, String lote){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_MovimientoProducto_ExisteLote");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Lote", java.sql.Types.VARCHAR));
        
        jdbcCall.declareParameters(new SqlReturnResultSet("#detallePorLoteRowMapper", new DetallePorLoteRowMapper()));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", idProducto);
        maps.addValue("@Lote", lote);
        
        Map<String, Object> res = jdbcCall.execute(maps);
        
        List<DetallePorLoteDto> detallesPorLote = (List<DetallePorLoteDto>) res.get("#detallePorLoteRowMapper");
        if(detallesPorLote.isEmpty()){
            return new DetallePorLoteDto();
        }else{
            return detallesPorLote.get(0);
        }
    }

    public class MovimientoProductoRowMapper implements RowMapper<MovimientoProducto> {

        @Override
        public MovimientoProducto mapRow(ResultSet rs, int i) throws SQLException {
            MovimientoProducto movimientoProducto = new MovimientoProducto();

            movimientoProducto.setIdMovimientoProducto(rs.getInt("IdMovimientoProducto"));
            movimientoProducto.setIdMovimiento(rs.getInt("IdMovimiento"));
            movimientoProducto.setIdProducto(rs.getInt("IdProducto"));
            movimientoProducto.setCantidad(rs.getInt("Cantidad"));
            movimientoProducto.setPrecio(rs.getBigDecimal("Precio"));
            movimientoProducto.setTotal(rs.getBigDecimal("Total"));
            movimientoProducto.setLote(rs.getString("Lote"));
            movimientoProducto.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));
            movimientoProducto.setRegistroSanitario(rs.getString("RegistroSanitario"));
            movimientoProducto.setNombreProducto(rs.getString("Descripcion"));
            
            return movimientoProducto;
        }
    }
    
    public class DetallePorLoteRowMapper implements RowMapper<DetallePorLoteDto>{
        @Override
        public DetallePorLoteDto mapRow(ResultSet rs, int i) throws SQLException {
            DetallePorLoteDto detallePorLoteDto = new DetallePorLoteDto();
            
            detallePorLoteDto.setPrecio(rs.getBigDecimal("Precio"));
            detallePorLoteDto.setRegistroSanitario(rs.getString("RegistroSanitario"));
            detallePorLoteDto.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));
            
            return detallePorLoteDto;            
        }        
    }
    
    public class MovimientoProductoStockRowMapper implements RowMapper<MovimientoProductoStock>{

        @Override
        public MovimientoProductoStock mapRow(ResultSet rs, int i) throws SQLException {
            MovimientoProductoStock movimientoProducto = new MovimientoProductoStock();

            movimientoProducto.setIdProducto(rs.getInt("IdProducto"));
            movimientoProducto.setLote(rs.getString("Lote"));
            movimientoProducto.setCantidad(rs.getInt("Cantidad"));
            movimientoProducto.setPrecio(rs.getBigDecimal("Precio"));            
            movimientoProducto.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));
            movimientoProducto.setRegistroSanitario(rs.getString("RegistroSanitario"));
            movimientoProducto.setNombreProducto(rs.getString("Producto"));
            
            return movimientoProducto;
        }        
    }
}
