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
    
    /**
     * Id d'une commande
     */
    @Id
    private String id;
    
    /**
     * Date de la commande
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private java.util.Date createdAt = new Date();
    
    /**
     * Booleen indiquant si la commande a ete payee
     */
    private Boolean payed=false;
    
    /**
     * Booleen indiquant si la commande est terminees
     */
    private Boolean finished = false;
    
    /**
     * Sandwichs composant la commande
     */
    //@ManyToMany(cascade = CascadeType.ALL, mappedBy = "id")
    //@JsonManagedReference
    private ArrayList<Sandwich>sandwichs;
    
    /**
     * Constructeur vide
     */
    public OrderSandwich(){
        this.sandwichs = new ArrayList<Sandwich>();
    }
    
    /**
     * Constructeur
     * @param sandwichs sandwichs de la commande
     */
    public OrderSandwich(ArrayList<Sandwich> sandwichs) {
        this.sandwichs = sandwichs;
    }
    
    /**
     * Methode permettant d'obtenir un id de commande
     * @return id de la commande
     */
    public String getId() {
        return id;
    }
    
    /**
     * Methode permettant de definir un id de commande
     * @param id id de la commande
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Methode permettant d'obtenir la date de commande
     * @return date de commande
     */
    public Date getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Methode permettant de definir la date de commande
     * @param createdAt date de commande
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Methode permettant d'obtenir le statut du paiement de commande
     * @return paiement de la commande
     */
    public Boolean getPayed() {
        return payed;
    }
    
    /**
     * Methode permettant de definir la statut de paiement de la commande
     * @param payed statut de paiement
     */
    public void setPayed(Boolean payed) {
        this.payed = payed;
    }
    
    /**
     * Methode permettant d'obtenir le statut d'avancement de la commande
     * @return statut d'avancement de la commande
     */
    public Boolean getFinished() {
        return finished;
    }
    
    /**
     * Methode permettant de definir le statut d'avancement de la commande
     * @param finished statut d'avancement de la commande
     */
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
    
    /**
     * Methode permettant d'obtenir la liste des sandwichs de la commande
     * @return liste des sandwichs de la commande
     */
    public List<Sandwich> getSandwichs() {
        return sandwichs;
    }
    
    /**
     * Methode permettant de definir la liste des sandwichs de la commande
     * @param sandwichs liste des sandwichs de la commande
     */
    public void setSandwichs(ArrayList<Sandwich> sandwichs) {
        this.sandwichs = sandwichs;
    }
    
    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
