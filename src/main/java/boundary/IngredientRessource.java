package boundary;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Category;
import entity.Ingredient;


@Stateless // gestion transactionelle (plusieurs users en même temps)
public class IngredientRessource {
	
	@PersistenceContext
    EntityManager em;
    
	  public Ingredient addIngredient(Ingredient ing) {
		  Ingredient res = new Ingredient(ing.getName(), ing.getCategory());
	      res.setId(UUID.randomUUID().toString());
	     
	      this.em.persist(res);
	      return res;
	    }

	 public Ingredient save(Ingredient ing) {
		ing.setId(UUID.randomUUID().toString());
		ing.setName("");
		ing.setCategory(new Category());
        return this.em.merge(ing);
    }

}
