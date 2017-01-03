package boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.Category;
import entity.Ingredient;


@Stateless // gestion transactionelle (plusieurs users en m�me temps)
public class IngredientRessource {
	
	@PersistenceContext
    EntityManager em;
 
	 public Ingredient save(Ingredient ing, String idCateg) {
		Category c = this.em.find(Category.class, idCateg);

		//cancel the save if ingredient already exist in this category
		for(Ingredient contains : c.getIngredients() ){
			if(contains.getName().equals(ing.getName())){
				return contains;
			}
		}
		
		Ingredient i = new Ingredient(ing.getName(), c) ;
		i.setId(UUID.randomUUID().toString());
        return this.em.merge(i);
    }
	 
	 public Ingredient findById(String id) {
	    return this.em.find(Ingredient.class, id);
	 }
	 
	 public List<Ingredient> findAll() {
			Query q = this.em.createNamedQuery("Ingredient.findAll", Ingredient.class);
			// pour éviter les pbs de cache
			q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
			return q.getResultList();
		}
	 
	 public List<Ingredient> findAllByCateg(){
		 Query q = this.em.createNamedQuery("Ingredient.findByCateg", Ingredient.class);
			// pour éviter les pbs de cache
			q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
			return q.getResultList();
	 }

}
