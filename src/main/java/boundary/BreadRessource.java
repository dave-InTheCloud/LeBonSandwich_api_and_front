package boundary;

import entity.Bread;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author maxime
 */
@Stateless
public class BreadRessource {
    
    
    	@PersistenceContext
	EntityManager em;

        
        public Bread save(String name, String size){
            Bread b = new Bread();
            
           b.setId(UUID.randomUUID().toString());
           b.setName(name);
           b.setSize(size);
           return this.em.merge(b);
        }
        
        public List<Bread> findAll(){
            Query q = this.em.createNamedQuery("Bread.findAll",Bread.class);
            q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
            return q.getResultList();    
        }
        
        public void delete(String id){
            try{
                Bread ref = this.em.getReference(Bread.class, id);
                this.em.remove(ref);
            }catch(EntityNotFoundException e){
                
            }
        }
    
        
}
