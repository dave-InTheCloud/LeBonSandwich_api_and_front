package entity;

import boundary.CategoryRepresentation;
import boundary.IngredientRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import javax.persistence.Transient;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
})
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name= "_links")
    @Transient
    private List<Link> links = new ArrayList<>();
    
    /**
     * Id de la categorie
     */
    @Id
    private String id;
    
    /**
     * Nom de la cateogire
     */
    private String name;
    
    /**
     * Limite d'ingredients de la categorie
     */
    private int limiteNbIngredient;
    
    /**
     * Liste des ingredients de la categorie
     */
    @OneToMany(orphanRemoval = true, mappedBy = "category", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Ingredient>ingredients;
    
    /**
     * Constructeur vide
     */
    public Category(){
        
    }
    
    /**
     * Constructeur
     * @param name nom de la categorie
     * @param ingredients ingredients de la categorie
     * @param limiteNbIngredient limite du nombre d'ingredients de la categorie
     */
    public Category(String name, List<Ingredient> ingredients, int limiteNbIngredient) {
        this.name = name;
        this.ingredients = ingredients;
        this.limiteNbIngredient = limiteNbIngredient;
    }
    
    /**
     * Methode permettant d'obtenir le nom de la categorie
     * @return nom de la categorie
     */
    public String getName() {
        return name;
    }
    
    /**
     * Methode permettant de definir le nom de la categorie
     * @param name nom de la categorie
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * Methode permettant d'obtenir les ingredients de la categorie
     * @return  liste des ingredients de la categorie
     */
    @JsonIgnore
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    /**
     * Methode pemettant de definir la liste des ingredients de la categorie
     * @param ingredients liste des ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    /**
     * Methode permettant de definir l'id de la categorie
     * @param id id de la categorie
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Methode permettant d'obtenir l'id de la categorie
     * @return id de la categorie
     */
    public String getId() {
        return this.id;
    }
    /**
     * Methode permettant d'obtenir le nombre limite d'ingredients de la categorie
     * @return
     */
    public int getLimiteNbIngredient() {
        return limiteNbIngredient;
    }
    
    public List<Link> getLinks(){
        return this.links;
    }
    
    public void addLink(String uri, String rel) {
        this.links.add(new Link(uri, rel));
    }
    
    public String getSelfUri(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder()
                .path(CategoryRepresentation.class)
                .path(this.id)
                .build()
                .toString();
    }
    
    public String getIngredientUri(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder()
                .path(CategoryRepresentation.class)
                .path(this.id)
                .path(IngredientRepresentation.class)
                .build()
                .toString();
    }
    /**
     * Methode permettant de definir le nombre limite d'ingredients de la categorie
     * @param limiteNbIngredient nombre limite d'ingredients de la categorie
     */
    public void setLimiteNbIngredient(int limiteNbIngredient) {
        this.limiteNbIngredient = limiteNbIngredient;
    }
}
