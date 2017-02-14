package boundary;

import entity.Bread;
import entity.Ingredient;
import entity.Sandwich;
import entity.SandwichBindIngredientsAndBread;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Ressource d'un sandwich
 */
@Stateless
public class SandwichResource {

    @PersistenceContext
    EntityManager em;

    public Sandwich create(SandwichBindIngredientsAndBread s) throws Exception {
        Sandwich res = new Sandwich();
        res.setId(UUID.randomUUID().toString());
        int tailleSandwich = s.getTaille();
        res.setTaille(tailleSandwich);


        /*
        if (s.getIdIngredients().size() != tailleSandwich) {
            throw new Exception("Nombre d'ingrédients incorrect");
        }
        */

        //get bread by id and add to sandwich
        Bread b = this.em.find(Bread.class, s.getIdBread());
        res.setBread(b);

        //get all id in list of id ingredients and add ingredients to sandwich
        List<Ingredient> listIng = new ArrayList<Ingredient>();

        for (String idIng : s.getIdIngredients()) {
            System.out.println(idIng);
            if (idIng != null) {
                listIng.add(this.em.find(Ingredient.class, idIng));
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

    public List<Sandwich> findAll() {
        Query q = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public void delete(String id)  throws EntityNotFoundException {
        Sandwich ref = this.em.getReference(Sandwich.class, id);
        this.em.remove(ref);
    }
}
