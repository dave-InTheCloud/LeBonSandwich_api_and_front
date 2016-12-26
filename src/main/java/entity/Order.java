package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@XmlRootElement
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	private String id;
	
    @Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdAt;
	private Boolean payed=false;
	private Boolean finished = false;
	
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "id")
	@JsonManagedReference
	private List<Sandwich>sandwichs;


	public Order(){
		
	}
	
	public Order(List<Sandwich> sandwichs) {
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


	public void setSandwichs(List<Sandwich> sandwichs) {
		this.sandwichs = sandwichs;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
