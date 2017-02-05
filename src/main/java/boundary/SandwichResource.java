package boundary;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Sandwich;

public class SandwichResource {
 
	@PersistenceContext
	EntityManager em;
	
	public Sandwich save(Sandwich s){
        s.setId(UUID.randomUUID().toString());
		
		return this.em.merge(s);
	}
}
