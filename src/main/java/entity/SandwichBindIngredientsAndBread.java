package entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by David on 14/02/2017.
 */
@XmlRootElement
public class SandwichBindIngredientsAndBread {

    private List<String> idIngredients;
    private String idBread;
    private int taille;

    public  SandwichBindIngredientsAndBread(){

    }

    public List<String> getIdIngredients() {
        return idIngredients;
    }

    public void setIdIngredients(List<String> idIngredients) {
        this.idIngredients = idIngredients;
    }

    public String getIdBread() {
        return idBread;
    }

    public void setIdBread(String idBread) {
        this.idBread = idBread;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }
}
