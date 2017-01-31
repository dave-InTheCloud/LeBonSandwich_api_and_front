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
     * @param categ categorie a enregistrer
     * @return categorie enregistree
     */
    public Category save(Category categ) {
        Category c = new Category(categ.getName(), new ArrayList<Ingredient>());
        c.setId(UUID.randomUUID().toString());
        return this.em.merge(c);
    }
    
    /**
     * Methode permettant de recuperer toutes les categories d'ingredients
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
     * @param id identificateur de la categorie
     */
    public void delete(String id){
        try {
            Category ref = this.em.getReference(Category.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }  
    }
    
    /**
     * Methode permettant de recuperer une categorie
     * @param id identificateur de la categorie
     * @return categorie identifiee
     */
    public Category findById(String id){
        return this.em.find(Category.class, id);
    }
    
    /**
     * Methode permettant de mettre a jour une categorie
     * @param id identificateur de la categorie
     * @param categ categorie modifiee
     */
    public void update(String id,Category categ) throws Exception {
        try {
            Category ref = this.em.getReference(Category.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon 
        }
    }
    
    
}
