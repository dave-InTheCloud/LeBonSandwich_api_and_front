package boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Category;
import entity.CategoryBindIngredient;
import entity.Ingredient;

/**
 * Ressource d'un Ingredient
 */
@Stateless // gestion transactionelle (plusieurs users en m�me temps)
public class IngredientRessource {
    
    /**
     * EntityManager
     */
    @PersistenceContext
            EntityManager em;
    
    /**
     * Methode permettant d'enregistrer un Ingredient
     * @param c ingredient binde avec une categorie
     * @return ingredient enregistre
     */
    public Ingredient save(CategoryBindIngredient c) throws Exception {
        Category categ = this.em.find(Category.class, c.getIdCateg());
        
        if(categ == null)
            throw new Exception("Categorie introuvable");
        
        if(c.getNameIng() == null)
            throw new Exception("Le nom est obligatoire");
        
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
    
    /**
     * Methode permettant de recuperer un ingredient a partir de son identificateur
     * @param id identificateur de l'ingredient
     * @return ingredient recupere
     */
    public Ingredient findById(String id) {
        return this.em.find(Ingredient.class, id);
    }
    
    /**
     * Methode permettant d'obtenir la liste de tous les ingredients
     * @return liste des ingredients
     */
    public List<Ingredient> findAll() {
        Query q = this.em.createNamedQuery("Ingredient.findAll", Ingredient.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }
    
    /**
     * Methode permettant de supprimer un ingredient
     * @param id identificateur de l'ingredient a supprimer
     */
    public void delete(String id) throws Exception {
        Ingredient ref = this.em.find(Ingredient.class, id);
        
        if(ref == null)
            throw new Exception("L'ingredient n'a pas ete trouve.");
        
        this.em.remove(ref);
    }
    
    /**
     * Methode permettant de mettre a jour un ingredient
     * @param id identificateur du pain
     * @param ingredient les nouveaux attributs d'ingredient
     * @return booleen indiquant si l'ingredient a ete cree ou mis a jour
     */
    public boolean update(String id, CategoryBindIngredient ingredient) throws Exception {
        boolean created = false;
        Ingredient ref = this.em.find(Ingredient.class, id);
        
        Category categ = this.em.find(Category.class, ingredient.getIdCateg());
        
        if(categ == null)
            throw new Exception("Categorie introuvable");
        
        if(ref == null) {
            created = true;
            ref = new Ingredient(ingredient.getNameIng(), categ);
            ref.setId(UUID.randomUUID().toString());
        } else {
            ref.setName(ingredient.getNameIng());
            ref.setCategory(categ);
        }
        
        // cancel the save if ingredient already exist in this category
        for (Ingredient contains : categ.getIngredients()) {
            if (contains.getName().equals(ingredient.getNameIng())) {
                return false;
            }
        }
        
        this.em.merge(ref);
        
        return created;
    }
}
