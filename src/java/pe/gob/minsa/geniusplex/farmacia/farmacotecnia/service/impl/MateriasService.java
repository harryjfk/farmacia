package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;

@Service("materiasService")
public class MateriasService extends GpServiceManager<Materias> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public MateriasService() {
        super(Materias.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Materias> listarModulo(Object idModulo) {
        ListaModulo<Materias> listaModulo = new ListaModulo<Materias>(Materias.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Materias> cq = cb.createQuery(entityClass);
        Root<Materias> fromMat = cq.from(entityClass);
        Predicate conditions = queryListar(cb, fromMat, idModulo);
        cq.where(conditions);
        TypedQuery<Materias> query = em.createQuery(cq);
        return query.getResultList();
    }

    private Predicate queryListar(CriteriaBuilder cb, Root<Materias> fromMat, Object idModulo) {
        Predicate conditions = cb.and(
                cb.equal(fromMat.get("IdModulo"), idModulo));
        return conditions;
    }

    public List<Materias> listarPorModuloAlmacen(Object idModulo, Object almacen) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Materias> cq = cb.createQuery(entityClass);
        Root<Materias> fromMat = cq.from(entityClass);
        Predicate conditions = queryListar(cb, fromMat, idModulo);
        if (almacen != null) {
            conditions = cb.and(conditions, cb.equal(fromMat.get("almacen"), almacen));
        }
        cq.where(conditions);
        TypedQuery<Materias> query = em.createQuery(cq);
        List<Materias> lista = query.getResultList();
        return lista;
    }

    public List<Materias> ConsultaMaterias(Object idModulo, Integer almacen, Date start, Date end) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Materias> cq = cb.createQuery(entityClass);
        Root<Materias> MAT = cq.from(entityClass);

        Predicate[] conds = new Predicate[]{cb.equal(MAT.get("IdModulo"), idModulo)};
        if (almacen != null && almacen > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.equal(MAT.get("almacen").get("idAlmacen"), almacen));
        }
        if (start != null) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.greaterThanOrEqualTo(MAT.<Date>get("fechaCreacion"), start));
        }
        if (end != null) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.lessThanOrEqualTo(MAT.<Date>get("fechaCreacion"), end));
        }
        cq.where(conds);
        TypedQuery<Materias> query = em.createQuery(cq);
        List<Materias> lista = query.getResultList();
        return lista;
    }
}
