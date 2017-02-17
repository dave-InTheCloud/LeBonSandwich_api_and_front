package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Sandwich.findAll", query = "SELECT i FROM Sandwich i")
})
public class Sandwich implements Serializable  {
	public static final long serialVersionUID = 1L;

	public final static int PETIT_FAIM =  4;
	public final static int MOYENNE_FAIM = 5;
	public final static int GROSSE_FIN =  6;
	public final static int OGRE = 7;

	@Id
	private String id;

	@ManyToOne
	//@JsonManagedReference
	private Bread bread;

	@ManyToMany(fetch = FetchType.EAGER)
	//@JsonManagedReference
	private List<Ingredient>ingredients;

	@ManyToOne
	@JsonBackReference
	OrderSandwich orderSandwich;

	public int taille;

	public Sandwich(){
		
	}
	
	public Sandwich(Bread Bread, ArrayList<Ingredient> ingredients, int taille) {
		this.bread = Bread;
		this.ingredients = ingredients;
		this.taille = taille;
	}
	

	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}


	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public Bread getBread() {
		return bread;
	}

	public void setBread(Bread bread) {
		this.bread = bread;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderSandwich getOrderSandwich() {
		return orderSandwich;
	}

	public void setOrderSandwich(OrderSandwich orderSandwich) {
		this.orderSandwich = orderSandwich;
	}
}
