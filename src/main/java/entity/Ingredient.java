package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
    @NamedQuery(name = "Ingredient.findAll", query = "SELECT i FROM Ingredient i")
})
public class Ingredient implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * Id d'un ingredient
     */
    @Id
    private String id;
    
    /**
     * Nom d'un ingredient
     */
    private String name;
    
    /**
     * Categorie d'un ingredient
     */
    @ManyToOne
    @JsonBackReference
    private Category category;
    
    /**
     * Constructeur vide
     */
    public Ingredient(){
        
    }
    
    /**
     * Constructeur
     * @param name nom de l'ingredient
     * @param category categorie de l'ingredient
     */
    public Ingredient(String name, Category category) {
        this.name = name;
        this.category = category;
    }
    
    /**
     * Constructeur
     * @param name nom de l'ingredient
     */
    public Ingredient(String name){
        this.name= name;
    }
    
    /**
     * Methode permettant de recuperer le nom d'un ingredient
     * @return nom de l'ingredient
     */
    public String getName() {
        return name;
    }
    
    /**
     * Methode permettant de definir le nom d'un ingredient
     * @param name nom d'un ingredient
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Methode permettant d'obtenir la categorie d'un ingredient
     * @return categorie d'un ingredient
     */
    public Category getCategory() {
        return category;
    }
    
    /**
     * Methode permettant de recuperer la categorie d'un ingredient
     * @param category categorie d'un ingredient
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * Methode permettant d'obtenir l'id d'un ingredient
     * @return id d'un ingredient
     */
    public String getId() {
        return id;
    }
    
    /**
     * Methode permettant de definir l'id d'un ingredient
     * @param id id d'un ingredient
     */
    public void setId(String id) {
        this.id = id;
    }
    
    
}
