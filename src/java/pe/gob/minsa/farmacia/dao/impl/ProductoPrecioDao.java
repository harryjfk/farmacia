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
import pe.gob.minsa.farmacia.domain.ProductoPrecio;
import pe.gob.minsa.farmacia.domain.dto.PrecioUltimoDto;
import pe.gob.minsa.farmacia.util.UtilDao;

public class ProductoPrecioDao {

    @Autowired
    DataSource dataSource;

    public List<PrecioUltimoDto> listarUltimoPrecio(String descripcion) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<PrecioUltimoDto> precioUltimo = jdbcTemplate.query("{call Far_Producto_Precio_Ultimo(?)}", new Object[]{descripcion}, new PrecioUltimoDtoRowMapper());
        return precioUltimo;
    }

    public List<ProductoPrecio> listarPorProducto(int idProducto) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductoPrecio> productosPrecio = jdbcTemplate.query("{call Far_Producto_Precio_PorProducto(?)}", new Object[]{idProducto}, new ProductoPrecioRowMapper());
        return productosPrecio;
    }
    
    public void insertar(ProductoPrecio productoPrecio){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Precio_Insertar");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoPrecio", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@PrecioAdquisicion", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@PrecioDistribucion", java.sql.Types.DECIMAL));
        jdbcCall.addDeclaredParameter(new SqlParameter("@PrecioOperacion", java.sql.Types.DECIMAL));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRegistro", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaVigencia", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@TipoPrecio", productoPrecio.getTipoPrecio());
        maps.addValue("@PrecioAdquisicion", productoPrecio.getPrecioAdquisicion());
        maps.addValue("@PrecioDistribucion", productoPrecio.getPrecioDistribucion());
        maps.addValue("@PrecioOperacion", productoPrecio.getPrecioOperacion());
        maps.addValue("@IdProducto", productoPrecio.getIdProducto());
        maps.addValue("@FechaRegistro", productoPrecio.getFechaRegistro());
        maps.addValue("@FechaVigencia", productoPrecio.getFechaVigencia());       
        maps.addValue("@UsuarioCreacion", productoPrecio.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    public class ProductoPrecioRowMapper implements RowMapper<ProductoPrecio> {

        @Override
        public ProductoPrecio mapRow(ResultSet rs, int i) throws SQLException {

            ProductoPrecio productoPrecio = new ProductoPrecio();

            productoPrecio.setIdProductoPrecio(rs.getInt("IdProductoPrecio"));
            productoPrecio.setTipoPrecio(rs.getString("TipoPrecio"));
            productoPrecio.setPrecioAdquisicion(rs.getBigDecimal("PrecioAdquisicion"));
            productoPrecio.setPrecioDistribucion(rs.getBigDecimal("PrecioDistribucion"));
            productoPrecio.setPrecioOperacion(rs.getBigDecimal("PrecioOperacion"));
            productoPrecio.setIdProducto(rs.getInt("IdProducto"));
            productoPrecio.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
            productoPrecio.setFechaVigencia(rs.getTimestamp("FechaVigencia"));

            return productoPrecio;
        }

    }

    public class PrecioUltimoDtoRowMapper implements RowMapper<PrecioUltimoDto> {

        @Override
        public PrecioUltimoDto mapRow(ResultSet rs, int i) throws SQLException {

            PrecioUltimoDto precioUltimoDto = new PrecioUltimoDto();
            precioUltimoDto.setIdProducto(rs.getInt("IdProducto"));
            precioUltimoDto.setNombreProducto(rs.getString("NombreProducto"));
            precioUltimoDto.setCodigoSismed(UtilDao.getStringFromNull(rs, "CodigoSismed"));
            precioUltimoDto.setPrecioAdquisicion(UtilDao.getBigDecimalFromNull(rs, "PrecioAdquisicion"));
            precioUltimoDto.setPrecioDistribucion(UtilDao.getBigDecimalFromNull(rs, "PrecioDistribucion"));
            precioUltimoDto.setPrecioOperacion(UtilDao.getBigDecimalFromNull(rs, "PrecioOperacion"));

            return precioUltimoDto;
        }
    }
}
