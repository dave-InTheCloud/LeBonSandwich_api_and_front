package boundary;

import entity.Bread;
import entity.Ingredient;
import entity.Sandwich;
import entity.SandwichBindIngredientsAndBread;
import exception.SandwichBadRequest;
import exception.SandwichNotFoundExeception;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.NoContentException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Ressource d'un sandwich
 */
@Stateless
public class SandwichResource {
    /**
     * EntityManager
     */
    @PersistenceContext
            EntityManager em;
    
    /**
     * Methode permettant de creer un sandwich
     * @param s sandwich a creer
     * @return sandwich cree
     * @throws EntityNotFoundException
     * @throws BadRequestException
     * @throws SandwichBadRequest
     */
    public Sandwich create(SandwichBindIngredientsAndBread s) throws EntityNotFoundException, BadRequestException, SandwichBadRequest {
        Sandwich res = new Sandwich();
        res.setId(UUID.randomUUID().toString());
        int tailleSandwich = s.getTaille();
        
        if (tailleSandwich >= 4 && tailleSandwich <= 7) {
            res.setTaille(tailleSandwich);
        } else {
            throw new SandwichBadRequest("La Taille doit etre comprise entre 7 et 9");
        }
        
        
        /*
        if (s.getIdIngredients().size() != tailleSandwich) {
        throw new Exception("Nombre d'ingrédients incorrect");
        }
        */
        
        //get bread by id and add to sandwich
        Bread b = this.em.find(Bread.class, s.getIdBread());
        if (b != null) {
            res.setBread(b);
        } else {
            throw new SandwichBadRequest("ID pain inexistant");
        }
        //get all id in list of id ingredients and add ingredients to sandwich
        List<Ingredient> listIng = new ArrayList<Ingredient>();
        
        for (int i = 0; i < s.getIdIngredients().size(); i++) {
            if (s.getIdIngredients().get(i) != null) {
                listIng.add(this.em.find(Ingredient.class, s.getIdIngredients().get(i)));
            } else {
                throw new SandwichBadRequest("Un id d'ingredients n'existe pas");
            }
        }
        
        
        /* verfié le nombre d'ingredient max
        
        int nbLimiteCateg = contains.getCategory().getLimiteNbIngredient();
        
        if (listIngredient.size() == nbLimiteCateg) {
        throw new Exception("Limite d'ingredients atteint pour la categorie " + contains.getCategory().getName());
        }
        */
        /*
        switch (tailleSandwich) {
        case Sandwich.PETIT_FAIM:
        if (nbViande > 1) {
        throw new Exception("Nombre de viande invalide pour cette taille");
        }
        
        break;
        
        case Sandwich.MOYENNE_FAIM:
        if (nbViande > 2) {
        throw new Exception("Nombre de viande invalide pour cette taille");
        }
        break;
        
        case Sandwich.GROSSE_FIN:
        if (nbViande > 3) {
        throw new Exception("Nombre de viande invalide pour cette taille");
        }
        break;
        
        case Sandwich.OGRE:
        if (nbViande > 4) {
        throw new Exception("Nombre de viande invalide pour cette taille");
        }
        break;
        
        default:
        throw new Exception("Taille de sandwich invalide");
        }
        */
        
        res.setIngredients(listIng);
        
        
        return this.em.merge(res);
    }
    
    /**
     * Methode permettant de retourner la liste de tous les sandwichs
     * @return liste des sandwichs
     * @throws NoContentException
     */
    public List<Sandwich> findAll() throws NoContentException{
        Query q = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        List<Sandwich> res = q.getResultList();
        if(res.size() != 0){
            return res;
        }else{
            throw (new NoContentException("Pas de sandwich pour le moment"));
        }
        
    }
    
    /**
     * Methode permettant de retourner un sandwich
     * @param id id du sandwich
     * @return sandwich identifie
     * @throws SandwichNotFoundExeception
     */
    public Sandwich findById(String id) throws SandwichNotFoundExeception {
        Sandwich s = this.em.find(Sandwich.class, id);
        if(s == null){
            throw  new SandwichNotFoundExeception("Pas de sandwich avec cette id");
        }
        return s;
    }
    
    /**
     * Methode permettant de mettre a jour un sandwich
     * @param s sandwich mis a jour
     * @param id id du sandwich
     * @return sandwich modifie
     * @throws SandwichBadRequest
     * @throws SandwichNotFoundExeception
     */
    public Sandwich update(SandwichBindIngredientsAndBread s, String id) throws SandwichBadRequest , SandwichNotFoundExeception {
        Sandwich ref = this.em.find(Sandwich.class, id);
        if (ref == null) throw (new SandwichNotFoundExeception("Pas de sandwich avec cette id"));
        
        
        int tailleSandwich = s.getTaille();
        if (tailleSandwich >= 4 && tailleSandwich <= 7) {
            ref.setTaille(tailleSandwich);
        } else {
            throw new SandwichBadRequest("La taille doit etre comprise entre  4 et 7");
        }
        
        
        //get bread by id and add to sandwich
        Bread b = this.em.find(Bread.class, s.getIdBread());
        if (b != null) {
            ref.setBread(b);
        } else {
            throw new SandwichBadRequest("ID pain inexistant");
        }
        //get all id in list of id ingredients and add ingredients to sandwich
        List<Ingredient> listIng = new ArrayList<Ingredient>();
        
        for (int i = 0; i < s.getIdIngredients().size(); i++) {
            String val = s.getIdIngredients().get(i);
            if (val != null) {
                listIng.add(this.em.find(Ingredient.class, val));
            } else {
                throw new SandwichBadRequest("Un id d'ingredients n'existe pas");
            }
        }
        
        //Category ref = this.em.getReference(Category.class, id);
        ref.setIngredients(listIng);
        
        return this.em.merge(ref);
    }
    
    /**
     * Methode permettant de supprimer un sandwich
     * @param id id du sandwich a supprimer
     * @throws SandwichNotFoundExeception
     */
    public void delete(String id) throws SandwichNotFoundExeception {
        Sandwich ref = this.em.find(Sandwich.class, id);
        if(ref == null){
            throw  new SandwichNotFoundExeception("id de sandwich non existant");
        }
        this.em.remove(ref);
    }
}
