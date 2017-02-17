package entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT s FROM OrderSandwich s")
})

public class OrderSandwich implements Serializable {

    public final static int PAYER = 1;
    public final static int FABRICATION = 2;
    public final static int FINI = 3;

    @Id
    private String id;


    //@Temporal(TemporalType.TIME)
    //@Column(name = "dateEnvoie", nullable = true)
    private String dateEnvoie;


    private int status;

    //@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "orderSandwich")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Sandwich> sandwichs;

    public OrderSandwich() {
    }

    public OrderSandwich(String dateEnvoie) {
        this.sandwichs = new HashSet<Sandwich>();
        this.status = 0;
        this.dateEnvoie = dateEnvoie;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(String dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public Set<Sandwich> getSandwichs() {
        return sandwichs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSandwichs(Set<Sandwich> sandwichs) {
        this.sandwichs = sandwichs;
    }

    public void addSandwich(Sandwich s){ this.sandwichs.add(s);}

}
