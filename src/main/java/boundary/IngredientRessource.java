package boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Category;
import entity.CategoryBindIngredient;
import entity.Ingredient;

@Stateless // gestion transactionelle (plusieurs users en m�me temps)
public class IngredientRessource {

	@PersistenceContext
	EntityManager em;

	public Ingredient save(CategoryBindIngredient c) {
		Category categ = this.em.find(Category.class, c.getIdCateg());

		// cancel the save if ingredient already exist in this category
		for (Ingredient contains : categ.getIngredients()) {
			if (contains.getName().equals(c.getNameIng())) {
				return contains;
			}
		}

		Ingredient i = new Ingredient(c.getNameIng(), categ);
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

	public void delete(String id) {
		try {
			Ingredient ref = this.em.getReference(Ingredient.class, id);
			this.em.remove(ref);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Ingredient update(String id, CategoryBindIngredient c) {
		try {
			Ingredient ref = this.em.getReference(Ingredient.class, id);
			Category categ = this.em.find(Category.class, c.getIdCateg());
			ref.setCategory(categ);
			ref.setName(c.getNameIng());
			return ref;

		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			// renvoyer un status ou un réponse
			return null;
		}
	}

}
