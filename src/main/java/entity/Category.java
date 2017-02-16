package entity;

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
	
	@OneToMany(orphanRemoval = true, mappedBy = "category", fetch = FetchType.EAGER)
	@JsonManagedReference
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
}
