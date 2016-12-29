package boundary;

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


@Stateless // gestion transactionelle (plusieurs users en même temps)
public class IngredientRessource {
	
	@PersistenceContext
    EntityManager em;
 
	 public Ingredient save(Ingredient ing, String idCateg) {
		Category c = this.em.find(Category.class, idCateg);
		Ingredient i = new Ingredient(ing.getName(), c) ;
		i.setId(UUID.randomUUID().toString());
        return this.em.merge(i);
    }
	 
	 public Ingredient findById(String id) {
	    return this.em.find(Ingredient.class, id);
	 }
	 
	 public List<Ingredient> findAll() {
			Query q = this.em.createNamedQuery("Ingredient.findAll", Ingredient.class);
			// pour Ã©viter les pbs de cache
			q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
			return q.getResultList();
		}

}
