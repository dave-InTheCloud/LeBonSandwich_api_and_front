package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@XmlRootElement
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	private String id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "name")
	@JsonManagedReference
	private List<Ingredient>ingredients;
	
	public Category(){
		
	}
	

	public Category(String name, List<Ingredient> ingredients) {
		this.name = name;
		this.ingredients = ingredients;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


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

	
	
}
