package entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderSandwich.findAll", query = "SELECT s FROM OrderSandwich s")
})
public class OrderSandwich implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	private String id;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
	private java.util.Date createdAt = new Date();
	private Boolean payed=false;
	private Boolean finished = false;
	
	
	//@ManyToMany(cascade = CascadeType.ALL, mappedBy = "id")
	//@JsonManagedReference
	private ArrayList<Sandwich>sandwichs;

	public OrderSandwich(){
		this.sandwichs = new ArrayList<Sandwich>(); 
	}
	
	public OrderSandwich(ArrayList<Sandwich> sandwichs) {
		this.sandwichs = sandwichs;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Boolean getPayed() {
		return payed;
	}


	public void setPayed(Boolean payed) {
		this.payed = payed;
	}


	public Boolean getFinished() {
		return finished;
	}


	public void setFinished(Boolean finished) {
		this.finished = finished;
	}


	public List<Sandwich> getSandwichs() {
		return sandwichs;
	}


	public void setSandwichs(ArrayList<Sandwich> sandwichs) {
		this.sandwichs = sandwichs;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
