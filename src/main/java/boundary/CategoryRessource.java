package boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Category;
import entity.Ingredient;

@Stateless // gestion transactionelle (plusieurs users en m�me temps)
public class CategoryRessource {

	@PersistenceContext
	EntityManager em;

	public Category save(Category categ) {
		Category c = new Category(categ.getName(), new ArrayList<Ingredient>());
		c.setId(UUID.randomUUID().toString());
		return this.em.merge(c);
	}

	public List<Category> findAll() {
		Query q = this.em.createNamedQuery("Category.findAll", Category.class);
		// pour éviter les pbs de cache
		q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
		return q.getResultList();
	}

	public void delete(String id) {
		try {
			Category ref = this.em.getReference(Category.class, id);
			this.em.remove(ref);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Category findById(String id) {
		return this.em.find(Category.class, id);
	}

	public Category update(String id, Category categ) {
		try {
			Category ref = this.em.getReference(Category.class, id);
			ref.setName(categ.getName());
			ref.setIngredients(categ.getIngredients());
			return ref;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

}
