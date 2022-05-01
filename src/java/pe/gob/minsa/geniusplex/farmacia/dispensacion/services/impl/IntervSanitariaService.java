/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitaria;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitariaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.IntSanKitProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;

/**
 *
 * @author armando
 */
@Service("intervSanitariaService")
public class IntervSanitariaService extends GpServiceManager<IntervSanitaria> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    @Autowired
    private ProductoHelper productoHelper;
    @Autowired
    private PersonalService personalService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    GpAlmacenService almacenService;
    @Autowired
    private ProductoHelper pHelper;

    public IntervSanitariaService() {
        super(IntervSanitaria.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
    }

    public boolean guardarCambios(IntervSanitaria interv, long idModulo) {
        if (interv.getNroSalida() != null && interv.getNroSalida().length() > 0) {
            IntervSanitaria intervOld = em.getReference(entityClass, interv.getNroSalida());
            List<IntervSanitariaProducto> intervSanitariaProductosOld = intervOld.getIntervSanitariaProductos();

            //borrar los viejos antes de guardar los cambios
            for (IntervSanitariaProducto isp : intervSanitariaProductosOld) {
                try {
                    em.getTransaction().begin();
                    em.remove(em.merge(isp));
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    java.util.logging.Logger.getLogger(IntervSanitariaService.class.getName() + " [guardarCambios: borrando los productos viejos en actuaizar la intervencion]").log(Level.SEVERE, null, e);
                }
            }
        } else {
            String nro = this.generarNroInterv();
            interv.setNroSalida(nro);
        }
        //Ahora proceso los que mando el cliente
        List<IntervSanitariaProducto> intervSanitariaProductos = interv.getIntervSanitariaProductos();
        List<IntervSanitariaProducto> intervSanitariaProductosNew = new ArrayList<IntervSanitariaProducto>();
        for (IntervSanitariaProducto intervSanitariaProducto : intervSanitariaProductos) {
            GpProducto producto = em.getReference(GpProducto.class, intervSanitariaProducto.getProducto().getIdProducto());
            intervSanitariaProducto.setProducto(producto);
            BigDecimal precio = productoHelper.obtenerPrecio(em, producto.getIdProducto(), "aq");
            int stock = obtenerStock(idModulo, producto);
            intervSanitariaProducto.setStock(stock);
            intervSanitariaProducto.setPrecio(precio);
            intervSanitariaProducto.setProducto(producto);
            intervSanitariaProducto.setInterSanitaria(interv);
            intervSanitariaProductosNew.add(intervSanitariaProducto);
        }
        chequearCliente(interv);
        interv.setIntervSanitariaProductos(intervSanitariaProductosNew);
        chequearJoins(interv);
        
        boolean ok = (interv.getNroSalida() != null && interv.getNroSalida().length() > 0) ? actualizar(interv) : insertar(interv);
        try {
            obtenerDatosCoordinador(interv);
        } catch (BusinessException ex) {
            Logger.getLogger(IntervSanitariaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

    public int obtenerStock(long idModulo, GpProducto producto) {
        List<GpAlmacen> almacenes = almacenService.listarPorModulo(idModulo);
        int stock = productoHelper.obtenerStock(producto.getIdProducto(), almacenes);
        return stock;
    }

    /**
     * Verifica las relaciones, buscando sus respectivos valores en el entity manager, ya que puede que solo vengan con los id's de la vista
     * @param interv 
     */
    private void chequearJoins(IntervSanitaria interv) {
        if (interv.getComponente() != null) {
            Componente componente = em.getReference(Componente.class, interv.getComponente().getId());
            interv.setComponente(componente);
        }
        if(interv.getSubComponente() != null) {
            SubComponente subComponente = em.getReference(SubComponente.class, interv.getSubComponente().getId());
            interv.setSubComponente(subComponente);
        }
        if(interv.getDiagnostico() != null) {
            DiagnosticoCIE diagnostico = em.getReference(DiagnosticoCIE.class, interv.getDiagnostico().getCodigo());
            interv.setDiagnostico(diagnostico);
        }
        if(interv.getProceso() != null) {
            Proceso proceso = em.getReference(Proceso.class, interv.getProceso().getId());
            interv.setProceso(proceso);
        }
        if(interv.getPrescriptor() != null) {
            Prescriptor prescriptor = em.getReference(Prescriptor.class, interv.getPrescriptor().getIdMedico());
            interv.setPrescriptor(prescriptor);
        }
//        if(interv.getCliente() != null) {
//            Cliente cliente = em.getReference(Cliente.class, interv.getCliente().getIdCliente());
//            interv.setCliente(cliente);
//        }
    }

    /**
     * Chequea que el cliente exista. Si es un paciente se registra en la base
     * de datos como cliente
     *
     * @param interv
     */
    private void chequearCliente(IntervSanitaria interv) {
        Cliente cliente = interv.getCliente();
        if (cliente != null && cliente.getIdCliente() == 0 && cliente.getCodPersonal() != null && cliente.getCodPersonal().length() > 0) {
            
            Paciente paciente = pacienteService.obtenerPorId(cliente.getCodPersonal());
            cliente.setDireccion(paciente.getDireccion());
            cliente.setIdModulo(interv.getIdModulo());
            String tlf1 = paciente.getTelefono1();
            String tlf2 = paciente.getTelefono2();
            String telefono = tlf1 != null && tlf1.length() > 0 ? tlf1 : tlf2;
            cliente.setTelefono(telefono);
                em.getTransaction().begin();
                em.persist(cliente);
                em.getTransaction().commit();
                em.refresh(cliente);
                interv.setCliente(cliente);
           
                String mssg = String.format("Ha ocurrido un error al registrar al paciente %s %s %s como cliente: paciente no encontrado",
                        cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno());
                Logger.getLogger(IntervSanitariaService.class.getName()).log(Level.SEVERE, mssg);
                interv.setCliente(null);
           
        }
    }

    public void sincronizarIntervProducto(IntervSanitariaProducto iSaniProducto) {
        GpProducto producto = em.find(GpProducto.class, iSaniProducto.getProducto().getIdProducto());
        if (producto != null) {
            BigDecimal precio = productoHelper.obtenerPrecio(em, producto.getIdProducto(), "ad");
            iSaniProducto.setPrecio(precio);
            iSaniProducto.setProducto(producto);
        }
    }

    private String generarNroInterv() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root<IntervSanitaria> from = cq.from(entityClass);
        cq.select(cb.count(from));

        TypedQuery query = em.createQuery(cq);
        long cantidad = ((Long) query.getSingleResult());
        cantidad++;

        DecimalFormat df = new DecimalFormat("0000000");
        return df.format(cantidad);
    }

    public IntSanKitProductoDTO obtenerProductosPorKit(long kId, List<GpProducto> userSelected, long idModulo) throws BusinessException {
        List<KitAtencionProducto> kitAtencionProductos = productoHelper.obtenerPorductosPorKitAtencion(em, kId, userSelected);
        IntSanKitProductoDTO intsandto = new IntSanKitProductoDTO();

        boolean hasStockIssues = false;
        String[] issueMessg = new String[0];
        String format = "";

        for (KitAtencionProducto kitAtencionProducto : kitAtencionProductos) {
            IntervSanitariaProducto intervProd = new IntervSanitariaProducto();
            intervProd.setProducto(kitAtencionProducto.getProducto());
            intervProd.setCantidad(kitAtencionProducto.getCantidad());
            this.sincronizarIntervProducto(intervProd);

            int productoStock = obtenerStock(idModulo, intervProd.getProducto());
            intervProd.setStock(productoStock);
            intsandto.getIntervSaniProductos().add(intervProd);
            if (productoStock <= 0) {
                hasStockIssues = true;
                issueMessg = Arrays.copyOf(issueMessg, issueMessg.length + 1);
                issueMessg[issueMessg.length - 1] = intervProd.getProducto().getDescripcion();
                format += "%s, ";
            }
        }
        if (hasStockIssues) {
            intsandto.setHasMessage(hasStockIssues);
            format = "El o los productos " + format.trim().substring(0, format.lastIndexOf(",")) + " no tienen Stock disponible.";
            intsandto.setMessage(String.format(format, (Object[]) issueMessg));
        }
        return intsandto;
    }

    public IntervSanitaria consultarPorNroSalida(String nroSalida, long idModulo) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<IntervSanitaria> cq = cb.createQuery(entityClass);
        Root<IntervSanitaria> from = cq.from(entityClass);
        cq.where(cb.equal(from.get("nroSalida"), nroSalida),
                cb.equal(from.get("IdModulo"), idModulo));

        TypedQuery<IntervSanitaria> query = em.createQuery(cq);
        try {
            IntervSanitaria interv = query.getSingleResult();
            if (interv.getCoordinador() != null) {
                obtenerDatosCoordinador(interv);
            }
            return interv;
        } catch (NoResultException e) {
            java.util.logging.Logger.getLogger(IntervSanitariaService.class.getName()).log(Level.INFO, "No se encontro ninguna intervencion sanitaria con nroSalida=" + nroSalida, e);
            return null;
        }
    }

    private void obtenerDatosCoordinador(IntervSanitaria interv) throws BusinessException {
        Personal personal = personalService.obtenerPorId(interv.getCoordinador());
        HashMap<String, String> datosCoor = new HashMap<String, String>();
        datosCoor.put("idPersonal", personal.getIdPersonal());
        datosCoor.put("nombre", personal.getNombre());
        datosCoor.put("apellidoPaterno", personal.getApellidoPaterno());
        datosCoor.put("apellidoMaterno", personal.getApellidoMaterno());
        interv.setDatosCoor(datosCoor);
        interv.setCoorPersonal(personal);
    }

    public IntervSanitaria anularIntervencion(String nroSalida, long idModulo) throws BusinessException {
        IntervSanitaria intervSanitaria = this.obtenerPorId(nroSalida);
        intervSanitaria.setAnulada(Boolean.TRUE);
        boolean ok = this.guardarCambios(intervSanitaria, idModulo);
        if (!ok) {
            throw new BusinessException(Arrays.asList("Ha ocurrido un error inesperado, la intervenci&oacute; no ha podido ser anulada."));
        } else {
            return intervSanitaria;
        }
    }

    @Override
    public IntervSanitaria obtenerPorId(Object nroSalida) {
        IntervSanitaria interv = super.obtenerPorId(nroSalida);
        if (interv != null && interv.getCoordinador() != null) {
            try {
                obtenerDatosCoordinador(interv);
            } catch (BusinessException ex) {
                Logger.getLogger(IntervSanitariaService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return interv;
    }
}
