/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author stark
 */
@Entity
@Table(name="Far_Devoluciones")
public class Devolucion  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Servicio")
    private String servicio;
    @Column(name = "Tipo")
    private String tipo;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "Paciente")
    private Paciente paciente;
    @Column(name = "Cantidad")
    private int cantidad;
    @Column(name = "ValorUnitario")
    private BigDecimal valorUnitario;
    @Column(name = "Observaciones")
    private String observaciones;
    @ManyToOne
    @JoinColumn(name = "Producto")
    private GpProducto produto;
    @Column(name = "IdModulo")
    private long IdModulo;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.clinica.domain.Devolucion[ id=" + id + " ]";
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.paciente != null ? this.paciente.hashCode() : 0);
        hash = 59 * hash + (this.produto != null ? this.produto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Devolucion other = (Devolucion) obj;
        if (this.paciente != other.paciente && (this.paciente == null || !this.paciente.equals(other.paciente))) {
            return false;
        }
        if (this.produto != other.produto && (this.produto == null || !this.produto.equals(other.produto))) {
            return false;
        }
        return true;
    }

    public GpProducto getProduto() {
        return produto;
    }

    public void setProduto(GpProducto produto) {
        this.produto = produto;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }
    public BigDecimal getValorTotal(){
        return getValorUnitario().multiply(new BigDecimal(cantidad));
    }
}
