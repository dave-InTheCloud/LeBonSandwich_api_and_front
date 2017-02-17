package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sandwich.findAll", query = "SELECT i FROM Sandwich i")
})
public class Sandwich implements Serializable  {
    /**
     * Taille petite faime
     */
    public final static int PETITE_FAIM =  4;
    
    /**
     * Taille moyenne faim
     */
    public final static int MOYENNE_FAIM = 5;
    
    /**
     * Taille grosse faim
     */
    public final static int GROSSE_FAIM =  6;
    
    /**
     * Taille ogre
     */
    public final static int OGRE = 7;
    public static final long serialVersionUID = 1L;
    
    /**
     * Id du sandwich
     */
    @Id
    private String id;
    
    /**
     * Pain du sandwich
     */
    @ManyToOne
    //@JsonManagedReference
    private Bread bread;
    
    /**
     * Ingredients du sandwich
     */
    @ManyToMany(fetch = FetchType.EAGER)
    //@JsonManagedReference
    private List<Ingredient>ingredients;
    
    /**
     * Taille du sandwich
     */
    public int taille;
    
    /**
     * Constructeur vide de sandwich
     */
    public Sandwich(){
        
    }
    
    /**
     * Constructeur de sandwich
     * @param Bread pain du sandwich
     * @param ingredients ingredients du sandiwh
     * @param taille taille du sandwich
     */
    public Sandwich(Bread Bread, ArrayList<Ingredient> ingredients, int taille) {
        this.bread = Bread;
        this.ingredients = ingredients;
        this.taille = taille;
    }
    
    /**
     * Methode permettant d'obtenir la liste des ingredients
     * @return liste des ingredients du sandwich
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    
    /**
     * Methode permettant de definir la liste des ingredients
     * @param ingredients liste des ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    /**
     * Methode permettant d'obtenir la taille d'un sandwich
     * @return taille du sandwich
     */
    public int getTaille() {
        return taille;
    }
    
    /**
     * Methode permettant de definir la taille d'un sandwich
     * @param taille taille du sandwich
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }
    
    /**
     * Methode permettant d'obtenir le pain du sandwich
     * @return pain du sandwich
     */
    public Bread getBread() {
        return bread;
    }
    
    /**
     * Methode permettant de definir le pain du sandwich
     * @param bread pain du sandwich
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }
    
    /**
     * Methode permettant d'obtenir l'id d'un sandiwch
     * @return id du sandwich
     */
    public String getId() {
        return id;
    }
    
    /**
     * Methode permettant de definir l'id du sandwich
     * @param id id du sandwich
     */
    public void setId(String id) {
        this.id = id;
    }
}
