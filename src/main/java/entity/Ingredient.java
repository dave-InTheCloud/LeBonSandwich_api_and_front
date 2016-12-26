package entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@XmlRootElement
public class Ingredient {

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
	
	
}
