package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.dto.AlmacenTree;
import pe.gob.minsa.farmacia.util.UtilDao;

public class AlmacenDao implements DaoManager<Almacen> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Almacen> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Almacen> almacenes = jdbcTemplate.query("{call Far_Almacen_Listar}", new AlmacenRowMapper());
        return almacenes;
    }

    public List<Almacen> listarPadres() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Almacen> almacenes = jdbcTemplate.query("{call Far_Almacen_ListarPadres}", new AlmacenRowMapper());

        return almacenes;
    }

    public List<Almacen> listarPorPadre(int idAlmacenPadre) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Almacen> almacenes = jdbcTemplate.query("{call Far_Almacen_ListarPorPadre(?)}", new Object[]{idAlmacenPadre}, new AlmacenRowMapper());

        return almacenes;
    }

    @Override
    public Almacen obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Almacen almacen = jdbcTemplate.queryForObject("{call Far_Almacen_ListarPorId(?)}", new Object[]{id}, new AlmacenRowMapper());
            return almacen;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    public int obtenerIdAlmacenLogistica() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_ObtenerIdAlmacenLogistica");
        jdbcCall.addDeclaredParameter(new SqlOutParameter("@ID", java.sql.Types.INTEGER));        
        
        Map<String, Object> resultMap = jdbcCall.execute();
        return (Integer)resultMap.get("@ID");
    }
    
    public int obtenerIdAlmacenEspecializado() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_ObtenerIdAlmacenEspecializado");
        jdbcCall.addDeclaredParameter(new SqlOutParameter("@ID", java.sql.Types.INTEGER));
        
        Map<String, Object> resultMap = jdbcCall.execute();
        return (Integer)resultMap.get("@ID");
    }
        
    public List<AlmacenTree> cargarTree(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<AlmacenTree> almacenTrees = new ArrayList<AlmacenTree>();
        
       List<Map<String, Object>> rows = jdbcTemplate.queryForList("{call Far_Almacen_Tree}");
       
        for (Map<String, Object> row : rows) {
            AlmacenTree almacenTree = new AlmacenTree();
            almacenTree.setAlmacen((String) row.get("Almacen"));
            almacenTree.setIdAlmacen((Integer) row.get("IdAlmacen"));
            almacenTree.setIdTipoAlmacen((Integer)row.get("IdTipoAlmacen"));
            Integer IdAlmacenPadre = (Integer) row.get("IdAlmacenPadre");
            
            if(IdAlmacenPadre == null){                
                almacenTrees.add(almacenTree);
            }else{
                for(int i = 0; i <= almacenTrees.size() - 1; ++i){
                    if(almacenTrees.get(i).getIdAlmacen() == IdAlmacenPadre){
                        almacenTrees.get(i).getAlmacenes().add(almacenTree);
                        break;
                    }
                }
            }            
        }
       
        return almacenTrees;
    }

    @Override
    public void insertar(Almacen almacen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenPadre", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Direccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fax", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Telefono", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Ruc", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUbigeo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Responsable", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Farmacia", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoAlmacen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        
        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdAlmacen", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdAlmacenPadre", almacen.getIdAlmacenPadre());
        maps.addValue("@IdTipoAlmacen", almacen.getIdTipoAlmacen());
        maps.addValue("@Descripcion", almacen.getDescripcion());
        maps.addValue("@Abreviatura", almacen.getAbreviatura());
        maps.addValue("@Direccion", almacen.getDireccion());        
        maps.addValue("@Fax", almacen.getFax());
        maps.addValue("@Telefono", almacen.getTelefono());
        maps.addValue("@Ruc", almacen.getRuc());
        maps.addValue("@IdUbigeo", almacen.getIdUbigeo());
        maps.addValue("@Responsable", almacen.getResponsable());
        maps.addValue("@Farmacia", almacen.getFarmacia());
        maps.addValue("@CodigoAlmacen", almacen.getCodigoAlmacen());
        maps.addValue("@Activo", almacen.getActivo());
        maps.addValue("@UsuarioCreacion", almacen.getUsuarioCreacion());

        Map<String, Object> res = jdbcCall.execute(maps);
        int idAlmacen = (Integer) res.get("@IdAlmacen");
        almacen.setIdAlmacen(idAlmacen);
    }

    @Override
    public void actualizar(Almacen almacen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenPadre", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Descripcion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Direccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fax", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Telefono", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Ruc", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUbigeo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Responsable", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Farmacia", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoAlmacen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdAlmacen", almacen.getIdAlmacen());
        maps.addValue("@IdAlmacenPadre", almacen.getIdAlmacenPadre());
        maps.addValue("@IdTipoAlmacen", almacen.getIdTipoAlmacen());
        maps.addValue("@Descripcion", almacen.getDescripcion());
        maps.addValue("@Abreviatura", almacen.getAbreviatura());
        maps.addValue("@Direccion", almacen.getDireccion());
        maps.addValue("@Fax", almacen.getFax());
        maps.addValue("@Telefono", almacen.getTelefono());
        maps.addValue("@Ruc", almacen.getRuc());
        maps.addValue("@IdUbigeo", almacen.getIdUbigeo());
        maps.addValue("@Responsable", almacen.getResponsable());
        maps.addValue("@Farmacia", almacen.getFarmacia());
        maps.addValue("@CodigoAlmacen", almacen.getCodigoAlmacen());
        maps.addValue("@Activo", almacen.getActivo());
        maps.addValue("@UsuarioModificacion", almacen.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }
    
     public void actualizarVirtualEstado(int idAlmacenPadre, int activo, int usuarioModificacion) {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
		        .withProcedureName("Far_Almacen_VirtualEstado");

            jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacenPadre", java.sql.Types.INTEGER));
            jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
            jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@IdAlmacenPadre", idAlmacenPadre);
            maps.addValue("@Activo", activo);
            maps.addValue("@UsuarioModificacion", usuarioModificacion);

            jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdAlmacen", id);

        jdbcCall.execute(maps);
    }

    public boolean esUsado(int id) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_EsUsado");            
        
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdAlmacen", id);
        
        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }
    
    /**
     * Método que devuelve la confirmación de existencia del código del almacén
     *
     * @param codigoAlmacen El codigo del almacén a buscar 
     * @param idAlmacen El almacén del cual se va a verificar, para registrar debemos pasar 0 
     * y para modificacion el id del almacén a editar     
     * @return Estado de existencia del código de almacén consultado
     */
    public boolean existeCodigo(String codigoAlmacen, int idAlmacen){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Almacen_ExisteCodigo");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoAlmacen", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdAlmacen", idAlmacen);
        maps.addValue("@CodigoAlmacen", codigoAlmacen);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }

    public class AlmacenRowMapper implements RowMapper<Almacen> {

        @Override
        public Almacen mapRow(ResultSet rs, int i) throws SQLException {
            Almacen almacen = new Almacen();

            almacen.setIdAlmacen(rs.getInt("IdAlmacen"));
            almacen.setIdAlmacenPadre(UtilDao.getIntegerFromNull(rs, "IdAlmacenPadre"));
            almacen.setCantidadHijos(rs.getInt("CantidadHijos"));
            almacen.setIdTipoAlmacen(rs.getInt("IdTipoAlmacen"));
            almacen.setDescripcion(rs.getString("Descripcion"));
            almacen.setAbreviatura(rs.getString("Abreviatura"));
            almacen.setDireccion(rs.getString("Direccion"));            
            almacen.setFax(rs.getString("Fax"));
            almacen.setTelefono(rs.getString("Telefono"));
            almacen.setRuc(rs.getString("Ruc"));
            almacen.setIdUbigeo(rs.getString("IdUbigeo"));
            almacen.setResponsable(rs.getString("Responsable"));
            almacen.setFarmacia(rs.getInt("Farmacia"));
            almacen.setCodigoAlmacen(rs.getString("CodigoAlmacen"));
            almacen.setActivo(rs.getInt("Activo"));

            return almacen;
        }
    }        
}
