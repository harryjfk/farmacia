package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.domain.dto.AyudaProductoIngresoDto;
import pe.gob.minsa.farmacia.domain.dto.AyudaProductoSalidaDto;
import pe.gob.minsa.farmacia.domain.dto.DetalleStockEnAlmacen;
import pe.gob.minsa.farmacia.domain.dto.DetalleStockPorAlmacen;
import pe.gob.minsa.farmacia.domain.dto.ProductoAlertaVencimientoDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoComp;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockFechaDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockMinimo;
import pe.gob.minsa.farmacia.domain.dto.StockGeneralProductoDto;
import pe.gob.minsa.farmacia.domain.param.ProductoAlmacenParam;
import pe.gob.minsa.farmacia.domain.param.ProductoParam;
import pe.gob.minsa.farmacia.util.UtilDao;

public class ProductoDao implements DaoManager<Producto> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Producto> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Producto> productos = jdbcTemplate.query("{call Far_Producto_Listar}", new ProductoRowMapper());

        return productos;
    }

    public List<ProductoComp> listar(ProductoParam productoParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Listar_Comp");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdFormaFarmaceutica", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUnidadMedida", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EstrSop", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EstrVta", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TraNac", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TraLoc", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Narcotico", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#productoRowMapper", new ProductoCompRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Descripcion", productoParam.getDescripcion());
        maps.addValue("@IdFormaFarmaceutica", productoParam.getIdFormaFarmaceutica());
        maps.addValue("@IdTipoProducto", productoParam.getIdTipoProducto());
        maps.addValue("@IdUnidadMedida", productoParam.getIdUnidadMedida());
        maps.addValue("@EstrSop", productoParam.getEstrSop());
        maps.addValue("@EstrVta", productoParam.getEstrVta());
        maps.addValue("@TraNac", productoParam.getTraNac());
        maps.addValue("@TraLoc", productoParam.getTraLoc());
        maps.addValue("@Narcotico", productoParam.getNarcotico());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<ProductoComp>) res.get("#productoRowMapper");

    }

    public List<ProductoComp> listarPorAlmacen(ProductoAlmacenParam productoAlmacenParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_ListarComp_PorAlmacen");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdFormaFarmaceutica", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUnidadMedida", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#productoRowMapper", new ProductoCompRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Descripcion", productoAlmacenParam.getDescripcion());
        maps.addValue("@IdFormaFarmaceutica", productoAlmacenParam.getIdFormaFarmaceutica());
        maps.addValue("@IdTipoProducto", productoAlmacenParam.getIdTipoProducto());
        maps.addValue("@IdUnidadMedida", productoAlmacenParam.getIdUnidadMedida());
        maps.addValue("@IdAlmacen", productoAlmacenParam.getIdAlmacen());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<ProductoComp>) res.get("#productoRowMapper");
    }

    /**
     * @param criterio Realiza la búsqueda en base a descripción o codigo sismed
     * @return Lista de productos en base a la búsqueda realizada
     */ 
    public List<AyudaProductoIngresoDto> listarAyudaProductoIngreso(String criterio){
          SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_AyudaIngresos");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Criterio", java.sql.Types.VARCHAR));
        
         jdbcCall.declareParameters(new SqlReturnResultSet("#productoRowMapper", new AyudaProductoIngresoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Criterio", criterio);
        
        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<AyudaProductoIngresoDto>) res.get("#productoRowMapper");
    }    
    
    /**
     * @param criterio Realiza la búsqueda en base a descripción o codigo sismed
     * @return Lista de productos en base a la búsqueda realizada
     */
    public List<AyudaProductoSalidaDto> listarAyudaProductoSalida(String criterio, int idAlmacen){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_StockPorAlmacen");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Criterio", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        
         jdbcCall.declareParameters(new SqlReturnResultSet("#productoRowMapper", new AyudaProductoSalidaRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Criterio", criterio);
        maps.addValue("@IdAlmacen", idAlmacen);        
        
        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<AyudaProductoSalidaDto>) res.get("#productoRowMapper");
    }
    
    public List<ProductoAlertaVencimientoDto> listarAlertaVencimiento(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductoAlertaVencimientoDto> productos = jdbcTemplate.query("{call Far_Producto_AlertaVencimiento}", new ProductoAlertaVencimientoRowMapper());

        return productos;
    }
    
     public List<ProductoAlertaVencimientoDto> listarProductosVencimiento(int idAlmacen, int idProducto, Timestamp fechaVenDesde, Timestamp fechaVenHasta){
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Vencimiento");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));
        
         jdbcCall.declareParameters(new SqlReturnResultSet("#productoRowMapper", new ProductoAlertaVencimientoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();        
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@IdProducto", idProducto);
        maps.addValue("@FechaDesde", fechaVenDesde);
        maps.addValue("@FechaHasta", fechaVenHasta);
        
        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<ProductoAlertaVencimientoDto>) res.get("#productoRowMapper");
    }
    
    public List<ProductoStockMinimo> listarConStockMinimo(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductoStockMinimo> productos = jdbcTemplate.query("{call Far_Producto_StockMinimo}", new ProductoStockMinimoRowMapper());

        return productos;
    }
    
    public List<ProductoComp> listarMedicamento() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductoComp> productos = jdbcTemplate.query("{call Far_Medicamento_Listar}", new MedicamentoRowMapper());

        return productos;
    }

    public List<StockGeneralProductoDto> listarStockGeneral(String descripcion) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<StockGeneralProductoDto> productos = jdbcTemplate.query("{call Far_Producto_StockGeneral(?)}", new Object[]{descripcion}, new StockGeneralProductoDtoRowMapper());

        return productos;
    }
    
    public List<DetalleStockPorAlmacen> listarStockPorAlmacenes(int idProducto){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<DetalleStockPorAlmacen> stockPorAlmacen = jdbcTemplate.query("{call Far_Producto_StockEnAlmacenes(?)}", new Object[]{idProducto}, new DetalleStockPorAlmacenRowMapper());

        return stockPorAlmacen;
    }
    
    public List<DetalleStockEnAlmacen> listarStockPorAlmacen(int idProducto, int idAlmacen, Timestamp fecha){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_StockEnAlmacen");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@fecha", java.sql.Types.TIMESTAMP));
        
        jdbcCall.declareParameters(new SqlReturnResultSet("#productoStockRowMapper", new DetalleStockEnAlmacenRowMapper()));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", idProducto);
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@fecha", fecha);
        
        Map<String, Object> res = jdbcCall.execute(maps);
        
        return (List<DetalleStockEnAlmacen>) res.get("#productoStockRowMapper");
    }
    
    public List<ProductoStockFechaDto> listarProductoAFecha(Timestamp fecha, int idAlmacen){
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Stock_Fecha");
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fecha", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        
        jdbcCall.declareParameters(new SqlReturnResultSet("#productoStockRowMapper", new ProductoStockFechaDtoRowMapper()));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Fecha", fecha);
        maps.addValue("@IdAlmacen", idAlmacen);
        
        Map<String, Object> res = jdbcCall.execute(maps);
        
        return (List<ProductoStockFechaDto>) res.get("#productoStockRowMapper");
    }

    @Override
    public Producto obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Producto producto = jdbcTemplate.queryForObject("{call Far_Producto_ListarPorId(?)}", new Object[]{id}, new ProductoRowMapper());

        return producto;
    }

    public ProductoComp obtenerProductoCompPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ProductoComp producto = jdbcTemplate.queryForObject("{call Far_Producto_ListarComp_PorId(?)}", new Object[]{id}, new ProductoCompRowMapper());

        return producto;
    }
    
    public ProductoComp obtenerProductoCompPorIdPorSolicitud(int id, int idSolicitud) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        ProductoComp producto = jdbcTemplate.queryForObject("{call Far_Producto_ListarComp_PorId(?, ?)}", new Object[]{id, idSolicitud}, new ProductoCompRowMapperDet());

        return producto;
    }

    @Override
    public void insertar(Producto producto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSismed", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSiga", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdFormaFarmaceutica", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUnidadMedida", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Presentacion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Concentracion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Petitorio", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EstrSop", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EstrVta", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TraNac", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TraLoc", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Narcotico", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@StockMin", java.sql.Types.NUMERIC));
        jdbcCall.addDeclaredParameter(new SqlParameter("@StockMax", java.sql.Types.NUMERIC));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Requerimiento", java.sql.Types.NUMERIC));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Adscrito", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Descripcion", producto.getDescripcion());
        maps.addValue("@IdProductoSismed", producto.getIdProductoSismed());
        maps.addValue("@IdProductoSiga", producto.getIdProductoSiga());
        maps.addValue("@Abreviatura", producto.getAbreviatura());
        maps.addValue("@IdFormaFarmaceutica", producto.getIdFormaFarmaceutica());
        maps.addValue("@IdTipoProducto", producto.getIdTipoProducto());
        maps.addValue("@IdUnidadMedida", producto.getIdUnidadMedida());
        maps.addValue("@Presentacion", producto.getPresentacion());
        maps.addValue("@Concentracion", producto.getConcentracion());     
        maps.addValue("@Petitorio", producto.getPetitorio());        
        maps.addValue("@EstrSop", producto.getEstrSop());
        maps.addValue("@EstrVta", producto.getEstrVta());
        maps.addValue("@TraNac", producto.getTraNac());
        maps.addValue("@TraLoc", producto.getTraLoc());
        maps.addValue("@Narcotico", producto.getNarcotico());
        maps.addValue("@StockMin", producto.getStockMin());
        maps.addValue("@StockMax", producto.getStockMax());
        maps.addValue("@Requerimiento", producto.getRequerimiento());
        maps.addValue("@Adscrito", producto.getAdscrito());
        maps.addValue("@Activo", producto.getActivo());
        maps.addValue("@UsuarioCreacion", producto.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(Producto producto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSismed", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSiga", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdFormaFarmaceutica", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUnidadMedida", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Presentacion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Concentracion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Petitorio", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EstrSop", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@EstrVta", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TraNac", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TraLoc", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Narcotico", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@StockMin", java.sql.Types.NUMERIC));
        jdbcCall.addDeclaredParameter(new SqlParameter("@StockMax", java.sql.Types.NUMERIC));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Requerimiento", java.sql.Types.NUMERIC));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Adscrito", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", producto.getIdProducto());
        maps.addValue("@Descripcion", producto.getDescripcion());
        maps.addValue("@IdProductoSismed", producto.getIdProductoSismed());
        maps.addValue("@IdProductoSiga", producto.getIdProductoSiga());
        maps.addValue("@Abreviatura", producto.getAbreviatura());
        maps.addValue("@IdFormaFarmaceutica", producto.getIdFormaFarmaceutica());
        maps.addValue("@IdTipoProducto", producto.getIdTipoProducto());
        maps.addValue("@IdUnidadMedida", producto.getIdUnidadMedida());
        maps.addValue("@Presentacion", producto.getPresentacion());
        maps.addValue("@Concentracion", producto.getConcentracion());
        maps.addValue("@Petitorio", producto.getPetitorio());
        maps.addValue("@EstrSop", producto.getEstrSop());
        maps.addValue("@EstrVta", producto.getEstrVta());
        maps.addValue("@TraNac", producto.getTraNac());
        maps.addValue("@TraLoc", producto.getTraLoc());
        maps.addValue("@Narcotico", producto.getNarcotico());
        maps.addValue("@StockMin", producto.getStockMin());
        maps.addValue("@StockMax", producto.getStockMax());
        maps.addValue("@Requerimiento", producto.getRequerimiento());
        maps.addValue("@Adscrito", producto.getAdscrito());
        maps.addValue("@Activo", producto.getActivo());
        maps.addValue("@UsuarioModificacion", producto.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProducto", id);

        jdbcCall.execute(maps);
    }

    public class MedicamentoRowMapper implements RowMapper<ProductoComp> {

        @Override
        public ProductoComp mapRow(ResultSet rs, int i) throws SQLException {
            ProductoComp producto = new ProductoComp();

            producto.setIdProducto(rs.getInt("IdProducto"));
            producto.setDescripcion(rs.getString("Descripcion"));
            producto.setConcentracion(rs.getString("Concentracion"));
            producto.setFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));

            return producto;
        }
    }

    public class ProductoRowMapper implements RowMapper<Producto> {

        @Override
        public Producto mapRow(ResultSet rs, int i) throws SQLException {
            Producto producto = new Producto();

            producto.setIdProducto(rs.getInt("IdProducto"));
            producto.setDescripcion(rs.getString("Descripcion"));
            producto.setIdProductoSismed(rs.getInt("IdProductoSismed"));
            producto.setIdProductoSiga(rs.getInt("IdProductoSiga"));
            producto.setAbreviatura(rs.getString("Abreviatura"));
            producto.setIdFormaFarmaceutica(rs.getInt("IdFormaFarmaceutica"));
            producto.setIdTipoProducto(rs.getInt("IdTipoProducto"));
            producto.setIdUnidadMedida(rs.getInt("IdUnidadMedida"));
            producto.setPresentacion(rs.getString("Presentacion"));
            producto.setConcentracion(rs.getString("Concentracion"));
            producto.setPetitorio(rs.getInt("Petitorio"));
            producto.setEstrSop(rs.getInt("EstrSop"));
            producto.setEstrVta(rs.getInt("EstrVta"));
            producto.setTraNac(rs.getInt("TraNac"));
            producto.setTraLoc(rs.getInt("TraLoc"));
            producto.setNarcotico(rs.getInt("Narcotico"));
            producto.setStockMin(rs.getBigDecimal("StockMin"));
            producto.setStockMax(rs.getBigDecimal("StockMax"));
            producto.setRequerimiento(rs.getBigDecimal("Requerimiento"));
            producto.setAdscrito(rs.getInt("Adscrito"));
            producto.setActivo(rs.getInt("Activo"));

            return producto;
        }
    }

    
public class ProductoCompRowMapper implements RowMapper<ProductoComp> {

        @Override
        public ProductoComp mapRow(ResultSet rs, int i) throws SQLException {
            ProductoComp producto = new ProductoComp();

            producto.setIdProducto(rs.getInt("IdProducto"));
            producto.setDescripcion(rs.getString("Descripcion"));
            producto.setPresentacion(rs.getString("Presentacion"));
            producto.setConcentracion(rs.getString("Concentracion"));
            producto.setFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            producto.setTipoProducto(rs.getString("NombreTipoProducto"));
            producto.setUnidadMedida(rs.getString("NombreUnidadMedida"));
            producto.setActivo(rs.getInt("Activo"));
            
            return producto;
        }
    }

public class ProductoCompRowMapperDet implements RowMapper<ProductoComp> {

        @Override
        public ProductoComp mapRow(ResultSet rs, int i) throws SQLException {
            ProductoComp producto = new ProductoComp();

            producto.setIdProducto(rs.getInt("IdProducto"));
            producto.setDescripcion(rs.getString("Descripcion"));
            producto.setPresentacion(rs.getString("Presentacion"));
            producto.setConcentracion(rs.getString("Concentracion"));
            producto.setFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            producto.setTipoProducto(rs.getString("NombreTipoProducto"));
            producto.setUnidadMedida(rs.getString("NombreUnidadMedida"));
            producto.setActivo(rs.getInt("Activo"));

            
            producto.setAprobado(rs.getString("Aprobado"));
            producto.setMotivoAprobado(rs.getString("MotivoAprobado"));
            producto.setCondicionAprobado(rs.getString("CondicionAprobado"));
            producto.setCantidadAprobada(rs.getString("CantidadAprobada"));
                    
            return producto;
        }
    }

    public class AyudaProductoIngresoRowMapper implements RowMapper<AyudaProductoIngresoDto> {

        @Override
        public AyudaProductoIngresoDto mapRow(ResultSet rs, int i) throws SQLException {
            AyudaProductoIngresoDto producto = new AyudaProductoIngresoDto();
            
            producto.setIdProducto(rs.getInt("IdProducto"));
            producto.setCodigoSismed(UtilDao.getStringFromNull(rs, "CodigoSismed"));
            producto.setProducto(rs.getString("Producto"));
            producto.setFormaFarmaceutica(rs.getString("FormaFarmaceutica"));
            
            return producto;
        }
    }
    
    public class AyudaProductoSalidaRowMapper implements RowMapper<AyudaProductoSalidaDto>{

        @Override
        public AyudaProductoSalidaDto mapRow(ResultSet rs, int i) throws SQLException {
            AyudaProductoSalidaDto producto = new AyudaProductoSalidaDto();
            
            producto.setIdProducto(rs.getInt("IdProducto"));
            producto.setCodigoSismed(UtilDao.getStringFromNull(rs, "CodigoSismed"));
            producto.setProducto(rs.getString("Producto"));
            producto.setFormaFarmaceutica(rs.getString("FormaFarmaceutica"));
            producto.setCantidad(rs.getInt("Cantidad"));
            
            return producto;
        }        
    }
    
    public class StockGeneralProductoDtoRowMapper implements RowMapper<StockGeneralProductoDto> {

        @Override
        public StockGeneralProductoDto mapRow(ResultSet rs, int i) throws SQLException {
            StockGeneralProductoDto stockGeneralProductoDto = new StockGeneralProductoDto();
            
            stockGeneralProductoDto.setIdProducto(rs.getInt("IdProducto"));
            stockGeneralProductoDto.setCodigoSismed(rs.getString("CodigoSismed"));
            stockGeneralProductoDto.setDescripcion(rs.getString("Descripcion"));
            stockGeneralProductoDto.setCantidad(rs.getInt("Cantidad"));
            stockGeneralProductoDto.setNombreFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            stockGeneralProductoDto.setPresentacion(rs.getString("Presentacion"));
            stockGeneralProductoDto.setConcentracion(rs.getString("Concentracion"));
            stockGeneralProductoDto.setPrecioRef(rs.getBigDecimal("PrecioRef"));
            stockGeneralProductoDto.setStockMax(rs.getInt("StockMin"));
            stockGeneralProductoDto.setStockMax(rs.getInt("StockMax"));

            return stockGeneralProductoDto;
        }
    }
    
    public class ProductoStockFechaDtoRowMapper implements RowMapper<ProductoStockFechaDto>{

        @Override
        public ProductoStockFechaDto mapRow(ResultSet rs, int i) throws SQLException {
            ProductoStockFechaDto productoStockFecha = new ProductoStockFechaDto();
            
            productoStockFecha.setIdProducto(rs.getInt("IdProducto"));
            productoStockFecha.setCodigoSismed(rs.getString("CodigoSismed"));
            productoStockFecha.setDescripcion(rs.getString("Descripcion"));
            productoStockFecha.setTipoProducto(rs.getString("NombreTipoProducto"));
            productoStockFecha.setFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            productoStockFecha.setStock(rs.getInt("Cantidad"));
            
            return productoStockFecha;            
        }
    }
    
    public class ProductoAlertaVencimientoRowMapper implements RowMapper<ProductoAlertaVencimientoDto>{

        @Override
        public ProductoAlertaVencimientoDto mapRow(ResultSet rs, int i) throws SQLException {
            ProductoAlertaVencimientoDto productoAlertaVencimiento = new ProductoAlertaVencimientoDto();
            
            productoAlertaVencimiento.setAlmacen(rs.getString("Almacen"));
            productoAlertaVencimiento.setCodigoSismed(UtilDao.getStringFromNull(rs, "CodigoSismed"));
            productoAlertaVencimiento.setProducto(rs.getString("NombreProducto"));
            productoAlertaVencimiento.setLote(rs.getString("Lote"));
            productoAlertaVencimiento.setTipoProducto(rs.getString("NombreTipoProducto"));
            productoAlertaVencimiento.setStock(rs.getInt("Cantidad"));
            productoAlertaVencimiento.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));
            productoAlertaVencimiento.setEstado(rs.getString("Estado"));            
            
            return productoAlertaVencimiento;
        }        
    }
    
    public class ProductoStockMinimoRowMapper implements RowMapper<ProductoStockMinimo>{

        @Override
        public ProductoStockMinimo mapRow(ResultSet rs, int i) throws SQLException {
            ProductoStockMinimo productoStockMinimo = new ProductoStockMinimo();
            
            productoStockMinimo.setAlmacen(rs.getString("Almacen"));
            productoStockMinimo.setCodigoSismed(UtilDao.getStringFromNull(rs, "CodigoSismed"));
            productoStockMinimo.setProducto(rs.getString("NombreProducto"));
            productoStockMinimo.setLote(rs.getString("Lote"));
            productoStockMinimo.setTipoProducto(rs.getString("NombreTipoProducto"));
            productoStockMinimo.setStock(rs.getInt("Cantidad"));
            productoStockMinimo.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));            
            productoStockMinimo.setStockMin(rs.getInt("StockMin"));
            
            return productoStockMinimo;
        }        
    }
    
    public class DetalleStockPorAlmacenRowMapper implements RowMapper<DetalleStockPorAlmacen>{

        @Override
        public DetalleStockPorAlmacen mapRow(ResultSet rs, int i) throws SQLException {
            DetalleStockPorAlmacen detalleStockPorAlmacen = new DetalleStockPorAlmacen();
            
            detalleStockPorAlmacen.setAlmacen(rs.getString("Almacen"));
            detalleStockPorAlmacen.setCodigoAlmacen(rs.getString("CodigoAlmacen"));
            detalleStockPorAlmacen.setCantidad(rs.getInt("Cantidad"));
            detalleStockPorAlmacen.setPrecio(rs.getBigDecimal("Precio"));
            
            return detalleStockPorAlmacen;
        }
    }
    
    public class DetalleStockEnAlmacenRowMapper implements RowMapper<DetalleStockEnAlmacen>{

        @Override
        public DetalleStockEnAlmacen mapRow(ResultSet rs, int i) throws SQLException {
            DetalleStockEnAlmacen detalleStockEnAlmacen = new DetalleStockEnAlmacen();
            
            detalleStockEnAlmacen.setCantidad(rs.getInt("Cantidad"));
            detalleStockEnAlmacen.setFechaVencimiento(rs.getTimestamp("FechaVencimiento"));
            detalleStockEnAlmacen.setLote(rs.getString("Lote"));
            
            return detalleStockEnAlmacen;
        }
        
    }
}
