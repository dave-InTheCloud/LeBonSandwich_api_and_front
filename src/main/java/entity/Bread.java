package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bread.findAll", query = "SELECT b FROM Bread b")
})
public class Bread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String name;


	public Bread(){

	}

	public Bread(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

        public void setId(String id) {
		this.id = id;
	}


	public String getId() {
		return this.id;
	}

}
