package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.Servicio;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.enums.VentaEstadoEnum;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Venta")
public class Venta extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private long id;

    //9 digitos: los 2 primeros el anho, y el resto es un correlativo (consecutivos)
    @Column(name = "NumeroPreventa", length = 9)
    private String preventa;

    @Column(name = "NroOperacion")
    private String nroOperacion;

    //Numero de la venta: tres digitos correlativo, guion (-) y 7 digitos mas (correlativo tambien)
    @Column(name = "NroVenta")
    private String nroVenta;

    @Column(name = "Modalidad")
    private int modalidad;

    @JsonDeserialize(as = Date.class)
    @Column(name = "FechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @JsonDeserialize(as = Date.class)
    @Column(name = "VentaFechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date ventafechaRegistro;

    @JsonDeserialize(as = BigDecimal.class)
    @Column(name = "SubTotalPreventa", precision = 38, scale = 2)
    private BigDecimal subTotalPreventa;

    @JsonDeserialize(as = BigDecimal.class)
    @Column(name = "ImpuestoPreventa", precision = 38, scale = 2)
    private BigDecimal impuestoPreventa;

    @JsonDeserialize(as = BigDecimal.class)
    @Column(name = "RedondeoPreventa", precision = 38, scale = 2)
    private BigDecimal redondeoPreventa;
    
    @Column(name = "Exonerados")
    private Boolean exonerados;

    @Lob
    @Column(name = "Especificaciones")
    private String especificaciones;

    @Lob
    @Column(name = "Substitutos")
    private String substitutos;

    @Column(name = "TipoDeReceta")
    private int tipoDeReceta;

    @Column(name = "NumeroDeReceta")
    private String numeroDeReceta;

    @Column(name = "FlgProcesoVenta")
    private Boolean procesoVenta;

    @Column(name = "Documento")
    private int documento;

    @Lob
    @Column(name = "MotivosAnulacion")
    private String motivosAnulacion;

    @Column(name = "UsuarioAnulacion")
    private Long idUsuarioAnulacion;

    @Column(name = "FlgVentaAnulada")
    private Boolean anulada;

    @JsonDeserialize(as = DiagnosticoCIE.class)
    @JoinColumn(name = "CodigoDiagnosticoCIE")
    @ManyToOne
    private DiagnosticoCIE diagnostico;

    @JoinColumn(name = "Vendedor")
    @ManyToOne
    private Vendedor vendedor;

    @JsonDeserialize(as = Prescriptor.class)
    @JoinColumn(name = "Medico")
    @ManyToOne
    private Prescriptor prescriptor;

    @JsonDeserialize(as = Cliente.class)
    @JoinColumn(name = "Cliente")
    @ManyToOne
    private Cliente cliente;

    @JsonDeserialize(as = Turno.class)
    @JoinColumn(name = "Turno")
    @ManyToOne
    private Turno turno;

    @JsonDeserialize(as = Proceso.class)
    @JoinColumn(name = "Proceso")
    @ManyToOne
    private Proceso proceso;

    @JsonDeserialize(as = FormaPago.class)
    @JoinColumn(name = "FomaDePago")
    @ManyToOne
    private FormaPago formaDePago;

    @JsonDeserialize(as = List.class, contentAs = VentaProducto.class)
    @JoinColumn(name = "VentaProducto")
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<VentaProducto> ventaProductos;

    @JoinColumn(name = "PuntoDeVenta")
    @ManyToOne
    private PuntoDeVenta puntoDeVenta;

    @Column(name = "IdModulo")
    private long IdModulo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado")
    private VentaEstadoEnum estado;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FechaCancelacion")
    private Date fechaCancelacion;
    
    
    @ManyToOne
    @JoinColumn(name = "Servicio")
    private Servicio servicio;
    
    @Transient
    private String docTipo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Venta[ id=" + id + " ]";
    }

    /**
     * @return the preventa
     */
    public String getPreventa() {
        return preventa;
    }

    /**
     * @param preventa the preventa to set
     */
    public void setPreventa(String preventa) {
        this.preventa = preventa;
    }

    /**
     * @return the nroOperacion
     */
    public String getNroOperacion() {
        return nroOperacion;
    }

    /**
     * @param nroOperacion the nroOperacion to set
     */
    public void setNroOperacion(String nroOperacion) {
        this.nroOperacion = nroOperacion;
    }

    /**
     * @return the diagnostico
     */
    public DiagnosticoCIE getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(DiagnosticoCIE diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the exonerados
     */
    public Boolean getExonerados() {
        return exonerados;
    }

    /**
     * @param exonerados the exonerados to set
     */
    public void setExonerados(Boolean exonerados) {
        this.exonerados = exonerados;
    }

    /**
     * @return the especificaciones
     */
    public String getEspecificaciones() {
        return especificaciones;
    }

    /**
     * @param especificaciones the especificaciones to set
     */
    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    /**
     * @return the substitutos
     */
    public String getSubstitutos() {
        return substitutos;
    }

    /**
     * @param substitutos the substitutos to set
     */
    public void setSubstitutos(String substitutos) {
        this.substitutos = substitutos;
    }

    /**
     * @return the vendedor
     */
    public Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the prescriptor
     */
    public Prescriptor getPrescriptor() {
        return prescriptor;
    }

    /**
     * @param prescriptor the prescriptor to set
     */
    public void setPrescriptor(Prescriptor prescriptor) {
        this.prescriptor = prescriptor;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the turno
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    /**
     * @return the proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the formaDePago
     */
    public FormaPago getFormaDePago() {
        return formaDePago;
    }

    /**
     * @param formaDePago the formaDePago to set
     */
    public void setFormaDePago(FormaPago formaDePago) {
        this.formaDePago = formaDePago;
    }

    /**
     * @return the tipoDeReceta
     */
    public int getTipoDeReceta() {
        return tipoDeReceta;
    }

    /**
     * @param tipoDeReceta the tipoDeReceta to set
     */
    public void setTipoDeReceta(int tipoDeReceta) {
        this.tipoDeReceta = tipoDeReceta;
    }

    /**
     * @return the numeroDeReceta
     */
    public String getNumeroDeReceta() {
        return numeroDeReceta;
    }

    /**
     * @param numeroDeReceta the numeroDeReceta to set
     */
    public void setNumeroDeReceta(String numeroDeReceta) {
        this.numeroDeReceta = numeroDeReceta;
    }

    /**
     * @return the IdModulo
     */
    public long getIdModulo() {
        return IdModulo;
    }

    /**
     * @param IdModulo the IdModulo to set
     */
    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    /**
     * @return the estado
     */
    public VentaEstadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(VentaEstadoEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the subTotalPreventa
     */
    public BigDecimal getSubTotalPreventa() {
        subTotalPreventa = BigDecimal.ZERO;
        for (VentaProducto ventaProducto : ventaProductos) {
            subTotalPreventa = subTotalPreventa.add(ventaProducto.getImporteTotal())
                    .setScale(3, RoundingMode.HALF_EVEN);
        }
        return subTotalPreventa.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * @param subTotalPreventa the subTotalPreventa to set
     */
    public void setSubTotalPreventa(BigDecimal subTotalPreventa) {
    }

    /**
     * @return the impuestoPreventa
     */
    public BigDecimal getImpuestoPreventa() {
        if (impuestoPreventa == null) {
            impuestoPreventa = BigDecimal.ZERO;
        }
        return impuestoPreventa.setScale(2, RoundingMode.UP);
    }

    /**
     * @param impuestoPreventa the impuestoPreventa to set
     */
    public void setImpuestoPreventa(BigDecimal impuestoPreventa) {
        this.impuestoPreventa = impuestoPreventa;
    }

    /**
     * @return the redondeoPreventa
     */
    public BigDecimal getRedondeoPreventa() {

        BigDecimal total = this.getSubTotalPreventa()
                .add(this.getImpuestoPreventa());

        //resto = total % 10
        //como se trabaja con numeros fraccionarios
        //resto = ParteEnteraDe(total * 100) % 10
        BigDecimal resto = total
                .multiply(new BigDecimal("100"))
                .setScale(0, BigDecimal.ROUND_FLOOR)//solo la parte entera
                .remainder(new BigDecimal("10"));

        BigDecimal ceroUno = new BigDecimal("0.01");
        // si resto > 5, redondeo = (10 - resto) * 0.01
        //ej: si resto = 6, redondeo = (10 - 6) * 0.01 = 0.04
        int comp = resto.compareTo(new BigDecimal("5"));
        if (comp > 0) {
            redondeoPreventa = BigDecimal.TEN.subtract(resto)
                    .multiply(ceroUno);
        } else if (comp <= 0 && resto.compareTo(BigDecimal.ZERO) != 0) {
            //si resto <= 5, redondeo = resto * (-0.01)
            //ej: si resto = 3, redondeo = 3 * (-0.01) = -0.03
            if (resto.multiply(ceroUno).compareTo(this.getImpuestoPreventa()) >= 0) {
                //si resto * 0.01 >= impuesto ent. sumar redondeo (DE LO CONTRARIO NO SE COBRARIA EL IMPUESTO)
                redondeoPreventa = BigDecimal.TEN.subtract(resto)
                        .multiply(ceroUno);
            } else {
                redondeoPreventa = resto.multiply(ceroUno.negate());
            }
        } else {
            redondeoPreventa = BigDecimal.ZERO;
        }
        return redondeoPreventa.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * @param redondeoPreventa the redondeoPreventa to set
     */
    public void setRedondeoPreventa(BigDecimal redondeoPreventa) {
    }

    /**
     * Dice si se realizo la nroOperacion o no
     *
     * @return the procesoVenta
     */
    public Boolean isProcesoVenta() {
        if (procesoVenta == null) {
            return false;
        }
        return procesoVenta;
    }

    /**
     * Indicador de que se realizo la nroOperacion
     *
     * @param procesoVenta the procesoVenta to set
     */
    public void setProcesoVenta(Boolean procesoVenta) {
        this.procesoVenta = procesoVenta;
    }

    /**
     * @return the ventaProductos
     */
    public List<VentaProducto> getVentaProductos() {
        return ventaProductos;
    }

    /**
     * @param ventaProductos the ventaProductos to set
     */
    public void setVentaProductos(List<VentaProducto> ventaProductos) {
        this.ventaProductos = ventaProductos;
    }

    public String getPreventaNetoAPagar() {
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal total = this.getSubTotalPreventa()
                .add(this.getImpuestoPreventa())
                .add(new BigDecimal(this.getRedondeoPreventa().toString()))
                .setScale(2, RoundingMode.UP);
        return df.format(total);
    }

    public void setPreventaNetoAPagar(String netoAPagar) {
    }

    /**
     * @return the modalidad
     */
    public int getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(int modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the puntoDeVenta
     */
    public PuntoDeVenta getPuntoDeVenta() {
        return puntoDeVenta;
    }

    /**
     * @param puntoDeVenta the puntoDeVenta to set
     */
    public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta) {
        this.puntoDeVenta = puntoDeVenta;
    }

    /**
     * @return the ventafechaRegistro
     */
    public Date getVentafechaRegistro() {
        return ventafechaRegistro;
    }

    /**
     * @param VentafechaRegistro the ventafechaRegistro to set
     */
    public void setVentafechaRegistro(Date VentafechaRegistro) {
        this.ventafechaRegistro = VentafechaRegistro;
    }

    /**
     * @return the documento
     */
    public int getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(int documento) {
        this.documento = documento;
    }

    /**
     * @return the motivosAnulacion
     */
    public String getMotivosAnulacion() {
        return motivosAnulacion;
    }

    /**
     * @param motivosAnulacion the motivosAnulacion to set
     */
    public void setMotivosAnulacion(String motivosAnulacion) {
        this.motivosAnulacion = motivosAnulacion;
    }

    /**
     * @return the nroVenta
     */
    public String getNroVenta() {
        return nroVenta;
    }

    /**
     * @param nroVenta the nroVenta to set
     */
    public void setNroVenta(String nroVenta) {
        this.nroVenta = nroVenta;
    }

    /**
     * @return the idUsuarioAnulacion
     */
    public Long getIdUsuarioAnulacion() {
        return idUsuarioAnulacion;
    }

    /**
     * @param idUsuarioAnulacion the idUsuarioAnulacion to set
     */
    public void setIdUsuarioAnulacion(Long idUsuarioAnulacion) {
        this.idUsuarioAnulacion = idUsuarioAnulacion;
    }

    /**
     * @return the anulada
     */
    public Boolean isAnulada() {
        return anulada;
    }

    /**
     * @param anulada the anulada to set
     */
    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }
    
    public String getTipo() {
        return modalidad > 0 ? "Venta Libre": "Preventa";
    }

    /**
     * @return the docTipo
     */
    public String getDocTipo() {
        return docTipo;
    }

    /**
     * @param docTipo the docTipo to set
     */
    public void setDocTipo(String docTipo) {
        this.docTipo = docTipo;
    }

    /**
     * @return the fechaCancelacion
     */
    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    /**
     * @param fechaCancelacion the fechaCancelacion to set
     */
    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    /**
     * @return the servicio
     */
    public Servicio getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (this.getModalidad() == 0) {
            this.setProcesoVenta(false);
        }
    }

    @PreUpdate
    public void preUpdate() {
        super.prePersist();
        if (this.getNroOperacion() != null && this.getNroVenta() != null) {
            this.setProcesoVenta(true);
            if (this.getVentafechaRegistro() == null) {
                Date fechRegistro = GregorianCalendar.getInstance().getTime();
                this.setVentafechaRegistro(fechRegistro);
            }
        }
    }

}
