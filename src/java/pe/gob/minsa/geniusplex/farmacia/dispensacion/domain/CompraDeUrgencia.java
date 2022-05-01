/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Compra_De_Urgencia")
public class CompraDeUrgencia extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdCompraDeUrgencia")
    private long id;
    
    @JsonDeserialize(as = Paciente.class)
    @JoinColumn(name = "Paciente")
    @ManyToOne
    private Paciente paciente;
    
    @JsonDeserialize(as = Prescriptor.class)
    @JoinColumn(name = "Medico")
    @ManyToOne
    private Prescriptor medico;
    
    @JsonDeserialize(as = List.class, contentAs = CompraUrgenciaProducto.class)
    @OneToMany(mappedBy = "compraDeUrgencia", cascade = CascadeType.ALL)
    private List<CompraUrgenciaProducto> compraProductos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paaciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the medico
     */
    public Prescriptor getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(Prescriptor medico) {
        this.medico = medico;
    }

    /**
     * @return the intervProductos
     */
    public List<CompraUrgenciaProducto> getCompraUrgenciaProductos() {
        return compraProductos;
    }

    /**
     * @param compraProductos the intervProductos to set
     */
    public void setCompraUrgenciaProductos(List<CompraUrgenciaProducto> compraProductos) {
        this.compraProductos = compraProductos;
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
        if (!(object instanceof CompraDeUrgencia)) {
            return false;
        }
        CompraDeUrgencia other = (CompraDeUrgencia) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "CompraDeUrgencia[ id=" + id + " ]";
    }
}
