package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.param.MovimientoParam;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.Proveedor;
import pe.gob.minsa.farmacia.domain.dto.IndicadorGestionDaoDto;
import pe.gob.minsa.farmacia.domain.dto.IngresoAlmacenDto;
import pe.gob.minsa.farmacia.domain.dto.MovimientoDto;
import pe.gob.minsa.farmacia.domain.dto.TarjetaControlDto;
import pe.gob.minsa.farmacia.domain.param.ConsultaMovimientoParam;
import pe.gob.minsa.farmacia.domain.param.IndicadorGestionParam;
import pe.gob.minsa.farmacia.domain.param.IngresoAlmacenParam;
import pe.gob.minsa.farmacia.domain.param.TarjetaControlParam;
import pe.gob.minsa.farmacia.domain.TipoMovimiento;
import pe.gob.minsa.farmacia.domain.lazyload.ProxyAlmacen;
import pe.gob.minsa.farmacia.domain.lazyload.ProxyConcepto;
import pe.gob.minsa.farmacia.domain.lazyload.ProxyDocumentoOrigen;
import pe.gob.minsa.farmacia.domain.lazyload.ProxyTipoCompra;
import pe.gob.minsa.farmacia.domain.lazyload.ProxyTipoDocumentoMov;
import pe.gob.minsa.farmacia.domain.lazyload.ProxyTipoProceso;
import pe.gob.minsa.farmacia.util.UtilDao;

public class MovimientoDao implements DaoManager<Movimiento> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Movimiento> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Movimiento> movimientos = jdbcTemplate.query("{call Far_Movimiento_Listar}", new MovimientoRowMapper());
        return movimientos;
    }

    public List<Movimiento> listarPorTipo(MovimientoParam movimentoParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_ListarPorTipo");

        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMovimiento", java.sql.Types.CHAR));

        jdbcCall.declareParameters(new SqlReturnResultSet("#movimientoRowMapper", new MovimientoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();        
        maps.addValue("@FechaDesde", movimentoParam.getFechaDesde());
        maps.addValue("@FechaHasta", movimentoParam.getFechaHasta());        
        maps.addValue("@TipoMovimiento", movimentoParam.getTipoMovimento().toString());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<Movimiento>) res.get("#movimientoRowMapper");
    }
    
    public int ingresoInicial(int idAlmacen, int idPeriodoAbrir, int usuarioCreacion){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_IngresoInicial");        
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodoAbrir", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdMovimiento", java.sql.Types.INTEGER));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodoAbrir", idPeriodoAbrir);
        maps.addValue("@IdAlmacenDestino", idAlmacen);
        maps.addValue("@UsuarioCreacion", usuarioCreacion);
        
        Map<String, Object> res = jdbcCall.execute(maps);
        int idMovimiento = (Integer) res.get("@IdMovimiento");
        return idMovimiento;
    }

    public List<IndicadorGestionDaoDto> indicadorGestion(Timestamp fechaDesde, Timestamp fechaHasta) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimento_IndicadorGestion");

        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRegistroDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRegistroHasta", java.sql.Types.TIMESTAMP));

        jdbcCall.declareParameters(new SqlReturnResultSet("#movimientoRowMapper", new IndicadorGestionRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@FechaRegistroDesde", fechaDesde);
        maps.addValue("@FechaRegistroHasta", fechaHasta);

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<IndicadorGestionDaoDto>) res.get("#movimientoRowMapper");
    }

    public List<MovimientoDto> consultaMovimiento(ConsultaMovimientoParam consultaMovimientoParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Consulta");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMovimiento", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#movimientoRowMapper", new MovimientoDtoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", consultaMovimientoParam.getIdPeriodo());
        maps.addValue("@TipoMovimiento", consultaMovimientoParam.getTipoMovimiento());
        maps.addValue("@IdConcepto", consultaMovimientoParam.getIdConcepto());
        maps.addValue("@IdAlmacenOrigen", consultaMovimientoParam.getIdAlmacenOrigen());
        maps.addValue("@IdAlmacenDestino", consultaMovimientoParam.getIdAlmacenDestino());
        maps.addValue("@FechaDesde", consultaMovimientoParam.getFechaDesde());
        maps.addValue("@FechaHasta", consultaMovimientoParam.getFechaHasta());
        maps.addValue("@Activo", consultaMovimientoParam.getActivo());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<MovimientoDto>) res.get("#movimientoRowMapper");
    }

    public List<TarjetaControlDto> tarjetaControl(TarjetaControlParam tarjetaControlParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_TarjetaKardex");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProducto", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#movimientoRowMapper", new TarjetaControlDtoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", tarjetaControlParam.getIdPeriodo());
        maps.addValue("@IdAlmacen", tarjetaControlParam.getIdAlmacen());
        maps.addValue("@FechaDesde", tarjetaControlParam.getFechaDesde());
        maps.addValue("@FechaHasta", tarjetaControlParam.getFechaHasta());
        maps.addValue("@IdProducto", tarjetaControlParam.getIdProducto());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<TarjetaControlDto>) res.get("#movimientoRowMapper");
    }

    public List<IngresoAlmacenDto> listarIngresoPorAlmacen(IngresoAlmacenParam ingresoAlmacenParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_IngresoPorAlmacen");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProveedor", java.sql.Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#movimientoRowMapper", new IngresoAlmacenDtoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", ingresoAlmacenParam.getIdPeriodo());
        maps.addValue("@IdAlmacenOrigen", ingresoAlmacenParam.getIdAlmacenOrigen());
        maps.addValue("@IdAlmacenDestino", ingresoAlmacenParam.getIdAlmacenDestino());
        maps.addValue("@FechaDesde", ingresoAlmacenParam.getFechaDesde());
        maps.addValue("@FechaHasta", ingresoAlmacenParam.getFechaHasta());
        maps.addValue("@IdProveedor", ingresoAlmacenParam.getIdProveedor());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<IngresoAlmacenDto>) res.get("#movimientoRowMapper");
    }

    public Movimiento obtenerPorIdIngreso(int idIngreso) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Movimiento movimiento = jdbcTemplate.queryForObject("{call Far_Movimiento_ListarPorIdIngreso(?)}", new Object[]{idIngreso}, new MovimientoRowMapper());
            return movimiento;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Movimiento obtenerPorId(int id) {
        try {

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Movimiento movimiento = jdbcTemplate.queryForObject("{call Far_Movimiento_ListarPorId(?)}", new Object[]{id}, new MovimientoRowMapper());
            return movimiento;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    /**
     * Método que devuelve el siguente número del correlativo de notas de
     * ingreso
     *
     * @param idPeriodo El periodo del cual se va a registrar una nota de
     * ingreso, ejemplo : 201401
     * @param idAlmacenDestino El almacén del cual se va a registrar una nota de
     * ingreso
     * @return Siguiente numero del correlativo de notas de ingreso
     */
    public int obtenerNumeroCorrelativoIngreso(int idPeriodo, int idAlmacenDestino) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Numero_Ingreso");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@NumeroMovimiento", Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdAlmacenDestino", idAlmacenDestino);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Integer) resultMap.get("@NumeroMovimiento"));
    }

    /**
     * Método que devuelve el siguente número del correlativo de notas de salida
     *
     * @param idPeriodo El periodo del cual se va a registrar una nota de
     * ingreso, ejemplo : 201401
     * @param idAlmacenOrigen El almacén del cual se va a registrar una nota de
     * salida
     * @return Siguiente numero del correlativo de notas de ingreso
     */
    public int obtenerNumeroCorrelativoSalida(int idPeriodo, int idAlmacenOrigen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Numero_Salida");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenOrigen", Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@NumeroMovimiento", Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdAlmacenOrigen", idAlmacenOrigen);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Integer) resultMap.get("@NumeroMovimiento"));
    }

    public List<IndicadorGestionDaoDto> obtenerIndicarGestionDao(IndicadorGestionParam indicadorGestionParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_IndicarGestion");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDesde", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaHasta", java.sql.Types.TIMESTAMP));

        jdbcCall.declareParameters(new SqlReturnResultSet("#indicadorGestionRowMapper", new IndicadorGestionDaoRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProducto", indicadorGestionParam.getIdTipoProducto());
        maps.addValue("@FechaDesde", indicadorGestionParam.getFechaDesde());
        maps.addValue("@FechaHasta", indicadorGestionParam.getFechaHasta());

        Map<String, Object> res = jdbcCall.execute(maps);

        return (List<IndicadorGestionDaoDto>) res.get("#indicadorGestionRowMapper");
    }

    public boolean ExisteSaldoInicial(int idPeriodo, int idConcepto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_ExisteSaldoInicial");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdConcepto", idConcepto);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }

    
    @Override
    public void insertar(Movimiento movimiento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMovimiento", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroMovimiento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRegistro", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumentoMov", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroDocumentoMov", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRecepcion", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroDocumentoOrigen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDocumentoOrigen", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProveedor", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoCompra", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroProceso", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Referencia", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimientoIngreso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdMovimiento", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", movimiento.getIdPeriodo());
        maps.addValue("@TipoMovimiento", movimiento.getTipoMovimiento().toString());
        maps.addValue("@NumeroMovimiento", movimiento.getNumeroMovimiento());
        maps.addValue("@FechaRegistro", movimiento.getFechaRegistro());
        maps.addValue("@IdAlmacenOrigen", movimiento.getIdAlmacenOrigen());
        maps.addValue("@IdAlmacenDestino", movimiento.getIdAlmacenDestino());
        maps.addValue("@IdConcepto", movimiento.getIdConcepto());
        maps.addValue("@IdTipoDocumentoMov", movimiento.getIdTipoDocumentoMov());
        maps.addValue("@NumeroDocumentoMov", movimiento.getNumeroDocumentoMov());
        maps.addValue("@FechaRecepcion", movimiento.getFechaRecepcion());
        maps.addValue("@IdDocumentoOrigen", movimiento.getIdDocumentoOrigen());
        maps.addValue("@NumeroDocumentoOrigen", movimiento.getNumeroDocumentoOrigen());
        maps.addValue("@FechaDocumentoOrigen", movimiento.getFechaDocumentoOrigen());
        maps.addValue("@IdProveedor", movimiento.getIdProveedor());        
        maps.addValue("@IdTipoCompra", movimiento.getIdTipoCompra());        
        maps.addValue("@IdTipoProceso", movimiento.getIdTipoProceso());
        maps.addValue("@NumeroProceso", movimiento.getNumeroProceso());
        maps.addValue("@Referencia", movimiento.getReferencia());
        maps.addValue("@IdMovimientoIngreso", movimiento.getIdMovimientoIngreso());
        maps.addValue("@Activo", movimiento.getActivo());
        maps.addValue("@UsuarioCreacion", movimiento.getUsuarioCreacion());

        Map<String, Object> res = jdbcCall.execute(maps);
        int idMovimiento = (Integer) res.get("@IdMovimiento");
        movimiento.setIdMovimiento(idMovimiento);
    }

    @Override
    public void actualizar(Movimiento movimiento) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimiento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMovimiento", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroMovimiento", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRegistro", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenDestino", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumentoMov", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroDocumentoMov", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaRecepcion", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroDocumentoOrigen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaDocumentoOrigen", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProveedor", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoCompra", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroProceso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Referencia", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimientoIngreso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
         maps.addValue("@IdMovimiento", movimiento.getIdMovimiento());
        maps.addValue("@IdPeriodo", movimiento.getIdPeriodo());
        maps.addValue("@TipoMovimiento", movimiento.getTipoMovimiento().toString());
        maps.addValue("@NumeroMovimiento", movimiento.getNumeroMovimiento());
        maps.addValue("@FechaRegistro", movimiento.getFechaRegistro());
        maps.addValue("@IdAlmacenOrigen", movimiento.getIdAlmacenOrigen());
        maps.addValue("@IdAlmacenDestino", movimiento.getIdAlmacenDestino());
        maps.addValue("@IdConcepto", movimiento.getIdConcepto());
        maps.addValue("@IdTipoDocumentoMov", movimiento.getIdTipoDocumentoMov());
        maps.addValue("@NumeroDocumentoMov", movimiento.getNumeroDocumentoMov());
        maps.addValue("@FechaRecepcion", movimiento.getFechaRecepcion());
        maps.addValue("@IdDocumentoOrigen", movimiento.getIdDocumentoOrigen());
        maps.addValue("@NumeroDocumentoOrigen", movimiento.getNumeroDocumentoOrigen());
        maps.addValue("@FechaDocumentoOrigen", movimiento.getFechaDocumentoOrigen());
        maps.addValue("@IdProveedor", movimiento.getIdProveedor());        
        maps.addValue("@IdTipoCompra", movimiento.getIdTipoCompra());        
        maps.addValue("@IdTipoProceso", movimiento.getIdTipoProceso());
        maps.addValue("@NumeroProceso", movimiento.getNumeroProceso());
        maps.addValue("@Referencia", movimiento.getReferencia());
        maps.addValue("@IdMovimientoIngreso", movimiento.getIdMovimientoIngreso());
        maps.addValue("@Activo", movimiento.getActivo());
        maps.addValue("@UsuarioModificacion", movimiento.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Movimiento_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMovimiento", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdMovimiento", id);

        jdbcCall.execute(maps);
    }
    
    public class MovimientoRowMapper implements RowMapper<Movimiento> {

        @Override
        public Movimiento mapRow(ResultSet rs, int i) throws SQLException {
            Movimiento movimiento = new Movimiento();

            movimiento.setIdMovimiento(rs.getInt("IdMovimiento"));
            movimiento.setIdPeriodo(rs.getInt("IdPeriodo"));
            movimiento.setTipoMovimiento(TipoMovimiento.fromString(rs.getString("TipoMovimiento")));
            movimiento.setNumeroMovimiento(rs.getInt("NumeroMovimiento"));
            movimiento.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
            movimiento.setConcepto(new ProxyConcepto(rs.getInt("IdConcepto"), rs.getString("NombreConcepto")));
            movimiento.setAlmacenOrigen(new ProxyAlmacen(UtilDao.getIntegerFromNull(rs, "IdAlmacenOrigen"), UtilDao.getStringFromNull(rs, "AlmacenOrigen")));
            movimiento.setAlmacenDestino(new ProxyAlmacen(UtilDao.getIntegerFromNull(rs, "IdAlmacenDestino"), UtilDao.getStringFromNull(rs, "AlmacenDestino")));
            movimiento.setTipoDocumentoMov(new ProxyTipoDocumentoMov(UtilDao.getIntegerFromNull(rs, "IdTipoDocumentoMov"), UtilDao.getStringFromNull(rs, "NombreTipoDocumentoMov")));
            movimiento.setNumeroDocumentoMov(rs.getString("NumeroDocumentoMov"));
            movimiento.setFechaRecepcion(UtilDao.getTimestampFromNull(rs, "FechaRecepcion"));
            movimiento.setDocumentoOrigen(new ProxyDocumentoOrigen(UtilDao.getIntegerFromNull(rs, "IdDocumentoOrigen"), UtilDao.getStringFromNull(rs, "NombreDocumentoOrigen")));
            movimiento.setNumeroDocumentoOrigen(rs.getString("NumeroDocumentoOrigen"));
            movimiento.setFechaDocumentoOrigen(UtilDao.getTimestampFromNull(rs, "FechaDocumentoOrigen"));
            
            Integer idProveedor = UtilDao.getIntegerFromNull(rs, "IdProveedor");
            Proveedor proveedor = new Proveedor();
            if(idProveedor != null){                
                proveedor.setIdProveedor(idProveedor);
                proveedor.setRazonSocial(UtilDao.getStringFromNull(rs, "RazonSocial"));
            }
            movimiento.setProveedor(proveedor);
                       
            //new ProxyProveedor();
            movimiento.setTipoCompra(new ProxyTipoCompra(rs.getInt("IdTipoCompra")));
            movimiento.setTipoProceso(new ProxyTipoProceso(rs.getInt("IdTipoProceso")));
            movimiento.setNumeroProceso(rs.getString("NumeroProceso"));
            movimiento.setReferencia(rs.getString("Referencia"));
            movimiento.setIdMovimientoIngreso(UtilDao.getIntegerFromNull(rs, "IdMovimientoIngreso"));
            movimiento.setTotal(rs.getBigDecimal("Total"));
            movimiento.setActivo(rs.getInt("Activo"));

            return movimiento;
        }
    }

    public class IndicadorGestionRowMapper implements RowMapper<IndicadorGestionDaoDto> {

        @Override
        public IndicadorGestionDaoDto mapRow(ResultSet rs, int i) throws SQLException {
            IndicadorGestionDaoDto indicadorGestionDto = new IndicadorGestionDaoDto();

            indicadorGestionDto.setIdProducto(rs.getInt("IdProducto"));
            indicadorGestionDto.setDescripcion(rs.getString("Descripcion"));
            indicadorGestionDto.setCantidad(rs.getInt("Cantidad"));

            return indicadorGestionDto;
        }
    }

    public class IndicadorGestionDaoRowMapper implements RowMapper<IndicadorGestionDaoDto> {

        @Override
        public IndicadorGestionDaoDto mapRow(ResultSet rs, int i) throws SQLException {
            IndicadorGestionDaoDto indicadorGestionDaoDto = new IndicadorGestionDaoDto();

            indicadorGestionDaoDto.setIdProducto(rs.getInt("IdProducto"));
            indicadorGestionDaoDto.setDescripcion(rs.getString("Descripcion"));
            indicadorGestionDaoDto.setNombreFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            indicadorGestionDaoDto.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
            indicadorGestionDaoDto.setEstrSop(rs.getInt("EstrSop"));
            indicadorGestionDaoDto.setPetitorio(rs.getInt("Petitorio"));
            indicadorGestionDaoDto.setCantidad(rs.getInt("Cantidad"));

            return indicadorGestionDaoDto;
        }

    }

    public class MovimientoDtoRowMapper implements RowMapper<MovimientoDto> {

        @Override
        public MovimientoDto mapRow(ResultSet rs, int i) throws SQLException {
            MovimientoDto movimientoDto = new MovimientoDto();

            movimientoDto.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
            movimientoDto.setTipoMovimiento(TipoMovimiento.fromString(rs.getString("TipoMovimiento")));
            movimientoDto.setNumeroMovimiento(rs.getInt("NumeroMovimiento"));
            movimientoDto.setAlmacenOrigen(rs.getString("AlmacenOrigen"));
            movimientoDto.setAlmacenDestino(rs.getString("AlmacenDestino"));
            movimientoDto.setNombreConcepto(rs.getString("NombreConcepto"));
            movimientoDto.setTotal(rs.getBigDecimal("Total"));            
            movimientoDto.setFechaRecepcion(UtilDao.getTimestampFromNull(rs, "FechaRecepcion"));
            movimientoDto.setNombreTipoDocumento(rs.getString("NombreTipoDocumentoMov"));
            movimientoDto.setNumeroDocumentoMov(rs.getString("NumeroDocumentoMov"));
            movimientoDto.setRazonSocial(rs.getString("RazonSocial"));

            return movimientoDto;
        }
    }

    public class TarjetaControlDtoRowMapper implements RowMapper<TarjetaControlDto> {

        @Override
        public TarjetaControlDto mapRow(ResultSet rs, int i) throws SQLException {
            TarjetaControlDto tarjetaControlDto = new TarjetaControlDto();

            tarjetaControlDto.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
            tarjetaControlDto.setTipoMovimiento(TipoMovimiento.fromString(rs.getString("TipoMovimiento")));
            tarjetaControlDto.setNumeroMovimiento(rs.getInt("NumeroMovimiento"));
            tarjetaControlDto.setProducto(rs.getString("DescripcionProducto"));
            tarjetaControlDto.setNombreConcepto(rs.getString("NombreConcepto"));
            tarjetaControlDto.setLote(rs.getString("Lote"));
            tarjetaControlDto.setFechaVencimiento(UtilDao.getTimestampFromNull(rs, "FechaVencimiento"));
            tarjetaControlDto.setIngresos(rs.getInt("Ingresos"));
            tarjetaControlDto.setSalidas(rs.getInt("Salidas"));
            tarjetaControlDto.setSaldo(rs.getInt("Saldos"));
            
            tarjetaControlDto.setSaldoInicial(rs.getInt("SaldoInicial"));

            return tarjetaControlDto;
        }
    }

    public class IngresoAlmacenDtoRowMapper implements RowMapper<IngresoAlmacenDto> {

        @Override
        public IngresoAlmacenDto mapRow(ResultSet rs, int i) throws SQLException {
            IngresoAlmacenDto ingresoAlmacenDto = new IngresoAlmacenDto();

            ingresoAlmacenDto.setFechaRegistro(rs.getTimestamp("FechaRegistro"));
            ingresoAlmacenDto.setCodigoSismed(rs.getString("CodigoSismed"));
            ingresoAlmacenDto.setDescripcion(rs.getString("Descripcion"));

            return ingresoAlmacenDto;
        }

    }
}
