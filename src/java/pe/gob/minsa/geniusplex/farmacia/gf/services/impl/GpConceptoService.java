
package pe.gob.minsa.geniusplex.farmacia.gf.services.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpConcepto;

@Service("gpConceptoService")
public class GpConceptoService extends GpServiceManager<GpConcepto> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    
    public GpConceptoService() {
        super(GpConcepto.class);
    }
    
    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }
    
    public List<GpConcepto> listarConceptosActivos(Character type) {
        TypedQuery<GpConcepto> query = em.createQuery("SELECT c FROM GpConcepto c WHERE c.tipoMovimientoConcepto = :type", entityClass);
        return query.setParameter("type", type).getResultList();
    }
    
}
