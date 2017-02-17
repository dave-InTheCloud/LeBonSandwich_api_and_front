package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
    @NamedQuery(name = "Bread.findAll", query = "SELECT b FROM Bread b")
})
public class Bread implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id d'un pain
     */
    @Id
    private String id;
    
    /**
     * Nom d'un pain
     */
    private String name;
    
    /**
     * Constructeur vide
     */
    public Bread(){
        
    }
    
    /**
     * Constructeur
     * @param name nom du pain
     */
    public Bread(String name) {
        this.name = name;
    }
    
    /**
     * Methode permettant de recuperer le nom du pain
     */
    public String getName() {
        return name;
    }
    
    /**
     * Methode permettant de definir le nom du pain
     * @param name nom du pain
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Methode permettant de definir un id
     * @param id id du pain
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Methode permettant d'obtenir un id
     * @return id du pain
     */
    public String getId() {
        return this.id;
    }
    
}
