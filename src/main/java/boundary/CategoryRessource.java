package boundary;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Category;



@Stateless // gestion transactionelle (plusieurs users en même temps)
public class CategoryRessource {
	
	@PersistenceContext
    EntityManager em;
    
	 

	 public Category save(Category categ) {
		 
		 Category c = new Category();
		 c.setId(UUID.randomUUID().toString());
		 c.setName(categ.getName());
        return this.em.merge(c);
    }

}
