
package pe.gob.minsa.geniusplex.farmacia.gf.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;

@Service("gpAlmacenService")
public class GpAlmacenService extends GpServiceManager<GpAlmacen> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    
    public GpAlmacenService() {
        super(GpAlmacen.class);
    }
    
    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public List<GpAlmacen>  listarFisicos() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpAlmacen> cq = cb.createQuery(entityClass);
        Root<GpAlmacen> from = cq.from(entityClass);
        cq.where(cb.isNull(from.get("idAlmacenPadre")));
        TypedQuery<GpAlmacen> query = em.createQuery(cq);
        return query.getResultList();
    }
    public List<GpAlmacen>  listarVirtuales(int idPadre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpAlmacen> cq = cb.createQuery(entityClass);
        Root<GpAlmacen> from = cq.from(entityClass);
        cq.where(
                cb.isNull(from.get("idAlmacenPadre")).not(), 
                cb.equal(from.get("idAlmacenPadre").get("idAlmacen"), idPadre));
        TypedQuery<GpAlmacen> query = em.createQuery(cq);
        return query.getResultList();
    }
    
    public List<GpAlmacen> listarPorModulo(long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GpAlmacen> cq = cb.createQuery(entityClass);
        Root<GpAlmacen> from = cq.from(entityClass);
        
        cq.where(cb.isNotNull(from.get("idAlmacenPadre")), cb.equal(from.get("farmacia"), idModulo));
        return em.createQuery(cq).getResultList();
    }
    
    
}
