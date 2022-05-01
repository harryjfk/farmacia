package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Matriz;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.MatrizMateria;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;

@Service("matrizService")
public class MatrizService extends GpServiceManager<Matriz> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public MatrizService() {
        super(Matriz.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Matriz> listarModulo(Object idModulo) {
        ListaModulo<Matriz> listaModulo = new ListaModulo<Matriz>(Matriz.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }

    public List<Matriz> ConsultaMatriz(Object idModulo, String descripcion, Date start, Date end) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Matriz> cq = cb.createQuery(Matriz.class);
        Root<Matriz> MAT = cq.from(entityClass);
        Predicate[] conds = new Predicate[]{cb.equal(MAT.get("IdModulo"), idModulo)};
        if (descripcion != null && descripcion.length() > 0) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.like(MAT.<String>get("descripcion"), "%" + descripcion + "%"));
        }
        if (start != null) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.greaterThanOrEqualTo(MAT.<Date>get("fechaCreacion"), start));
        }
        if (end != null) {
            conds = JpaCriteriaHelper.agregarPredicado(conds, cb.lessThanOrEqualTo(MAT.<Date>get("fechaCreacion"), end));
        }
        cq.where(conds);
        TypedQuery<Matriz> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Matriz> listarConInsumos(long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Matriz> cq = cb.createQuery(entityClass);
        Root<Matriz> fromMatriz = cq.from(entityClass);
        cq.where(
                cb.equal(fromMatriz.get("IdModulo"), idModulo),
                cb.gt(cb.size(fromMatriz.<List<Materias>>get("insumos")), 0)
        );

        TypedQuery<Matriz> query = em.createQuery(cq);
        List<Matriz> matrices = query.getResultList();
        return matrices;
    }

    public boolean hasInsumo(Matriz matriz, Materias insumo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        cq.select(cb.literal(true));
        Root<Matriz> fromM = cq.from(Matriz.class);

        Subquery<MatrizMateria> sq = cq.subquery(MatrizMateria.class);
        Root<MatrizMateria> fromMm = sq.from(MatrizMateria.class);
        Join<MatrizMateria, Matriz> joinMm = fromMm
                .<MatrizMateria, Matriz>join("matriz");

        sq.where(
                cb.equal(fromMm.get("matriz"), matriz),
                cb.equal(fromMm.get("insumo"), insumo),
                cb.equal(fromM, joinMm)
        );
        cq.where(cb.exists(sq));

        TypedQuery<Boolean> query = em.createQuery(cq);
        try {
            return query.getSingleResult();
        } catch(NoResultException ex) {
            return false;
        }
    }

    public MatrizMateria getInsumo(Matriz matriz, Materias insumo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MatrizMateria> cq = cb.createQuery(MatrizMateria.class);
        Root<MatrizMateria> fromMm = cq.from(MatrizMateria.class);
        cq.where(
                cb.equal(fromMm.get("matriz"), matriz),
                cb.equal(fromMm.get("insumo"), insumo)
        );
        TypedQuery<MatrizMateria> query = em.createQuery(cq);
        return query.getSingleResult();
    }

    public boolean eliminarInsumo(Matriz matriz, MatrizMateria insumo) {
        try {
            em.getTransaction().begin();
            matriz.getMatrizInsumos().remove(insumo);
            em.remove(insumo);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatrizService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
