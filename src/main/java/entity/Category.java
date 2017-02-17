package entity;

import boundary.CategoryRepresentation;
import boundary.IngredientRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    
    @Id
    private String id;
    private String name;
    
    private int limiteNbIngredient;
    
    @XmlElement(name= "_links")
    @Transient
    private List<Link> links = new ArrayList<>();
    
    
    @OneToMany(orphanRemoval = true, mappedBy = "category", fetch = FetchType.EAGER)
    private List<Ingredient>ingredients;
    
    public Category(){
        
    }
    
    public Category(String name, List<Ingredient> ingredients, int limiteNbIngredient) {
        this.name = name;
        this.ingredients = ingredients;
        this.limiteNbIngredient = limiteNbIngredient;
    }
    
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    @JsonIgnore
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    
    
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    
    public void setId(String id) {
        this.id = id;
    }
    
    
    public String getId() {
        return this.id;
    }
    
    public int getLimiteNbIngredient() {
        return limiteNbIngredient;
    }
    
    public void setLimiteNbIngredient(int limiteNbIngredient) {
        this.limiteNbIngredient = limiteNbIngredient;
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
}
