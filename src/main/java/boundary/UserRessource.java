package boundary;

import entity.User;
import exception.NoResultException;
import exception.AlreadyExistException;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Classe permettant la gestion des données d'un user
 */
@Stateless
public class UserRessource {
    /**
     * Objet faisant le lien avec la base de données
     */
    @PersistenceContext
            EntityManager em;
    
    /**
     * Methode permettant l'ajout d'un utilisateur dans la base de données
     * @param user l'utilisateur a ajouter
     * @return utilisateur ajouté a la base
     * @throws AlreadyExistException  Si un utilisateur existe deja avec cette adresse email
     * @throws Exception
     */
    public User save(User user) throws Exception{
        try{
            List<User> users = this.findAll();
            // On annule si un user est deja crée avec l'adresse mail donnée
            for (User contains : users) {
                if (contains.getEmail().equals(user.getEmail())) {
                    throw new AlreadyExistException("Adresse mail déjà utilisée");
                }
            }
        } catch(NoResultException e){}
        
        
        User u = new User(user);
        u.setId(UUID.randomUUID().toString());
        u.hashPassword();
        this.em.persist(u);
        return u;
    }
    
    /**
     * Methode permettant de rechercher un utilisateur via son id
     * @param id id de l'utilisateur a chercher
     * @return utilisateur
     */
    public User findById(String id){
        return this.em.find(User.class, id);
    }
    
    /**
     * Methode permettant de rechercher tous les utilisateurs de la base de donnée
     * @return liste des utilisateurs
     * @throws NoResultException Si il n'y a aucun résultat dans la base de donnée
     */
    public List<User> findAll() throws NoResultException{
        Query q = this.em.createNamedQuery("User.findAll", User.class);
        q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
        List<User> resultList = q.getResultList();
        if(resultList.isEmpty()) throw new NoResultException();
        return resultList;
    }
    
    /**
     * Methode permettant de retrouver un utilisateur via son adresse mail
     * @param email email de l'utilisateur
     * @return utilisateur trouvé
     */
    public User findByEmail(String email) {
        Query q = this.em.createNamedQuery("User.findByEmail", User.class);
        q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
        
        q.setParameter("email", email);
        q.setFirstResult(0);
        q.setMaxResults(1);
        
        return (User)q.getSingleResult();
    }
    
    /**
     * Methode permettant de modifier un utilisateur
     * @param user utilisateur a modifier
     * @return utilisateur modifié
     */
    public User update(User user) throws AlreadyExistException {
        User u = this.findById(user.getId());
        user.hashPassword();
        if(u == null){
            //Si aucun utilisateur n'est trouvé, on en crée un
            try{
                List<User> users = this.findAll();
                // On annule si un user est deja crée avec l'adresse mail donnée
                for (User contains : users) {
                    if (contains.getEmail().equals(user.getEmail())) {
                        throw new AlreadyExistException("Adresse mail déjà utilisée");
                    }
                }
            } catch(NoResultException e){}
            user.setId(UUID.randomUUID().toString());
            this.em.persist(user);
            return user;
        } else{
            return this.em.merge(user);
        }
    }
    
    /**
     * Methode permettant de supprimer un utilisateur via son id
     * @param id id de l'utilisateur a supprimer
     */
    public void delete(String id) throws NoResultException {
        User user = this.em.find(User.class, id);
        if(user == null)
            throw new NoResultException();
        this.em.remove(user);
    }
}
