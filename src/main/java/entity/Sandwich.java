package entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@XmlRootElement
public class Sandwich {
		
	@Id 
	private String id;
	private String size;
	private String typeBread;
	@ManyToOne
	@JsonBackReference
	private List<Ingredient>ingredients;
	
	
	public Sandwich(){
		
	}
	
	public Sandwich(String size, String typeBread, List<Ingredient> ingredients) {
		super();
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
