package entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by David on 14/02/2017.
 */
@XmlRootElement
public class SandwichBindIngredientsAndBread {
    /**
     * Liste des id des ingredients d'un sandwich
     */
    private List<String> idIngredients;
    
    /**
     * Id du Pain d'un sandwich
     */
    private String idBread;
    
    /**
     * Taille d'un sandwich
     */
    private int taille;
    
    /**
     * Constructeur vide
     */
    public  SandwichBindIngredientsAndBread(){
        
    }
    
    /**
     * Methode permettant d'obtenir la liste des ids des ingredients d'un sandwich
     * @return
     */
    public List<String> getIdIngredients() {
        return idIngredients;
    }
    
    /**
     * Methode permettant de definir la liste des ids des ingredients d'un sandwich
     * @param idIngredients  liste des ids des ingredients d'un sandwich
     */
    public void setIdIngredients(List<String> idIngredients) {
        this.idIngredients = idIngredients;
    }
    
    /**
     * Methode permettant d'obtenir l'id du pain d'un sandwich
     * @return id du pain d'un sandwich
     */
    public String getIdBread() {
        return idBread;
    }
    
    /**
     * Methode permettant de definir l'id du pain d'un sandwich
     * @param idBread id du pain d'un sandwich
     */
    public void setIdBread(String idBread) {
        this.idBread = idBread;
    }
    
    /**
     * Methode permettant d'obtenir la taille du sandwich
     * @return taille du sandwich
     */
    public int getTaille() {
        return taille;
    }
    
    /**
     * Methode permettant de definir la taille d'un sandwich
     * @param taille taille d'un sandwich
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }
}
