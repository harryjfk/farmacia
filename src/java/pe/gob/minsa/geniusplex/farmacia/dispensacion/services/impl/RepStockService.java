/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.farmacia.domain.Inventario;
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;
import pe.gob.minsa.farmacia.services.impl.InventarioProductoService;
import pe.gob.minsa.farmacia.services.impl.InventarioService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.RepStockDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.RemoteGpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpInventario;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpInventarioProducto;

/**
 *
 * @author armando
 */
@Service("repStockService")
public class RepStockService extends RemoteGpServiceManager<GpInventarioProducto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    @Autowired
    InventarioService invService;
    @Autowired
    InventarioProductoService invProdService;

    public RepStockService() {
        super(GpInventarioProducto.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }

    public RepStockDTO consultar(Inventario inventario) {
        List<InventarioProductoTotalDto> totales = invProdService.listarTotales(inventario.getIdInventario());
        RepStockDTO dto = new RepStockDTO(totales);
        return dto;
    }

    public Inventario obtenerInventario(int idPeriodo, int idAlmacen, Timestamp fechaProceso) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpInventario> cq = cb.createQuery(GpInventario.class);
        Root<GpInventario> fromInv = cq.from(GpInventario.class);
        cq.where(cb.equal(fromInv.get("periodo").get("idPeriodo"), idPeriodo),
                cb.equal(fromInv.get("almacen").get("idAlmacen"), idAlmacen),
                cb.equal(fromInv.get("fechaCierre"), fechaProceso));
        TypedQuery<GpInventario> query = em.createQuery(cq);
        GpInventario result;
        Inventario inventario = new Inventario();
        try {
            result = query.getSingleResult();
            popularInventario(inventario, result, fechaProceso, idAlmacen, idPeriodo);
        } catch (NoResultException e) {
            java.util.logging.Logger.getLogger(RepStockService.class.getName()).log(Level.INFO, null, e);
            for (GpInventario r : query.getResultList()) {
                if (!r.getInventarioProductoList().isEmpty()) {
                    inventario.setIdInventario(r.getIdInventario());
                    break;
                }
            }
            
        }
        return inventario;
    }

    private void popularInventario(Inventario inventario, GpInventario result, Timestamp fechaProceso, int idAlmacen, int idPeriodo) {
        inventario.setActivo(result.getActivo());
        inventario.setFechaProceso(fechaProceso);
        inventario.setIdAlmacen(idAlmacen);
        inventario.setIdInventario(result.getIdInventario());
        inventario.setIdPeriodo(idPeriodo);
        inventario.setNumeroInventario(result.getNumeroInventario());
        inventario.setUsuarioCreacion(result.getUsuarioCreacion());
        inventario.setUsuarioModificacion(result.getUsuarioModificacion());
        inventario.setFechaCierre(new Timestamp(result.getFechaCierre().getTime()));
    }

}
