package entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 17/02/2017.
 */

@XmlRootElement
public class OrderBindSandwich {

    private List<String> idSandwichs;

    private String dateEnvoie;

    public OrderBindSandwich(){}

    public List<String> getIdSandwichs() {
        return idSandwichs;
    }

    public void setIdSandwichs(List<String> idSandwichs, String dateEnvoie) {
        this.idSandwichs = idSandwichs;
        this.dateEnvoie = dateEnvoie;
    }

    public String getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(String dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public void setIdSandwichs(List<String> idSandwichs) {
        this.idSandwichs = idSandwichs;
    }
}
