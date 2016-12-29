package boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	public List<Category> findAll() {
		Query q = this.em.createNamedQuery("Category.findAll", Category.class);
		// pour Ã©viter les pbs de cache
		q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
		return q.getResultList();
	}

}
