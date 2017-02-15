package boundary;

import entity.User;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserRessource {
    
    @PersistenceContext
        EntityManager em;
    
    public User save(User user) throws Exception{
        User u = new User(user);
        u.setId(UUID.randomUUID().toString());
        this.em.persist(u);
        return u;
    }
    
    public User findById(String id){
        return this.em.find(User.class, id);
    }
    
    public List<User> findAll(){
        Query q = this.em.createNamedQuery("User.findAll", User.class);
        q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
        
        return q.getResultList();
    }
}
