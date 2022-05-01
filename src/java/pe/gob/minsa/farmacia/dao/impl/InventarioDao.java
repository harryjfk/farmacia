package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.Inventario;

public class InventarioDao {

    @Autowired
    DataSource dataSource;

    public Inventario obtener(int idPeriodo, int idAlmacen, Timestamp fechaProceso, int idTipoProceso) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Consulta");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaProceso", Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", Types.INTEGER));

        jdbcCall.declareParameters(new SqlReturnResultSet("#inventarioRowMapper", new InventarioRowMapper()));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@FechaProceso", fechaProceso);
        maps.addValue("@IdTipoProceso", idTipoProceso);

        Map<String, Object> resultMap = jdbcCall.execute(maps);

        List<Inventario> inventarios = (List<Inventario>) resultMap.get("#inventarioRowMapper");
        if (inventarios.isEmpty()) {
            return new Inventario();
        } else {
            return inventarios.get(0);
        }
    }
    
    public boolean existe(int idPeriodo, int idAlmacen, Timestamp fechaProceso, int idTipoProceso) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Existe");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaProceso", Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@FechaProceso", fechaProceso);
        maps.addValue("@IdTipoProceso", idTipoProceso);
        
        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }

    public int obtenerCorrelativoNumeroInventario(int idPeriodo){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_NumeroInventario");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@NumeroInventario", Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", idPeriodo);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Integer) resultMap.get("@NumeroInventario"));        
    }
    
    public void insertar(Inventario inventario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Inventario_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPeriodo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NumeroInventario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaProceso", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@FechaCierre", java.sql.Types.TIMESTAMP));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));

        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdInventario", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPeriodo", inventario.getIdPeriodo());
        maps.addValue("@NumeroInventario", inventario.getNumeroInventario());
        maps.addValue("@IdAlmacen", inventario.getIdAlmacen());
        maps.addValue("@FechaProceso", inventario.getFechaProceso());
        maps.addValue("@FechaCierre", inventario.getFechaCierre());
        maps.addValue("@Activo", inventario.getActivo());
        maps.addValue("@UsuarioCreacion", inventario.getUsuarioCreacion());
        maps.addValue("@IdTipoProceso", inventario.getIdTipoProceso());

        Map<String, Object> res = jdbcCall.execute(maps);
        int idInventario = (Integer) res.get("@IdInventario");
        inventario.setIdInventario(idInventario);
    }

    public class InventarioRowMapper implements RowMapper<Inventario> {

        @Override
        public Inventario mapRow(ResultSet rs, int i) throws SQLException {
            
            Inventario inventario = new Inventario();
            inventario.setIdInventario(rs.getInt("IdInventario"));
            inventario.setIdPeriodo(rs.getInt("IdPeriodo"));
            inventario.setNumeroInventario(rs.getInt("NumeroInventario"));
            inventario.setIdAlmacen(rs.getInt("IdAlmacen"));
            inventario.setFechaProceso(rs.getTimestamp("FechaProceso"));
            inventario.setFechaCierre(rs.getTimestamp("FechaCierre"));
            inventario.setActivo(rs.getInt("Activo"));
            
            return inventario;   
        }
    }
}
