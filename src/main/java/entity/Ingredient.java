package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingredient.findAll", query = "SELECT i FROM Ingredient i")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	private String id;
	private String name;
	
	@ManyToOne
	@JsonBackReference
	private Category category;
	
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

	
}
