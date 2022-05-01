/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.PRM;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.helpers.JpaCriteriaHelper;

/**
 *
 * @author stark
 */
@Service("prmService")
public class PRMService extends GpServiceManager<PRM> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public PRMService() {
        super(PRM.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<PRM> listarModulo(Object idModulo) {
        ListaModulo<PRM> listaModulo = new ListaModulo<PRM>(PRM.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);

    }

    public List<PRM> listarPorModuloConParams(Object idModulo, String servicio) {
        TypedQuery<PRM> q = em.createQuery("SELECT m FROM PRM m WHERE m.IdModulo = :idModulo AND m.servicio = :servicio ", PRM.class);
        q.setParameter("servicio", servicio);
        List<PRM> lista = q.setParameter("idModulo", idModulo).getResultList();
        return lista;
    }

    public List<PRM> ConsultaPRM(long idModulo, String paciente, Date startDate, Date endDate) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PRM> cq = cb.createQuery(entityClass);

        Root<PRM> fromPRM = cq.from(entityClass);
        Predicate[] predicates = new Predicate[0];

        predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromPRM.get("IdModulo"), idModulo));
        if (paciente != null && paciente.length() > 0 && !paciente.equals("0")) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.equal(fromPRM.get("paciente").get("paciente"), paciente));
        }
        if (startDate != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.greaterThanOrEqualTo(fromPRM.<Date>get("fecha"), startDate));
        }
        if (endDate != null) {
            predicates = JpaCriteriaHelper.agregarPredicado(predicates, cb.lessThanOrEqualTo(fromPRM.<Date>get("fecha"), endDate));
        }

        cq.where(predicates);
        TypedQuery<PRM> query1 = em.createQuery(cq);

        return query1.getResultList();
    }
}
