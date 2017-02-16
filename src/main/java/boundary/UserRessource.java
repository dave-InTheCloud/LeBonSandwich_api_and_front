package boundary;

import entity.User;
import exception.NoResultException;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserRessource {
    
    @PersistenceContext
            EntityManager em;
    
    public User save(User user) throws Exception{
        try{
            List<User> users = this.findAll();
            // On annule si un user est deja crée avec l'adresse mail donnée
            for (User contains : users) {
                if (contains.getEmail().equals(user.getEmail())) {
                    throw new Exception("L'adresse mail est déjà utilisée");
                }
            }
        } catch(NoResultException e){}
        
        
        User u = new User(user);
        u.setId(UUID.randomUUID().toString());
        u.hashPassword();
        this.em.persist(u);
        return u;
    }
    
    public User findById(String id){
        return this.em.find(User.class, id);
    }
    
    public List<User> findAll() throws NoResultException{
        Query q = this.em.createNamedQuery("User.findAll", User.class);
        q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
        List<User> resultList = q.getResultList();
        if(resultList.isEmpty()) throw new NoResultException();
        return resultList;
    }
    
    public User findByEmail(String email) {
        Query q = this.em.createNamedQuery("User.findByEmail", User.class);
        q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
        
        q.setParameter("email", email);
        q.setFirstResult(0);
        q.setMaxResults(1);
        
        return (User)q.getSingleResult();
    }
    
    public User update(User user) {
        return this.em.merge(user);
    }
    
    public void delete(String id) {
        try {
            User user = this.em.getReference(User.class, id);
            this.em.remove(user);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
