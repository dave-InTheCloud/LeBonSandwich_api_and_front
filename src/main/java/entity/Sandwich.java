package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@XmlRootElement
public class Sandwich implements Serializable  {
    private static final long serialVersionUID = 1L;
    
    
	@Id 
	private String id;
	
	@OneToOne
	@JsonManagedReference
	private Bread bread;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "id")
	@JsonManagedReference
	private List<Ingredient>ingredients;
	
	
	public Sandwich(){
		
	}
	
	public Sandwich(Bread Bread, List<Ingredient> ingredients) {
		this.bread = Bread;
		this.ingredients = ingredients;
	}
	
	public Bread getTypeBread() {
		return bread;
	}
	
	public void setTypeBread(Bread typeBread) {
		this.bread = typeBread;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
}
