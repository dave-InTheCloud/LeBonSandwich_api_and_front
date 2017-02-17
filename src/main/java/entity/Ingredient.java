package entity;

import boundary.CategoryRepresentation;
import boundary.IngredientRepresentation;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;


@Entity
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
    @NamedQuery(name = "Ingredient.findAll", query = "SELECT i FROM Ingredient i")
})
public class Ingredient implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String name;
    
    @ManyToOne
    @JsonBackReference
    private Category category;
    
    @XmlElement(name= "_links")
    @Transient
    private List<Link> links = new ArrayList<>();
    
    public Ingredient(){
        
    }
    
    public Ingredient(String name, Category category) {
        this.name = name;
        this.category = category;
    }
    
    public Ingredient(String name){
        this.name= name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<Link> getLinks(){
        return this.links;
    }
    
    public void addLink(String uri, String rel) {
        this.links.add(new Link(uri, rel));
    }
    
    public String getSelfUri(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder()
                .path(IngredientRepresentation.class)
                .path(this.id)
                .build()
                .toString();
    }

    public String getCategoryUri(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder()
                .path(IngredientRepresentation.class)
                .path(this.id)
                .path(CategoryRepresentation.class)
                .build()
                .toString();
    }
    
}
