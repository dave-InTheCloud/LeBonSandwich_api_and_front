package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CategoryBindIngredient {
	
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
