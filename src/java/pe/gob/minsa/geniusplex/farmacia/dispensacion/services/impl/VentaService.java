/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.lazyload.IAlmacen;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;
import pe.gob.minsa.farmacia.services.impl.MovimientoProductoService;
import pe.gob.minsa.farmacia.services.impl.MovimientoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DemandaInsatisfecha;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.FormaPago;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.VentaKitProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.enums.VentaEstadoEnum;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.ListaModulo;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.ProductoHelper;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.helpers.VentaHelper;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpParametro;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProductoPrecio;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpConceptoService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 * Servicios para gestionar Ventas
 *
 */
@Service("ventaService")
public class VentaService extends GpServiceManager<Venta> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    VentaHelper vHelper;
    @Autowired
    ProductoHelper pHelper;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private GpAlmacenService almacenService;
    @Autowired
    private PrescriptorService prescriptorService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private GpProductoService productoService;
    @Autowired
    private DemandaInsatisfechaService dIService;
    @Autowired
    private MovimientoProductoService movProdService;
    @Autowired
    private MovimientoService movimientoService;
    @Autowired
    private ConceptoService conceptoService;

    public VentaService() {
        super(Venta.class);
    }

    @PostConstruct
    public void init() {
        em = entityManagerFactory.createEntityManager();
        setEntityManager(em);
    }

    public List<Venta> listarPorModulo(long idModulo) {
        ListaModulo<Venta> listaModulo = new ListaModulo<Venta>(Venta.class);
        listaModulo.setEntityManager(entityManagerFactory.createEntityManager());
        return listaModulo.listarPorModulo(idModulo);
    }

    public List<Venta> listarPorModuloClienteVentas(long idModulo, long idCliente) {
        TypedQuery<Venta> q = em.createQuery("SELECT v FROM Venta v WHERE v.IdModulo = :idModulo AND v.cliente.idCliente = :idCliente AND v.procesoVenta = TRUE", Venta.class);
        q.setParameter("idModulo", idModulo);
        List<Venta> lista = q.setParameter("idCliente", idCliente).getResultList();
        return lista;
    }

    public List<Venta> ReportePreventas(long idModulo, long turno, long vendedor, Date startDate, Date endDate) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_Preventas_Consultar", Venta.class);
        query.registerStoredProcedureParameter("Turno", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Vendedor", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start = null;
        String end = null;
        if (startDate != null) {
            start = format.format(startDate);

        }
        if (endDate != null) {
            end = format.format(endDate);
        }

        query.setParameter("Vendedor", vendedor);
        query.setParameter("Turno", turno);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("StartDate", start);
        query.setParameter("EndDate", end);

        List<Venta> resultList = query.getResultList();
        return resultList;
    }

    public List<Venta> ConsultaConsumoSalida(long idModulo, long tipoPago, Date startDate, Date endDate) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_Consumo_Salida_Consultar", Venta.class);
        query.registerStoredProcedureParameter("TipoPago", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start = null;
        String end = null;
        if (startDate != null) {
            start = format.format(startDate);

        }
        if (endDate != null) {
            end = format.format(endDate);
        }

        query.setParameter("TipoPago", tipoPago);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("StartDate", start);
        query.setParameter("EndDate", end);

        List<Venta> resultList = query.getResultList();
        return resultList;
    }

    public List<Venta> ConsultaConsumoPaciente(long idModulo, long tipoPago, Date startDate, Date endDate, Long cliente, long medico, long tipoProducto, String diagnostico) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_Consumo_Paciente_Consultar", Venta.class);
        query.registerStoredProcedureParameter("TipoPago", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Cliente", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("TipoProducto", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Medico", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Diagnostico", String.class, ParameterMode.IN);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start = null;
        String end = null;
        if (startDate != null) {
            start = format.format(startDate);

        }
        if (endDate != null) {
            end = format.format(endDate);
        }

        if (diagnostico != null && diagnostico.length() <= 0) {
            diagnostico = null;
        }

        query.setParameter("TipoPago", tipoPago);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("StartDate", start);
        query.setParameter("EndDate", end);
        query.setParameter("Cliente", cliente);
        query.setParameter("Medico", medico);
        query.setParameter("TipoProducto", tipoProducto);
        query.setParameter("Diagnostico", diagnostico);

        List<Venta> resultList = query.getResultList();
        return resultList;
    }

    public List<Venta> ConsultaVentas(long idModulo, long tipoPago, Date startDate, Date endDate, long cliente, long vendedor, long turno, String servicio) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("Far_Ventas_Consultar", Venta.class);
        query.registerStoredProcedureParameter("TipoPago", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdModulo", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("StartDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("EndDate", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Cliente", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Vendedor", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Turno", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Servicio", String.class, ParameterMode.IN);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start = null;
        String end = null;
        if (startDate != null) {
            start = format.format(startDate);

        }
        if (endDate != null) {
            end = format.format(endDate);
        }

        query.setParameter("TipoPago", tipoPago);
        query.setParameter("IdModulo", idModulo);
        query.setParameter("StartDate", start);
        query.setParameter("EndDate", end);
        query.setParameter("Cliente", cliente);
        query.setParameter("Vendedor", vendedor);
        query.setParameter("Turno", turno);
        query.setParameter("Seervicio", servicio);

        List<Venta> resultList = query.getResultList();
        HashMap<Integer, String> docs = new HashMap<Integer, String>();
        for (Venta venta : resultList) {
            int documento = venta.getDocumento();
            if (docs.containsKey(documento)) {
                venta.setDocTipo(docs.get(documento));
            } else {
                String nombreDocumento = vHelper.getNombreDocumento(documento, em);
                venta.setDocTipo(nombreDocumento);
                docs.put(documento, nombreDocumento);
            }
        }
        return resultList;
    }

    public boolean guardarCambios(Venta venta, boolean esPreventa) throws BusinessException {
        refreshVentaData(venta);
        if (venta.getId() == 0) {
            return processNew(esPreventa, venta);
        } else {
            return update(venta, esPreventa);
        }
    }

    private boolean update(Venta venta, boolean esPreventa) {
        Venta ventaActual = em.find(Venta.class, venta.getId());//stored
        List<VentaProducto> ventaProductosOld = ventaActual.getVentaProductos();
        List<VentaProducto> ventaProductosNew = venta.getVentaProductos();//client
        ArrayList<VentaProducto> ventaProductos = new ArrayList<VentaProducto>();
        //estos son los productos venta que llegaron desde el cliente
        for (VentaProducto ventaProducto : ventaProductosNew) {
            this.sincronizarVentaProducto(ventaProducto, venta.getFormaDePago());//este metodo busca el precio tambien
            ventaProducto.setVenta(venta);
            ventaProductos.add(ventaProducto);
        }
        venta.setVentaProductos(ventaProductos);

        //borrar los productos ventas actuales
        for (VentaProducto vPropOld : ventaProductosOld) {
            try {
                em.getTransaction().begin();
                em.remove(em.merge(vPropOld));
                em.getTransaction().commit();
            } catch (Exception e) {
                java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                em.getTransaction().rollback();
                return false;
            }
        }

        //Obtener porciento de impuesto y calcular impuesto
        double porciento = this.obtenerPorcientoDeImpuesto();
        BigDecimal impuesto = venta.getSubTotalPreventa()
                .multiply(new BigDecimal(String.valueOf(porciento)));

        venta.setImpuestoPreventa(impuesto);

        //actualizar venta con los productos que llegaron desde el cliente
        if (!esPreventa) {
            return true;
        } else {
            return actualizar(venta);
        }
    }

    private boolean processNew(boolean esPreventa, Venta venta) {
        if (esPreventa) {
            venta.setPreventa(this.getNroPreventa(venta.getIdModulo()));
        }
        if (venta.getVentaProductos() == null) {
            venta.setVentaProductos(new ArrayList<VentaProducto>());
        }
        ArrayList<VentaProducto> ventaProductosNew = new ArrayList<VentaProducto>();
        for (VentaProducto ventaProducto : venta.getVentaProductos()) {
            this.sincronizarVentaProducto(ventaProducto, venta.getFormaDePago());
            ventaProducto.setVenta(venta);
            ventaProductosNew.add(ventaProducto);
        }
        venta.setVentaProductos(ventaProductosNew);

        //Obtener porciento de impuesto y calcular impuesto
        double porciento = this.obtenerPorcientoDeImpuesto();
        BigDecimal impuesto = venta.getSubTotalPreventa()
                .multiply(new BigDecimal(String.valueOf(porciento)));

        venta.setImpuestoPreventa(impuesto);
        boolean result = insertar(venta);
        this.registrarNotaDeSalida(venta);
        return result;
    }

    private void registrarNotaDeSalida(Venta venta) {

        List<MovimientoProducto> mps = new ArrayList<MovimientoProducto>();

        Movimiento mov = new Movimiento();
        for (VentaProducto vp : venta.getVentaProductos()) {
            MovimientoProducto mp = new MovimientoProducto();
            mp.setActivo(1);
            mp.setCantidad(vp.getCantidad().intValue());
            mp.setIdProducto(vp.getProducto().getIdProducto());
            mp.setNombreProducto(vp.getProducto().getSingleDescripcion());

            List<Concepto> conceptos = conceptoService.listarActivos();
            for (Concepto c : conceptos) {
                //Esto puede cambiar
                if (c.getNombreConcepto().equals("VENTA PUBLICO")) {
                    mov.setConcepto(c);
                    mov.setIdConcepto(c.getIdConcepto());
                    break;
                }
            }

            List<GpAlmacen> almacens
                    = almacenService.listarPorModulo(venta.getIdModulo());
            for (GpAlmacen almacen : almacens) {
                int stock = pHelper
                        .obtenerStock(vp.getProducto().getIdProducto(),
                                almacen.getIdAlmacen());
                if (stock > 0) {

                    IAlmacen ia = new Almacen();
                    ia.setIdAlmacen(almacen.getIdAlmacen());
                    ia.setDescripcion(almacen.getDescripcion());
                    if (almacen.getIdUbigeo() != null) {
                        ia.setIdUbigeo(almacen.getIdUbigeo().getIdUbigeo());
                    }
                    ia.setDescripcion(almacen.getDireccion());

                    mov.setAlmacenOrigen(ia);
                    mov.setIdAlmacenOrigen(ia.getIdAlmacen());

                    Date now = GregorianCalendar.getInstance().getTime();
                    Timestamp timestamp = new java.sql.Timestamp(now.getTime());
                    mov.setFechaRegistro(timestamp);

                    String lote = pHelper
                            .obtenerLoteConStock(vp.getProducto().getIdProducto(),
                                    ia.getIdAlmacen());
                    mp.setLote(lote);
                    mp.setPrecio(vp.getPrecio());
                    Date fv = productoService.obtenerFechaDeVencimiento(vp.getProducto().getIdProducto(), 
                            almacen.getIdAlmacen(), lote);
                    if(fv != null) {
                        Timestamp fvts = new java.sql.Timestamp(fv.getTime());
                        mp.setFechaVencimiento(fvts);
                    } else {
                        mp.setFechaVencimiento(timestamp);//no se encontro ninguna pero voy a guardar de todos modos
                    }
                    
                    mp.setTotal(mp.getPrecio()
                            .multiply(new BigDecimal(vp.getCantidad().toString())));
                    mp.setActivo(1);
                    mp.setUsuarioCreacion(venta.getUsuarioCreacion());
                    mps.add(mp);

                    break;
                }
            }
        }
        try {
            movimientoService.insertarSalidaConDetalle(mov, mps);
        } catch (BusinessException ex) {
            Logger.getLogger(VentaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshVentaData(Venta venta) throws BusinessException {
        venta.setFormaDePago(em.getReference(FormaPago.class, venta.getFormaDePago().getId()));
        if (venta.getCliente() != null) {
            chequearCliente(venta);
            if (venta.getCliente() == null) {
                throw new BusinessException(Arrays.asList("Si la modalidad de pago es crédito, debe seleccionar un cliente de la lista de Cliente."));
            }
        }
        if (venta.getProceso() != null) {
            venta.setProceso(em.getReference(Proceso.class, venta.getProceso().getId()));
        }
        if (venta.getTurno() != null) {
            venta.setTurno(em.getReference(Turno.class, venta.getTurno().getIdTurno()));
        }
        if (venta.getDiagnostico() != null) {
            venta.setDiagnostico(em.getReference(DiagnosticoCIE.class, venta.getDiagnostico().getCodigo()));
        }
        if (venta.getPrescriptor() != null) {
            if (venta.getPrescriptor().getPersonal() != null) {
                Prescriptor prescriptor = prescriptorService.validarPersonal(venta.getPrescriptor(), venta.getIdModulo());
                venta.setPrescriptor(prescriptor);
            } else {
                venta.setPrescriptor(em.getReference(Prescriptor.class, venta.getPrescriptor().getIdMedico()));
            }
        }
        if (venta.getVendedor() != null) {
            if (venta.getVendedor().getPersonal() != null) {
                Vendedor vendedor = vendedorService.validarVendedor(venta.getVendedor(), venta.getIdModulo());
                venta.setVendedor(vendedor);
            } else {
                venta.setVendedor(em.getReference(Vendedor.class, venta.getVendedor().getIdVendedor()));
            }
        }
    }

    private void chequearCliente(Venta venta) throws BusinessException {
        Cliente cliente = venta.getCliente();
        if (cliente.getIdCliente() == 0) {
            if (cliente.getCodPersonal() != null) {
                Cliente tmpCliente = clienteService.obtenerPorCodPaciente(cliente.getCodPersonal(), venta.getIdModulo());
                if (tmpCliente == null) {
                    Paciente paciente = em.find(Paciente.class, cliente.getCodPersonal());
                    if (paciente != null) {
                        cliente.setIdModulo(venta.getIdModulo());
                        crearClienteDesdePaciente(cliente, paciente);
                        em.refresh(cliente);
                    } else {
                        throw new BusinessException(Arrays.asList("Ha ocurrido un error. No se ha encontrado el paciente seleccionado."));
                    }
                } else {
                    cliente = tmpCliente;
                }
            } else {//puede que la forma de pago sea credito, asi que se verifica aqui
                FormaPago formaPago = em.getReference(FormaPago.class, venta.getFormaDePago().getId());
                String fp = null;
                if (formaPago != null) {
                    fp = formaPago.getDescripcion().toLowerCase().split("\\s")[0];
                }
                String nombreCliente = cliente.getNombre();
                if (nombreCliente != null && nombreCliente.length() > 0
                        && fp != null
                        && !fp.startsWith("cr") && !fp.endsWith("dito")) {//la e con tilde de credito es tricky
                    String[] nombSplitted = nombreCliente.split("\\s");
                    if (nombSplitted.length >= 3) {
                        String aMat = nombSplitted[nombSplitted.length - 1];
                        String aPat = nombSplitted[nombSplitted.length - 2];
                        String nomb = "";
                        for (int i = 0; i < nombSplitted.length - 1; i++) {
                            nomb += nombSplitted[i] + " ";
                        }
                        nomb = nomb.trim();
                        cliente.setNombre(nomb);
                        cliente.setApellidoMaterno(aMat);
                        cliente.setApellidoPaterno(aPat);
                        cliente.setIdModulo(venta.getIdModulo());
                    } else {
                        throw new BusinessException(Arrays.asList("El nombre del cliente no es v&aacute;lido. Verifique que tenga al menos un nombre y dos apellidos"));
                    }
                    clienteService.insertar(cliente);
                } else {//la forma de pago es credito y no selecciono un cliente del listado
                    cliente = null;
                }
            }
            venta.setCliente(cliente);
        } else {
            venta.setCliente(em.getReference(Cliente.class, venta.getCliente().getIdCliente()));
        }
    }

    private void crearClienteDesdePaciente(Cliente cliente, Paciente paciente) {
        cliente.setApellidoMaterno(paciente.getMaterno());
        cliente.setApellidoPaterno(paciente.getPaterno());
        cliente.setNombre(paciente.getNombre());
        cliente.setDireccion(paciente.getDireccion());
        String tlf1 = paciente.getTelefono1();
        String tlf2 = paciente.getTelefono2();
        String telefono = tlf1 != null && tlf1.length() > 0 ? tlf1 : tlf2;
        cliente.setTelefono(telefono);
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    public boolean guardarCambiosVenta(Venta venta) throws BusinessException {
        boolean result = this.guardarCambios(venta, false);

        if (venta.getNroOperacion() == null && !venta.isProcesoVenta()) {
            String nro = this.getNroOperacion(venta.getIdModulo());
            venta.setNroOperacion(nro);
        }
        if (venta.getNroVenta() == null && !venta.isProcesoVenta()) {
            String nroVenta = this.getNroVenta(venta.getDocumento(), venta.getIdModulo());
            venta.setNroVenta(nroVenta);
        }
        if (venta.getFormaDePago().getDescripcion().equalsIgnoreCase("contado")) {
            venta.setEstado(VentaEstadoEnum.PAGADA_EN_CAJA);
        } else {
            venta.setEstado(VentaEstadoEnum.X_COBRAR);
        }

        //TODO Generar Boleta de Venta (Factura de Pago) ????
        return result && actualizar(venta);
    }

    public String getNroPreventa(long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Venta> rt = cq.from(entityClass);
        cq.select(cb.count(rt))
                .where(cb.isNotNull(rt.get("preventa")), cb.equal(rt.get("IdModulo"), idModulo));
        Query q = em.createQuery(cq);
        int cantidad = ((Long) q.getSingleResult()).intValue();
        cantidad++;
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String yyFormat = sdf.format(GregorianCalendar.getInstance().getTime());
        DecimalFormat df = new DecimalFormat("0000000");
        return yyFormat + df.format(cantidad);
    }

    public String getNroOperacion(long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Venta> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt))
                .where(cb.and(
                                cb.isNotNull(rt.get("procesoVenta")),
                                cb.isTrue(rt.get("procesoVenta").as(Boolean.class)),
                                cb.equal(rt.get("IdModulo"), idModulo)
                        ));
        Query q = em.createQuery(cq);
        int cantidad = ((Long) q.getSingleResult()).intValue();
        cantidad++;
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String yyFormat = sdf.format(GregorianCalendar.getInstance().getTime());
        DecimalFormat df = new DecimalFormat("0000000");
        return yyFormat + df.format(cantidad);
    }

    public String getNroVenta(int idDoc, long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root<Venta> rt = cq.from(entityClass);
        cq.where(cb.equal(rt.get("documento"), idDoc),
                cb.isTrue(rt.get("procesoVenta").as(Boolean.class)),
                cb.equal(rt.get("IdModulo"), idModulo));
        cq.select(cb.count(rt));
        TypedQuery query = em.createQuery(cq);
        long cantidad = ((Long) query.getSingleResult());
        cantidad++;

        DecimalFormat df7 = new DecimalFormat("0000000");
        DecimalFormat df3 = new DecimalFormat("000");

        long c1 = cantidad % 9999999;
        long c2 = (long) cantidad / 9999999;

        if (cantidad <= 9999999) {
            c2 = 1;
            c1 = cantidad;
        } else if (c1 == 0) {
            c1 = 1;
            c2++;
        } else {
            c2++;
        }
        //Uff, 3:04 in the morning, let's call it a day. See u in a few hours, Mr. Source.
        return String.format("%s-%s", df3.format(c2), df7.format(c1));
    }

    public boolean borrarProductos(Venta venta) {
        venta = em.find(Venta.class, venta.getId());
        //borrar los productos ventas actuales
        for (VentaProducto vProp : venta.getVentaProductos()) {
            try {
                em.getTransaction().begin();
                em.remove(em.merge(vProp));
                em.getTransaction().commit();
            } catch (Exception e) {
                java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                em.getTransaction().rollback();
                return false;
            }
        }
        //actualizar venta con los productos que llegaron desde el cliente
        return actualizar(venta);
    }

    public double obtenerPorcientoDeImpuesto() {
        TypedQuery<GpParametro> query = em.createNamedQuery("GpParametro.findByNombre", GpParametro.class);
        query.setParameter("nombre", "IGV");//El valor de este parametro puede cambiar
        List<GpParametro> res = query.getResultList();
        if (res.isEmpty()) {
            return 0;
        }
        GpParametro parametro = res.get(0);
        String valor = parametro.getValor();
        if (valor.contains("%")) {
            valor = valor.replace('%', ' ');
            valor = valor.trim();
        }
        return Double.parseDouble(valor);
    }

    /**
     * Busca el producto y su precio, basado en la forma de pago
     *
     * @param vProducto
     * @param formaPago
     */
    public void sincronizarVentaProducto(VentaProducto vProducto, FormaPago formaPago) {
        GpProducto producto = em.getReference(GpProducto.class, vProducto.getProducto().getIdProducto());
        BigDecimal precio = this.obtenerPrecioDeProducto(producto.getIdProducto());
        if (formaPago != null) {
            if (formaPago.getDescripcion().equalsIgnoreCase("soat")) {
                TypedQuery<GpParametro> query = em.createQuery("SELECT p FROM GpParametro p WHERE p.nombreParametro = :nombre", GpParametro.class);
                GpParametro parametro = null;
                try {
                    parametro = query.setParameter("nombre", "OPERACION").getSingleResult();
                } catch (NoResultException e) {
                    java.util.logging.Logger.getLogger(VentaService.class.getName()).log(Level.INFO, "No se encontro el parametro OPERACION", e);
                } catch (NonUniqueResultException ex) {
                    java.util.logging.Logger.getLogger(VentaService.class.getName()).log(Level.WARNING, "Hay mas de un parametro con el nombre OPERACION, se esta tomando el primero", ex);
                    parametro = query.setParameter("nombre", "OPERACION").getResultList().get(0);
                }
                //Este parametro puede cambiar
                if (parametro != null) {
                    BigDecimal porcientoSoat = new BigDecimal(parametro.getValor());
                    BigDecimal precioAdq = this.obtenerPrecioDeProducto(producto.getIdProducto(), "ad");
                    precio = precioAdq.add(precioAdq.multiply(porcientoSoat));
                }
            }
        }
        vProducto.setPrecio(precio);
        vProducto.setProducto(producto);
    }

    public BigDecimal obtenerPrecioDeProducto(Integer idProducto) {
        return pHelper.obtenerPrecio(em, idProducto, "op");
    }

    public BigDecimal obtenerPrecioDeProducto(Integer idProducto, String tipoPrecio) {
        return pHelper.obtenerPrecio(em, idProducto, tipoPrecio);
    }

    public Venta consultarPreventa(String numeroPreventa, long idModulo, boolean allowSails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(entityClass);
        Root<Venta> from = cq.from(entityClass);
        Predicate[] conditions = new Predicate[]{
            cb.equal(from.get("preventa"), numeroPreventa),
            cb.equal(from.get("IdModulo"), idModulo)
        };
        if (!allowSails) {
            conditions = Arrays.copyOf(conditions, conditions.length + 1);
            conditions[conditions.length - 1] = cb.or(
                    cb.isFalse(from.get("procesoVenta").as(Boolean.class)),
                    cb.isNull(from.get("procesoVenta"))
            );
        }
        cq.where(conditions);
        TypedQuery<Venta> query = em.createQuery(cq);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(VentaService.class.getName()).log(Level.INFO, null, e);
            java.util.logging.Logger.getLogger(VentaService.class.getName()).log(Level.SEVERE, null, "\n\nHay mas de una preventa con el mismo numero");
            return null;
        }
    }

    public Venta consultarVenta(String nroOperacion, long idModulo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(entityClass);
        final Root<Venta> VENT = cq.from(entityClass);
        cq.where(cb.equal(VENT.get("nroOperacion"), nroOperacion), cb.equal(VENT.get("IdModulo"), idModulo));
        TypedQuery<Venta> query = em.createQuery(cq);

        try {
            Venta venta = query.getSingleResult();
            venta.setDocTipo(vHelper.getNombreDocumento(venta.getDocumento(), em));
            return venta;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(VentaService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    public List<Venta> consultarVenta(Date fechIni, Date fechFin, String periodo,
            String nroVenta, String ordenarPor, long idModulo) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(entityClass);
        Root<Venta> v = cq.from(entityClass);
        Predicate[] condiciones = {
            cb.equal(v.get("IdModulo"), idModulo),//filtrar por el id de modulo
            cb.isTrue(v.get("procesoVenta").as(Boolean.class)),
            cb.or(
            cb.isFalse(v.get("anulada").as(Boolean.class)),
            cb.isNull(v.get("anulada"))
            )
        };
        //Fecha de Registro
        if (fechIni != null && fechFin != null) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.between(v.<Date>get("ventafechaRegistro"), fechIni, fechFin);
        }
        if (fechIni != null && fechFin == null) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.greaterThanOrEqualTo(v.<Date>get("ventafechaRegistro"), fechIni);
        }
        if (fechIni == null && fechFin != null) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.lessThanOrEqualTo(v.<Date>get("ventafechaRegistro"), fechFin);
        }
        //Periodo
        if (periodo != null && periodo.length() > 2) {
            //el periodo es un anho, asi que me quedo con los dos ultimos digitos
            //es con lo que comienza el numero de una operacion
            periodo = periodo.substring(2);
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.equal(cb.substring(v.<String>get("nroOperacion"), 1, 2), periodo);
        }
        //Nro. de Venta
        if (nroVenta != null && nroVenta.length() > 0) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.equal(v.get("nroVenta"), nroVenta);
        }
        cq.where(condiciones);
        //Ordenar
        String[] ordenarSplit = ordenarPor.split("-");
        if (ordenarSplit.length > 0) {
            List<Order> orders = new ArrayList<Order>();
            for (String ord : ordenarSplit) {
                if (ord.equals("nroVenta")) {
                    orders.add(cb.asc(v.get("nroVenta")));
                } else if (ord.equals("cliente")) {
                    orders.add(cb.asc(v.get("cliente").get("Nombre")));
                } else if (ord.equals("vendedor")) {
                    orders.add(cb.asc(v.get("vendedor").get("paterno")));
                }
            }
            if (!orders.isEmpty()) {
                cq.orderBy(orders);
            }
        }

        TypedQuery<Venta> query = em.createQuery(cq);
        if (condiciones.length == 3) {
            //no se especifico ningun parametro asi que vamos a paginar
            query.setMaxResults(100);
        }
        List<Venta> ventas = query.getResultList();
        HashMap<Integer, String> docs = new HashMap<Integer, String>();
        for (Venta venta : ventas) {
            int documento = venta.getDocumento();
            if (docs.containsKey(documento)) {
                venta.setDocTipo(docs.get(documento));
            } else {
                String nombreDocumento = vHelper.getNombreDocumento(documento, em);
                venta.setDocTipo(nombreDocumento);
                docs.put(documento, nombreDocumento);
            }
        }
        return ventas;
    }

    public List<Venta> consultarCuentasCorriente(long idModulo, FilterData fData) throws BusinessException {
        HashMap<String, Object> params = fData.getParams();
        Date fechIni = null;
        Date fechFin = null;
        VentaEstadoEnum estado = Enum.valueOf(VentaEstadoEnum.class, (String) params.get("estado"));
        long cliente;
        long formaPago;
        String periodo = (String) params.get("periodo");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(entityClass);
        Root<Venta> v = cq.from(entityClass);
        Predicate[] condiciones = {
            cb.equal(v.get("IdModulo"), idModulo),//filtrar por el id de modulo
            cb.isTrue(v.get("procesoVenta").as(Boolean.class))//no se listan las preventas
        };

        Predicate estadoPredicate;
        condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
        if (estado.equals(VentaEstadoEnum.CANCELADA)) {
            estadoPredicate = cb.equal(v.get("estado"), VentaEstadoEnum.CANCELADA);
        } else {
            estadoPredicate = cb.or(
                    cb.equal(v.get("estado"), estado),
                    cb.isNull(v.get("estado"))
            );
        }
        condiciones[condiciones.length - 1] = estadoPredicate;
        condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);//el estado no puede ser al contado
        condiciones[condiciones.length - 1] = cb.equal(v.get("estado"), VentaEstadoEnum.PAGADA_EN_CAJA).not();

        if (params.containsKey("cliente") && (cliente = Long.parseLong(params.get("cliente").toString())) > 0) {
            Predicate clientePredicate = cb.equal(v.get("cliente").get("idCliente"), cliente);
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = clientePredicate;
        }
        if (params.containsKey("formaDePago") && params.get("formaDePago").toString().length() > 0) {
            formaPago = Long.parseLong(params.get("formaDePago").toString());
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.equal(v.get("formaDePago").get("id"), formaPago);
        }

        try {
            if (params.containsKey("fechIni") && params.get("fechIni").toString().length() > 0) {
                fechIni = simpleDateFormat.parse((String) params.get("fechIni"));
            }
            if (params.containsKey("fechFin") && params.get("fechFin").toString().length() > 0) {
                fechFin = simpleDateFormat.parse((String) params.get("fechFin"));
            }
        } catch (ParseException pEx) {
            java.util.logging.Logger.getLogger(VentaService.class.getName()).log(Level.SEVERE, null, pEx);
            BusinessException bEx = new BusinessException(Arrays.asList("El formato de las fechas debe ser dia/mes/año"));
            throw bEx;
        }
        //Fecha de Registro
        if (fechIni != null && fechFin != null) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.between(v.<Date>get("ventafechaRegistro"), fechIni, fechFin);
        }
        if (fechIni != null && fechFin == null) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.greaterThanOrEqualTo(v.<Date>get("ventafechaRegistro"), fechIni);
        }
        if (fechIni == null && fechFin != null) {
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.lessThanOrEqualTo(v.<Date>get("ventafechaRegistro"), fechFin);
        }
        //Periodo
        if (periodo != null && periodo.length() > 2) {
            //el periodo es un anho, asi que me quedo con los dos ultimos digitos
            //es con lo que comienza el numero de una operacion
            periodo = periodo.substring(2);
            condiciones = Arrays.copyOf(condiciones, condiciones.length + 1);
            condiciones[condiciones.length - 1] = cb.equal(cb.substring(v.<String>get("nroOperacion"), 1, 2), periodo);
        }

        if (condiciones.length > 0) {
            cq.where(condiciones);
        }

        TypedQuery<Venta> query = em.createQuery(cq);
        List<Venta> ventas = query.getResultList();
        for (Venta venta : ventas) {
            venta.setDocTipo(vHelper.getNombreDocumento(venta.getDocumento(), em));
        }
        return ventas;
    }

    /**
     *
     * @param kitId
     * @param userSelected
     * @return
     * @throws pe.gob.minsa.farmacia.util.BusinessException
     * @retur
     */
    public VentaKitProductoDTO obtenerProductosPorKit(long kitId, List<GpProducto> userSelected) throws BusinessException {
        List<KitAtencionProducto> kitAtencionProductos = pHelper.obtenerPorductosPorKitAtencion(em, kitId, userSelected);
        ArrayList<VentaProducto> ventaProductos = new ArrayList<VentaProducto>();
        boolean hasStockIssues = false;
        String[] issueMessg = new String[0];
        String format = "";
        for (KitAtencionProducto kitAtencionProducto : kitAtencionProductos) {
            VentaProducto vProd = new VentaProducto();
            vProd.setProducto(kitAtencionProducto.getProducto());
            vProd.setCantidad(kitAtencionProducto.getCantidad());
            BigDecimal precio = this.obtenerPrecioDeProducto(vProd.getProducto().getIdProducto());
            vProd.setPrecio(precio);
            ventaProductos.add(vProd);
            int productoStock = pHelper.getProductoStock(em, vProd.getProducto().getIdProducto());
            if (productoStock <= 0) {
                hasStockIssues = true;
                issueMessg = Arrays.copyOf(issueMessg, issueMessg.length + 1);
                issueMessg[issueMessg.length - 1] = vProd.getProducto().getDescripcion();
                format += "%s, ";
            }
        }
        VentaKitProductoDTO vkpdto = new VentaKitProductoDTO(ventaProductos);
        if (hasStockIssues) {
            vkpdto.setHasMessage(hasStockIssues);
            format = "El o los productos " + format.trim().substring(0, format.lastIndexOf(",")) + " no tienen Stock disponible.";
            vkpdto.setMessage(String.format(format, (Object[]) issueMessg));
        }
        return vkpdto;
    }

    private List<GpProducto> refrescarProductos(List<GpProducto> productos) {
        ArrayList<GpProducto> gpProductos = new ArrayList<GpProducto>();
        for (GpProducto producto : productos) {
            producto = em.getReference(GpProducto.class, producto.getIdProducto());
            gpProductos.add(producto);
        }
        return gpProductos;
    }

    /**
     * Listar Documentos de Venta (Boleta de Venta y Factura)
     *
     * @return map<IdDocumento, Nombre>
     */
    public HashMap<Integer, String> listarDocumentos() {
        return vHelper.listarDocumentos(em);
    }

    public boolean anularVenta(Venta venta) {
        String motivosAnulacion = venta.getMotivosAnulacion();
        Long idUsuarioAnulacion = venta.getIdUsuarioAnulacion();

        venta = em.getReference(entityClass, venta.getId());

        venta.setMotivosAnulacion(motivosAnulacion);
        venta.setIdUsuarioAnulacion(idUsuarioAnulacion);
        venta.setAnulada(Boolean.TRUE);

        return actualizar(venta);
    }

    public boolean cancelarVenta(Venta venta) {
        venta.setFechaCancelacion(GregorianCalendar.getInstance().getTime());
        venta.setAnulada(Boolean.TRUE);
        venta.setEstado(VentaEstadoEnum.CANCELADA);
        boolean ok = this.actualizar(venta);
        return ok;
    }

    public int obtenerStock(int idProducto, long idModulo) {
        List<GpAlmacen> almacenes = almacenService.listarPorModulo(idModulo);
        return pHelper.obtenerStock(idProducto, almacenes);
    }

    public List<Venta> obtenerHistorial(Cliente cliente, int dias) {
        List<Venta> ventas = Collections.emptyList();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, dias * -1);
        Date time = calendar.getTime();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(entityClass);
        Root<Venta> fromVenta = cq.from(entityClass);
        cq.where(
                cb.equal(fromVenta.get("cliente"), cliente),
                cb.greaterThanOrEqualTo(fromVenta.get("ventafechaRegistro")
                        .as(Date.class), time),
                cb.isTrue(fromVenta.get("procesoVenta").as(Boolean.class))
        ).orderBy(cb.desc(fromVenta.get("ventafechaRegistro")));

        TypedQuery<Venta> query = em.createQuery(cq);
        ventas = query.getResultList();
        for (Venta venta : ventas) {
            venta.setDocTipo(vHelper.getNombreDocumento(venta.getDocumento(), em));
        }
        return ventas;
    }

    public List<Venta> obtenerHistorial(long idCliente, int dias) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        return obtenerHistorial(cliente, dias);
    }

    /**
     * Calcula los datos de pago, pero no guarda la venta, simplemente retorna
     * la misma venta, pero con los precios de los productos y los datos de pago
     * de la venta
     *
     * @param venta
     * @return
     */
    public Venta obtenerDatosPrepago(Venta venta) {
        venta.setFormaDePago(em.getReference(FormaPago.class, venta.getFormaDePago().getId()));
        for (VentaProducto ventaProducto : venta.getVentaProductos()) {
            this.sincronizarVentaProducto(ventaProducto, venta.getFormaDePago());//este metodo busca el precio tambien
        }
        //Obtener porcinto de impuesto y calcular impuesto
        double porciento = this.obtenerPorcientoDeImpuesto();
        BigDecimal impuesto = venta.getSubTotalPreventa()
                .multiply(new BigDecimal(String.valueOf(porciento)));

        venta.setImpuestoPreventa(impuesto);
        return venta;
    }

    public Date getLastPurchaseDate(long idModulo, long idCliente, int idProducto) {
        Cliente cliente = clienteService.obtenerPorId(idCliente);
        GpProducto producto = productoService.obtenerPorId(idProducto);

        if (cliente != null && producto != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Date> cq = cb.createQuery(Date.class);
            Root<Venta> ventaRoot = cq.from(entityClass);
            cq.select(cb.greatest(ventaRoot.<Date>get("ventafechaRegistro")));
            Join<Object, Object> prodsJoin = ventaRoot.join("ventaProductos");
            cq.where(
                    cb.equal(ventaRoot.get("IdModulo"), idModulo),
                    cb.equal(ventaRoot.get("cliente"), cliente),
                    cb.equal(prodsJoin.get("producto"), producto),
                    cb.isNotNull(ventaRoot.get("nroVenta"))
            );
            TypedQuery<Date> query = em.createQuery(cq);
            Date date = query.getSingleResult();
            return date;
        } else {
            return null;
        }
    }

    public Boolean addDemandaInsatisfecha(long idModulo, long idCliente, int idProducto) {
        DemandaInsatisfecha demandaInsatisfecha = new DemandaInsatisfecha();
        demandaInsatisfecha.setFecha(GregorianCalendar.getInstance().getTime());
        demandaInsatisfecha.setIdModulo(idModulo);

        Cliente cliente = clienteService.obtenerPorId(idCliente);
        demandaInsatisfecha.setCliente(cliente);

        demandaInsatisfecha.setIdProducto(idProducto);

        //buscamos todos los almacenes del modulo para asociar la demanda insatisfecha a alguno de ellos
        List<GpAlmacen> almacenes = almacenService.listarPorModulo(idModulo);
        long idAlmancen = 0;

        if (almacenes.isEmpty()) {
            return false;
        }

        for (int i = 0; i < almacenes.size() && idAlmancen == 0; i++) {
            GpAlmacen almacene = almacenes.get(i);
            List<String> lotes = movProdService.obtenerLotes(idProducto, almacene.getIdAlmacen());
            if (!lotes.isEmpty()) {
                idAlmancen = almacene.getIdAlmacen();
                demandaInsatisfecha.setLote(lotes.get(lotes.size() - 1));
            }
        }
        if (idAlmancen == 0) {//buscamos un almacen random dentro del listado de almacenes del modulo porque el producto no esta presente en ningun almacen
            int alIndex = new Random(1).nextInt(almacenes.size());
            idAlmancen = almacenes.get(alIndex).getIdAlmacen();
        }

        demandaInsatisfecha.setIdAlmacen(idAlmancen);

        return dIService.insertar(demandaInsatisfecha);
    }
}
