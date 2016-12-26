package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Bread implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private String size;
	
	
	public Bread(){
		
	}
	
	public Bread(String name, String size) {
		this.name = name;
		this.size = size;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
}
