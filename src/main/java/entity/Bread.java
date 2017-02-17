package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
    @NamedQuery(name = "Bread.findAll", query = "SELECT b FROM Bread b")
})
public class Bread implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String name;
        
        @XmlElement(name= "_links")
        @Transient
        private List<Link> links = new ArrayList<>();
        
        

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

        public List<Link> getLinks(){
            return this.links;
        }
        
        public void addLink(String uri, String rel) {
            this.links.add(new Link(uri, rel));
        }
        
}
