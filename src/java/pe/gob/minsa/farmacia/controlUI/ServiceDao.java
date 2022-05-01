package pe.gob.minsa.farmacia.controlUI;

import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    public List<SelectBase> listDogs() {
        
        List<SelectBase> dogs = new ArrayList<SelectBase>();

        SelectBase dog = new SelectBase();
        dog.setValue("1");
        dog.setText("Fido");
        dogs.add(dog);

        dog = new SelectBase();
        dog.setValue("2");
        dog.setText("Rayo");
        dogs.add(dog);

        dog = new SelectBase();
        dog.setValue("3");
        dog.setText("Duque");
        dogs.add(dog);

        return dogs;
    }

    public List<SelectBase> listCats() {

        List<SelectBase> cats = new ArrayList<SelectBase>();
        
        SelectBase cat = new SelectBase();
        cat.setValue("1");
        cat.setText("Pelusa");
        cats.add(cat);

        cat = new SelectBase();
        cat.setValue("2");
        cat.setText("Kero");
        cats.add(cat);

        cat = new SelectBase();
        cat.setValue("3");
        cat.setText("Azumi");
        cats.add(cat);

        return cats;
    }
}
