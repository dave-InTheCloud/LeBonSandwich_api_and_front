package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@XmlRootElement
public class Sandwich implements Serializable  {
    private static final long serialVersionUID = 1L;
    
    
	@Id 
	private String id;
	private String size;
	private String typeBread;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "id")
	@JsonManagedReference
	private List<Ingredient>ingredients;
	
	
	public Sandwich(){
		
	}
	
	public Sandwich(String size, String typeBread, List<Ingredient> ingredients) {
		this.size = size;
		this.typeBread = typeBread;
		this.ingredients = ingredients;
	}
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTypeBread() {
		return typeBread;
	}
	public void setTypeBread(String typeBread) {
		this.typeBread = typeBread;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
}
