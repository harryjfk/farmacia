/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PerfilService;
import pe.gob.minsa.farmacia.services.impl.UsuarioService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpPersonalService;

/**
 * Servicios para gestionar Vendedores
 *
 */
public class VendedorService extends GpServiceManager<Vendedor> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GpPersonalService personalService;

    public VendedorService() {
        super(Vendedor.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    public List<Vendedor> listarModulo(Object idModulo) {
        ListaModulo<Vendedor> listaModulo = new ListaModulo<Vendedor>(Vendedor.class);
        listaModulo.setEntityManager(entityManagerFactory.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }

    public List<Vendedor> listarModulo(Object idModulo, long idTurno) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vendedor> cq = cb.createQuery(entityClass);
        Root<Vendedor> ven = cq.from(entityClass);
        cq.where(cb.equal(ven.get("IdModulo"), idModulo), cb.equal(ven.get("turnos").get("idTurno"), idTurno));

        TypedQuery<Vendedor> query = em.createQuery(cq);
        return query.getResultList();
    }

    public Vendedor obtenerPorPersonalYModulo(String personal, Long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vendedor> cq = cb.createQuery(entityClass);
        Root<Vendedor> fromVen = cq.from(entityClass);
        if (idModulo != null) {
            cq.where(cb.equal(fromVen.get("personal"), personal), cb.equal(fromVen.get("IdModulo"), idModulo));
        } else {
            cq.where(cb.equal(fromVen.get("personal"), personal));
        }
        TypedQuery<Vendedor> query = em.createQuery(cq);
        List<Vendedor> resultList = query.setMaxResults(1).getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public Usuario crearUsuario(GpPersonal personal, String username, String password, Usuario usuarioCreacion) throws BusinessException {
        Usuario usuarioNew = new Usuario();
        List<Perfil> perfiles = perfilService.listar();

        Perfil perfilCajero = null;
        for (Perfil perfil : perfiles) {
            if (perfil.getNombrePerfil().equalsIgnoreCase("Cajero")) {
                perfilCajero = perfil;
                break;
            }
        }

        if (perfilCajero == null) {
            perfilCajero = new Perfil();
            perfilCajero.setNombrePerfil("Cajero");
            perfilCajero.setActivo(1);
            perfilCajero.setUsuarioCreacion(usuarioCreacion.getIdUsuario());
            perfilService.insertar(perfilCajero);

            perfiles = perfilService.listar();
            perfilCajero = null;
            for (Perfil perfil : perfiles) {
                if (perfil.getNombrePerfil().equalsIgnoreCase("Cajero")) {
                    perfilCajero = perfil;
                    break;
                }
            }
        }

        Personal per = new Personal();
            per.setIdPersonal(personal.getPersonal());
            per.setApellidoMaterno(personal.getApellidoMaterno());
            per.setApellidoPaterno(personal.getApellidoPaterno());
            per.setNombre(personal.getNombres());
        
        Query query = em.createNativeQuery("SELECT * FROM Far_Usuario u WHERE u.IdPersonal LIKE '%" + personal.getPersonal() + "%'");
        List uData = query.getResultList();
        if (!uData.isEmpty()) {
            Object[] obj = (Object[])uData.get(0);
            usuarioNew.setIdUsuario((Integer)obj[0]);
            usuarioNew.setPersonal(per);
            usuarioNew.setNombreUsuario(String.valueOf(obj[2]));
            usuarioNew.setClave(String.valueOf(obj[3]));
            usuarioNew.setCorreo(String.valueOf(obj[4]));
            usuarioNew.setActivo((Integer)obj[5]);
            usuarioNew.setUsuarioCreacion((Integer)obj[6]);
            
            query = em.createNativeQuery("SELECT DISTINCT p.* FROM Far_Usuario u, Far_Perfil p, Far_Usuario_Perfil pu WHERE u.IdPersonal LIKE '%" + personal.getPersonal() + "%' AND p.IdPerfil = pu.IdPerfil AND pu.IdUsuario = u.IdUsuario");
            List pData = query.getResultList();
            
            List<Perfil>perfils = new ArrayList<Perfil>();
            for (Object profile : pData) {
                Object[] pobj = (Object[])profile;
                Perfil p = new Perfil();
                p.setIdPerfil((Integer)pobj[0]);
                p.setNombrePerfil(String.valueOf(pobj[1]));
                p.setActivo((Integer)pobj[2]);
                p.setUsuarioCreacion((Integer)pobj[3]);
                
                //El usuario ya tiene ese perfil (poco probable pero es mejor prevenir)
                if(perfilCajero != null && p.getIdPerfil() == perfilCajero.getIdPerfil())
                    return usuarioNew;
                else
                    perfils.add(p);
            }
            perfils.add(perfilCajero);
            usuarioNew.setPerfiles(perfils);
            usuarioService.actualizar(usuarioNew);
        } else {

            usuarioNew.setNombreUsuario(username);
            usuarioNew.setCorreo(username + "@correoinvalido.farmacia.gob.pe");
            usuarioNew.setClave(password);
            usuarioNew.setUsuarioCreacion(usuarioCreacion.getIdUsuario());
            
            usuarioNew.setPersonal(per);

            usuarioNew.setPerfiles(Arrays.asList(perfilCajero));
            usuarioNew.setActivo(1);
            usuarioService.insertar(usuarioNew);
        }

        return usuarioNew;
    }

    public void setCredentials(Vendedor vendedor) {
        Query query = em.createNativeQuery("SELECT u.NombreUsuario, u.Clave FROM Far_Usuario u "
                + "INNER JOIN Far_Vendedor v ON v.Personal = u.IdPersonal "
                + "WHERE v.Personal LIKE '%" + vendedor.getPersonal().trim() + "%'");
        List<Object[]> usuarios = query.getResultList();
        for (Object[] usuario : usuarios) {
            vendedor.setUsername((String) usuario[0]);
            vendedor.setPassword((String) usuario[1]);
            return;
        }
    }

    public boolean actualizarUsuario(Vendedor vendedor, String userNameNew, String passwordNew, Usuario usuarioMod) {
        Query query = em.createNativeQuery("SELECT u.IdUsuario FROM Far_Usuario u "
                + "INNER JOIN Far_Vendedor v ON v.Personal = u.IdPersonal "
                + "WHERE v.Personal LIKE '%" + vendedor.getPersonal().trim() + "%'");
        final List resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            Integer idUsuario = (Integer) resultList.get(0);
            try {
                Usuario usuario = usuarioService.obtenerPorId(idUsuario);
                usuario.setClave(passwordNew);
                usuario.setNombreUsuario(userNameNew);
                usuario.setUsuarioModificacion(usuarioMod.getIdUsuario());
                usuarioService.actualizar(usuario);
                return true;
            } catch (BusinessException ex) {
                Logger.getLogger(VendedorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            GpPersonal personal = personalService.obtenerPorId(vendedor.getPersonal());
            if (personal != null) {
                try {
                    crearUsuario(personal, userNameNew, passwordNew, usuarioMod);
                    return true;
                } catch (BusinessException ex) {
                    Logger.getLogger(VendedorService.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }

        return false;
    }

    public Vendedor validarVendedor(Vendedor vendedor, long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vendedor> cq = cb.createQuery(entityClass);
        Root<Vendedor> from = cq.from(entityClass);

        cq.where(
                cb.equal(from.get("personal"), vendedor.getPersonal()),
                cb.equal(from.get("IdModulo"), idModulo)
        );
        TypedQuery<Vendedor> query = em.createQuery(cq);
        try {
            vendedor = query.getSingleResult();
        } catch (NoResultException e) {
            vendedor.setIdModulo(idModulo);
            super.insertar(vendedor);
            java.util.logging.Logger.getLogger(VendedorService.class.getName()).log(Level.INFO, null, e);
        } catch (NonUniqueResultException e) {
            java.util.logging.Logger.getLogger(VendedorService.class.getName()).log(Level.SEVERE, "Hay dos presriptores en el sistema con el mismo nombre", e);
        }
        return vendedor;
    }

}
