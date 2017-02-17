package entity;

import boundary.UserRepresentation;
import control.PasswordManagement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe modelisant un utilisateur
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u where u.email =:email")
})
public class User {
    
    /**
     * Id de l'utilisateur
     */
    @Id
    private String id;
    
    /**
     * Nom de l'utilisateur
     */
    private String name;
    /**
     * Email de l'utilisateur
     */
    private String email;
    /**
     * Mot de passe de l'utilisateur
     */
    private String password;
    
    @XmlElement(name= "_links")
    @Transient
    private List<Link> links = new ArrayList<>();
    /**
     * Constructeur vide d'un utilisateur
     */
    public User() {
        this.email="";
        this.password="";
        this.name="";
    }
    
    /**
     * Constructeur a partir d'un objet utilisateur
     * @param user utilisateur a copier
     * @throws Exception exception si il manque des données
     */
    public User(User user) throws Exception {
        if(user.email.equals("") || user.password.equals("") || user.name.equals(""))
            throw new Exception("Il manque des données");
        else{
            this.email = user.email;
            this.password = user.password;
            this.name = user.name;
        }
    }
    
    /**
     * getter du nom
     * @return nom de l'utilisateur
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter du nom
     * @param name nom a donner
     * @throws Exception si le nom n'est pas valide
     */
    public void setName(String name) throws Exception {
        if(!name.equals(""))
            this.name = name;
        else throw new Exception("Name invalide");
    }
    
    /**
     * Getter de l'email
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Setter de l'email
     * @param email
     * @throws Exception
     */
    public void setEmail(String email) throws Exception {
        if(!email.equals(""))
            this.email = email;
        else throw new Exception("Email invalide");
    }
    
    /**
     * Setter du mot de passe
     * @param password
     * @throws Exception
     */
    public void setPassword(String password) throws Exception {
        if(!password.equals(""))
            this.password = password;
        else throw new Exception("Mot de passe invalide");
    }
    
    /**
     * Getter de l'id
     * @return
     */
    public String getId() {
        return id;
    }
    
    /**
     * Setter de l'id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Méthode permettant de verifier les identifiant d'un utilisateur
     * lors de l'authentification
     * @param user utilisateur a authentifier
     */
    public void authentifie(User user) {
        if(!(this.email.equals(user.email) && BCrypt.checkpw(user.password, this.password)))
            throw new NotAuthorizedException("Problème d'authentification");
    }
    
    /**
     * Méthode permettant d'hasher un mot de passe
     */
    public void hashPassword() {
        this.password = PasswordManagement.digestPassword(this.password);
    }
    
    public List<Link> getLinks(){
        return this.links;
    }
    
    public void addLink(String uri, String rel) {
        this.links.add(new Link(uri, rel));
    }
    
    public String getSelfUri(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder()
                .path(UserRepresentation.class)
                .path(this.id)
                .build()
                .toString();
    }
}
