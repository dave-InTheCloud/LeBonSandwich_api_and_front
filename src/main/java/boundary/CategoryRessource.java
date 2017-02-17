package boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Category;
import entity.Ingredient;

/**
 * Ressource des Categories
 */
@Stateless
public class CategoryRessource {
    /**
     * EntityManager
     */
    @PersistenceContext
            EntityManager em;
    
    /**
     * Methode permettant d'enregistrer une categorie d'ingredients
     *
     * @param categ categorie a enregistrer
     * @return categorie enregistree
     */
    public Category save(Category categ) throws Exception { 
        if(categ.getName() != null) {
            Category c = new Category(categ.getName(), new ArrayList<Ingredient>(), categ.getLimiteNbIngredient());
            c.setId(UUID.randomUUID().toString());
            return this.em.merge(c);
        } else throw new Exception("Donnees manquantes");
    }
    
    /**
     * Methode permettant de recuperer toutes les categories d'ingredients
     *
     * @return liste des categories
     */
    public List<Category> findAll() {
        Query q = this.em.createNamedQuery("Category.findAll", Category.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }
    
    /**
     * Methode permettant de supprimer une categorie d'ingredients
     *
     * @param id identificateur de la categorie
     * @throws java.lang.Exception
     */
    public void delete(String id) throws Exception {
        Category ref = this.em.find(Category.class, id);
        
        if(ref == null)
            throw new Exception("La categorie recherchee n'a pas été trouvée.");
        
        this.em.remove(ref);
    }
    
    /**
     * Methode permettant de recuperer une categorie
     *
     * @param id identificateur de la categorie
     * @return categorie identifiee
     */
    public Category findById(String id) {
        return this.em.find(Category.class, id);
    }
    
    /**
     * Methode permettant de mettre a jour une categorie
     * @param id identificateur du pain
     * @param bread les nouveaux attributs de pain
     * @return pain mis a jour
     */
    public boolean update(String id, Category category) {
        boolean created = false;
        Category ref = this.em.find(Category.class, id);
        
        if(ref == null) {
            created = true;
            ref = new Category();
            ref.setName(category.getName());
            ref.setId(id);
            ref.setLimiteNbIngredient(category.getLimiteNbIngredient());
        } else {
            ref.setName(category.getName());
            ref.setIngredients(ref.getIngredients());
        }
        
        this.em.merge(ref);
        
        return created;
    }
}
