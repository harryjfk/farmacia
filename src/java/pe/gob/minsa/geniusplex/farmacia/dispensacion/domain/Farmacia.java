

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

/**
 *
 * @author stark
 */
public class Farmacia extends BaseEntity  {
    
    private long farmacia;
    private String nombre;

    public Farmacia() {
    }

    public long getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(long farmacia) {
        this.farmacia = farmacia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void prePersist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
