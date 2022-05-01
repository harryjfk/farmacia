package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Matriz")
public class Matriz extends Materias implements Serializable {

    @Column(name = "Porcentaje")
    private String porcentaje;
    
    @Column(name = "Descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "matriz", cascade = CascadeType.ALL)
    private List<MatrizMateria> matrizInsumos;

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List<MatrizMateria> getMatrizInsumos() {
        return this.matrizInsumos;
}
    
    public void setMatrizInsumos(List<MatrizMateria> insumos) {
        this.matrizInsumos = insumos;
    }
}
