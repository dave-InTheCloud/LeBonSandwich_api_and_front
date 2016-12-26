package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@XmlRootElement
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	private String id;
	private String name;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "name")
	@JsonManagedReference
	private List<Ingredient>ingredients;
	
	public Category(){
		
	}
	
	public Category(String name) {
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
