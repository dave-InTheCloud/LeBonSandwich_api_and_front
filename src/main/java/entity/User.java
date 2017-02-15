package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User {

    @Id
    private String id;
    
    private String name;
    private String email;
    private String password;

    public User() {}
    
    public User(User user) throws Exception {
        this.setName(user.name);
        this.setEmail(user.email);
        this.setPassword(user.password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(!name.equals(""))
            this.name = name;
        else throw new Exception("Name invalide");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(!email.equals(""))
            this.email = email;
        else throw new Exception("Email invalide");
    }

    public void setPassword(String password) throws Exception {
        if(!password.equals(""))
            this.password = password;
        else throw new Exception("Mot de passe invalide");
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
