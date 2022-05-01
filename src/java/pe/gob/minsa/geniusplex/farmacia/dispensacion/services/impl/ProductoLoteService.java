/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLotePk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.dtos.GpProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;

/**
 * Servicios para gestionar ProductoLotes
 *
 */
public class ProductoLoteService extends GpServiceManager<ProductoLote> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private GpAlmacenService almacenService;

    public ProductoLoteService() {
        super(ProductoLote.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    @Override
    public List<ProductoLote> listar() {
        TypedQuery<GpMovimientoProducto> q = em.createQuery("SELECT mv FROM GpMovimientoProducto mv WHERE mv.idMovimiento.idAlmacenDestino != NULL", GpMovimientoProducto.class);
        List<GpMovimientoProducto> resultList = q.getResultList();
        ArrayList<ProductoLote> lista = new ArrayList<ProductoLote>();

        for (GpMovimientoProducto gpMovimientoProducto : resultList) {
            if (gpMovimientoProducto.getIdProducto() != null) {
                lista.add(new ProductoLote(gpMovimientoProducto));
            }
        }
        return lista;
    }

    public List<ProductoLote> listarPorModulo(long idModulo) {
        List<GpAlmacen> almacenes = almacenService.listarPorModulo(idModulo);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpMovimientoProducto> cq = cb.createQuery(GpMovimientoProducto.class);
        Root<GpMovimientoProducto> MOV_PRO = cq.from(GpMovimientoProducto.class);
        cq.where(MOV_PRO.<GpMovimiento>get("idMovimiento").<GpAlmacen>get("idAlmacenDestino").in(almacenes));
        TypedQuery<GpMovimientoProducto> q = em.createQuery(cq);

        List<GpMovimientoProducto> resultList = q.getResultList();
        ArrayList<ProductoLote> lista = new ArrayList<ProductoLote>();

        for (GpMovimientoProducto gpMovimientoProducto : resultList) {
            if (gpMovimientoProducto.getIdProducto() != null) {
                lista.add(new ProductoLote(gpMovimientoProducto));
            }
        }
        return lista;
    }

    public List<GpProductoDTO> listarConStock() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpProductoDTO> cq = cb.createQuery(GpProductoDTO.class);
        Root<GpProducto> fromProducto = cq.from(GpProducto.class);
        cq.where(//TODO Quizas haya que filtrar por almacen 
                //(ahora lista todos los medicamentos)
                cb.equal(fromProducto.get("idTipoProducto").get("idTipoProducto"), 2),
                cb.equal(fromProducto.get("activo"), 1)
        ).select(cb.construct(GpProductoDTO.class, fromProducto))
                .orderBy(cb.asc(fromProducto.get("idTipoProducto")));

        List<GpProductoDTO> lista = em.createQuery(cq).getResultList();
        return lista;
    }

    public List<ProductoLote> encontrarPorProducto(GpProducto prod) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpMovimientoProducto> cq = cb.createQuery(GpMovimientoProducto.class);
        cq.where(cb.equal(cq.from(GpMovimientoProducto.class).get("idProducto"), prod));
        TypedQuery<GpMovimientoProducto> q = em.createQuery(cq);
        List<GpMovimientoProducto> resultList = q.getResultList();
        ArrayList<ProductoLote> lista = new ArrayList<ProductoLote>();

        for (GpMovimientoProducto gpMovimientoProducto : resultList) {
            if (gpMovimientoProducto.getIdProducto() != null) {
                lista.add(new ProductoLote(gpMovimientoProducto));
            }
        }
        return lista;
    }

    public List<ProductoLote> encontrarPorAlmacen(int almacen) {
        TypedQuery<GpMovimientoProducto> q = em.createQuery("SELECT mv FROM GpMovimientoProducto mv WHERE mv.idMovimiento.idAlmacenDestino.idAlmacen =:idAlmacen", GpMovimientoProducto.class);
        List<GpMovimientoProducto> resultList = q.setParameter("idAlmacen", almacen).getResultList();
        ArrayList<ProductoLote> lista = new ArrayList<ProductoLote>();

        for (GpMovimientoProducto gpMovimientoProducto : resultList) {
            if (gpMovimientoProducto.getIdProducto() != null) {
                lista.add(new ProductoLote(gpMovimientoProducto));
            }
        }
        return lista;
    }

    @Override
    public ProductoLote obtenerPorId(Object id) {
        ProductoLotePk pk = (ProductoLotePk) id;
        TypedQuery<GpMovimientoProducto> q = em.createQuery("SELECT mv FROM GpMovimientoProducto mv WHERE mv.idMovimiento.idAlmacenDestino.idAlmacen =:idAlmacen AND mv.idProducto.idProducto = :idProducto AND mv.lote = :lote", GpMovimientoProducto.class);
        q.setParameter("idProducto", pk.getIdProducto());
        q.setParameter("lote", pk.getIdLote());
        List<GpMovimientoProducto> resultList = q.setParameter("idAlmacen", pk.getIdAlmacen()).getResultList();
        //TODO Esto puede devolver resultList con producto null, no se por que ...
        if (!resultList.isEmpty()) {
            return new ProductoLote(resultList.get(0));
        }
        return null;
    }

}
