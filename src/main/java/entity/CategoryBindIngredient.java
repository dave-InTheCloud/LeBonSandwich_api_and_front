package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class CategoryBindIngredient {
	
	// this entity is use in method save of ingredient
	@Id
	private String id;
	private String idCateg, nameIng;
	
	public CategoryBindIngredient(){
		
	}
	
	public CategoryBindIngredient(String idCateg) {
		this.idCateg = idCateg;
	}

	public String getIdCateg() {
		return idCateg;
	}

	public void setIdCateg(String idCateg) {
		this.idCateg = idCateg;
	}

	public String getNameIng() {
		return nameIng;
	}

	public void setNameIng(String nameIng) {
		this.nameIng = nameIng;
	}
	
	
	

}
