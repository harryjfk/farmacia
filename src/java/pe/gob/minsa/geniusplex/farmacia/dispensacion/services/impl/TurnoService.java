/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;

/**
 *
 * @author stark
 */
public class TurnoService extends GpServiceManager<Turno> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;

    public TurnoService() {
        super(Turno.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public List<Turno> listarModulo(Object idModulo) {
        ListaModulo<Turno> listaModulo = new ListaModulo<Turno>(Turno.class);
        listaModulo.setEntityManager(emf.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);

    }

    public List<Turno> listarModuloFarmacia(Object idModulo, Object idFarmacia) {
        TypedQuery<Turno> q = em.createQuery("SELECT t FROM Turno t WHERE t.IdModulo = :idModulo AND t.farmacia = :idFarmacia", Turno.class);
        q.setParameter("idModulo", idModulo);
        List<Turno> lista = q.setParameter("idFarmacia", idFarmacia).getResultList();
        return lista;
    }

    public List<Turno> TurnosSolapados(Object idModulo, Date inicio, Date finale) {
        TypedQuery<Turno> q = em.createQuery("SELECT t FROM Turno t WHERE t.IdModulo = :idModulo AND ((:inicio >= t.horaInicio AND :inicio < t.horaFinal) OR (:final > t.horaInicio AND :final <= t.horaFinal) OR (t.horaInicio >= :inicio AND t.horaInicio < :final) OR (t.horaFinal > :inicio AND t.horaFinal <= :final))", Turno.class);
        q.setParameter("idModulo", idModulo);
        q.setParameter("final", finale);
        List<Turno> lista = q.setParameter("inicio", inicio).getResultList();
        return lista;
    }

    public Turno obtenerPorHora(Date hora, long idModulo) {

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(hora);
        String horaStr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
        Date hr = hora;
        try {
            hr = sdf.parse(horaStr);
        } catch (ParseException ex) {
            Logger.getLogger(TurnoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Turno> cq = cb.createQuery(entityClass);
        Root<Turno> fromTurno = cq.from(entityClass);

        cq.where(
                cb.or(
                        cb.and(
                                cb.lessThanOrEqualTo(fromTurno.<Date>get("horaInicio"), hr),
                                cb.greaterThanOrEqualTo(fromTurno.<Date>get("horaFinal"), hr)),
                        cb.and(
                                cb.greaterThanOrEqualTo(fromTurno.<Date>get("horaFinal"), hr),
                                cb.lessThanOrEqualTo(fromTurno.<Date>get("horaFinal"), fromTurno.<Date>get("horaInicio"))
                        )
                ),
                cb.equal(fromTurno.get("IdModulo"), idModulo));

        TypedQuery<Turno> query = em.createQuery(cq);
        query.setMaxResults(1);
        return !query.getResultList().isEmpty() ? query.getResultList().get(0) : null;
    }
}
