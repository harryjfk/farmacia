package pe.gob.minsa.farmacia.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class AlmacenTree extends AlmacenComp {
    
    private List<AlmacenComp> almacenes;
    
    public AlmacenTree(){
        almacenes = new ArrayList<AlmacenComp>();
    }

    public List<AlmacenComp> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<AlmacenComp> almacenes) {
        this.almacenes = almacenes;
    }
}


