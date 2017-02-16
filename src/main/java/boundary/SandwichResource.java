package boundary;

import entity.Ingredient;
import entity.Sandwich;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Ressource d'un sandwich
 */
@Stateless
public class SandwichResource {

    @PersistenceContext
    EntityManager em;

    public Sandwich create(Sandwich s) throws Exception {

        int tailleSandwich = s.getTaille();

        if (s.getIngredients().size() != tailleSandwich) {
            throw new Exception("Nombre d'ingrédients incorrect");
        }

        List<Ingredient> listIngredient = s.getIngredients();

        for (Ingredient contains : listIngredient) {
            int nbLimiteCateg = contains.getCategory().getLimiteNbIngredient();

            if(listIngredient.size() == nbLimiteCateg ){
                throw new Exception("Limite d'ingredients atteint pour la categorie "+contains.getCategory().getName());
            }

        }
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
        return this.em.merge(s);
    }
}
