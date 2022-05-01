package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import pe.gob.minsa.farmacia.domain.InventarioProducto;
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;

public class InventarioProductoDao {

    @Autowired
    DataSource dataSource;

    public List<InventarioProductoTotalDto> listarTotales(int idInventario) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("Far_Inventario_Producto_ListarTotales");

            jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventario", java.sql.Types.INTEGER));

            jdbcCall.declareParameters(new SqlReturnResultSet("#inventarioProductoTotalesDto", new InventarioProductoTotalDtoRowMapper()));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@IdInventario", idInventario);

            Map<String, Object> res = jdbcCall.execute(maps);
            return (List<InventarioProductoTotalDto>) res.get("#inventarioProductoTotalesDto");
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<InventarioProductoTotalDto>();
        }
    }

    public void preparar(int idInventario, int usuarioCreacion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Producto_Preparar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdInventario", idInventario);
        maps.addValue("@UsuarioCreacion", usuarioCreacion);

        jdbcCall.execute(maps);
    }

    public void procesar(int idInventario, int usuarioModificacion){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Producto_Procesar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdInventario", idInventario);
        maps.addValue("@UsuarioModificacion", usuarioModificacion);

        jdbcCall.execute(maps);        
    }
    
    public List<InventarioProducto> listar(int idProducto, int idInventario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Producto_PorProductoInventario");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventario", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#InventarioProductoRowMapper", new InventarioProductoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", idProducto);
        maps.addValue("@IdInventario", idInventario);

        Map<String, Object> res = jdbcCall.execute(maps);

        List<InventarioProducto> inventarioProductos = (List<InventarioProducto>) res.get("#InventarioProductoRowMapper");
        return inventarioProductos;

    }

    public InventarioProducto obtenerPorId(int idInventarioProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Producto_PorId");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventarioProducto", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#InventarioProductoRowMapper", new InventarioProductoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdInventarioProducto", idInventarioProducto);

        Map<String, Object> res = jdbcCall.execute(maps);

        List<InventarioProducto> inventarioProductos = (List<InventarioProducto>) res.get("#InventarioProductoRowMapper");
        if (inventarioProductos.isEmpty()) {
            return new InventarioProducto();
        } else {
            return inventarioProductos.get(0);
        }
    }

    public void insertar(InventarioProducto inventarioProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Producto_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Lote", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaVencimiento", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Cantidad", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Precio", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Total", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Conteo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadFaltante", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadSobrante", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadAlterado", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdInventarioProducto", inventarioProducto.getIdInventarioProducto());
        maps.addValue("@IdInventario", inventarioProducto.getIdInventario());
        maps.addValue("@IdProducto", inventarioProducto.getIdProducto());
        maps.addValue("@Lote", inventarioProducto.getLote());
        maps.addValue("@FechaVencimiento", inventarioProducto.getFechaVencimiento());
        maps.addValue("@Cantidad", inventarioProducto.getCantidad());
        maps.addValue("@Precio", inventarioProducto.getPrecio());
        maps.addValue("@Total", inventarioProducto.getTotal());
        maps.addValue("@Conteo", inventarioProducto.getConteo());
        maps.addValue("@CantidadFaltante", inventarioProducto.getCantidadFaltante());
        maps.addValue("@CantidadSobrante", inventarioProducto.getCantidadSobrante());
        maps.addValue("@CantidadAlterado", inventarioProducto.getCantidadAlterado());
        maps.addValue("@UsuarioCreacion", inventarioProducto.getUsuarioCreacion());

        jdbcCall.execute(maps);

    }

    public void actualizar(InventarioProducto inventarioProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Producto_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventarioProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdInventario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Lote", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaVencimiento", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Cantidad", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Precio", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Total", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Conteo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadFaltante", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadSobrante", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CantidadAlterado", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdInventarioProducto", inventarioProducto.getIdInventarioProducto());
        maps.addValue("@IdInventario", inventarioProducto.getIdInventario());
        maps.addValue("@IdProducto", inventarioProducto.getIdProducto());
        maps.addValue("@Lote", inventarioProducto.getLote());
        maps.addValue("@FechaVencimiento", inventarioProducto.getFechaVencimiento());
        maps.addValue("@Cantidad", inventarioProducto.getCantidad());
        maps.addValue("@Precio", inventarioProducto.getPrecio());
        maps.addValue("@Total", inventarioProducto.getTotal());
        maps.addValue("@Conteo", inventarioProducto.getConteo());
        maps.addValue("@CantidadFaltante", inventarioProducto.getCantidadFaltante());
        maps.addValue("@CantidadSobrante", inventarioProducto.getCantidadSobrante());
        maps.addValue("@CantidadAlterado", inventarioProducto.getCantidadAlterado());
        maps.addValue("@UsuarioModificacion", inventarioProducto.getUsuarioModificacion());

        jdbcCall.execute(maps);

    }

    public class InventarioProductoTotalDtoRowMapper implements RowMapper<InventarioProductoTotalDto> {

        @Override
        public InventarioProductoTotalDto mapRow(ResultSet rs, int i) throws SQLException {
            InventarioProductoTotalDto inventarioProductoTotalesDto = new InventarioProductoTotalDto();

            inventarioProductoTotalesDto.setIdProducto(rs.getInt("IdProducto"));
            inventarioProductoTotalesDto.setDescripcion(rs.getString("Descripcion"));
            inventarioProductoTotalesDto.setFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            inventarioProductoTotalesDto.setCantidad(rs.getInt("Cantidad"));
            inventarioProductoTotalesDto.setConteo(rs.getInt("Conteo"));
            inventarioProductoTotalesDto.setPrecio(rs.getBigDecimal("Precio"));
            inventarioProductoTotalesDto.setCantidadFaltante(rs.getInt("CantidadFaltante"));
            inventarioProductoTotalesDto.setCantidadSobrante(rs.getInt("CantidadSobrante"));
            inventarioProductoTotalesDto.setCantidadAlterado(rs.getInt("CantidadAlterado"));

            return inventarioProductoTotalesDto;
        }
    }

    public class InventarioProductoRowMapper implements RowMapper<InventarioProducto> {

        @Override
        public InventarioProducto mapRow(ResultSet rs, int i) throws SQLException {
            InventarioProducto inventarioProducto = new InventarioProducto();

            inventarioProducto.setIdInventarioProducto(rs.getInt("IdInventarioProducto"));
            inventarioProducto.setIdInventario(rs.getInt("IdInventario"));
            inventarioProducto.setIdProducto(rs.getInt("IdProducto"));
            inventarioProducto.setLote(rs.getString("Lote"));
            inventarioProducto.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));
            inventarioProducto.setCantidad(rs.getInt("Cantidad"));
            inventarioProducto.setPrecio(rs.getBigDecimal("Precio"));
            inventarioProducto.setTotal(rs.getBigDecimal("Total"));
            inventarioProducto.setConteo(rs.getInt("Conteo"));
            inventarioProducto.setCantidadFaltante(rs.getInt("CantidadFaltante"));
            inventarioProducto.setCantidadSobrante(rs.getInt("CantidadSobrante"));
            inventarioProducto.setCantidadAlterado(rs.getInt("CantidadAlterado"));
            inventarioProducto.setActivo(rs.getInt("Activo"));

            return inventarioProducto;
        }

    }
}
