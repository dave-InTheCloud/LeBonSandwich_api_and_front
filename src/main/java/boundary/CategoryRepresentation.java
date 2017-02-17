
package boundary;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entity.Category;
import entity.Ingredient;

import javax.persistence.EntityNotFoundException;

/**
 * Representation d'une Categorie d'Ingredient
 */
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class CategoryRepresentation {

    /**
     * Ressource des categories
     */
    @EJB
    private CategoryRessource categoryResource;

    /**
     * Methode permettant d'ajouter une categorie d'ingredient
     *
     * @param categ   categorie d'ingredient a ajouter
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP
     * 
     * @api {post} /categories/ Creation d'une categorie
     * @apiName PostCategories
     * @apiGroup Categories
     * 
     * @apiParam {String} name nom de la catégorie
     * @apiparam {Number} limiteNbIngredient limite de ce type d'ingredient par commande
     * 
     * @apiSuccess (201) {Category} category   Categorie creee
     * @apiError (400) NomCategorieManquant   le nom de la categorie est manquant
     * @apiError (401) NonAutorise  token d'authentification invalide
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(Category categ, @Context UriInfo uriInfo) {
        try {
            Category c = this.categoryResource.save(categ);
            URI uri = uriInfo.getAbsolutePathBuilder().path(c.getId()).build();

            return Response.created(uri).entity(c).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Methode permettant de recuperer toutes les categories d'ingredients
     *
     * @return reponse HTTP comportant la liste des categories
     * 
     * @api {get} /categories/ Recuperation Liste des categories
     * @apiName GetCategories
     * @apiGroup Categories
     * 
     * @apiSuccess {ListCategory} categories   Liste des categories
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Category> l = this.categoryResource.findAll();
        GenericEntity<List<Category>> list = new GenericEntity<List<Category>>(l) {
        };

        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Methode permettant de recuperer une categorie d'ingredient definie par son identificateur
     *
     * @param id      identificateur de la categorie d'ingredient
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP comportant la categorie d'ingredient
     * 
     * @api {get} /categories/:id Recuperation d'une categorie
     * @apiName GetCategory
     * @apiGroup Categories
     * 
     * @apiParam {String} :id id de la catégorie
     * 
     * @apiSuccess {Category} category   Categorie recuperee
     * @apiError (204) CategorieInexistance la categorie n'existe pas
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Category c = this.categoryResource.findById(id);

        if (c != null)
            return Response.ok(c, MediaType.APPLICATION_JSON).build();
        else
            return Response.noContent().build();
    }

    /**
     * Methode permettant de supprimer une categorie d'ingredients
     *
     * @param id identificateur de la categorie a supprimer
     * @return reponse HTTP
     * 
     * @api {delete} /categories/:id Suppression d'une categorie
     * @apiName DeleteCategories
     * @apiGroup Categories 
    * 
     * @apiParam {String} :id id de la categorie
     * 
     * @apiSuccess {null} null   Categorie supprimee
     * @apiError (401) NonAutorise  token d'authentification invalide
     * @apiError (204) CategorieInexistante la categorie a supprimer n'existe pas 
     */
    @DELETE
    @Path("/{categId}")
    public Response deleteCategory(@PathParam("categId") String id) {
        try {
            this.categoryResource.delete(id);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    /**
     * Methode permettant de mettre a jour une categorie (methode HTTP: PUT)
     * @id id de la categorie a modifier
     * @param category categorie a modifier
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP
     * 
     * @api {put} /categories/:id Modification d'une categorie
     * @apiName PutCategories
     * @apiGroup Categories
     * 
     * @apiParam {String} :id id de la categorie
     * @apiParam {String} name nom de la catégorie
     * @apiparam {Number} limiteNbIngredient limite de ce type d'ingredient par commande
     * 
     * @apiSuccess {Category} category   Categorie modifiee
     * @apiSuccess (201) {Category} category Categorie creee
     * @apiError (400) NomCategorieManquant   le nom de la categorie est manquant
     * @apiError (401) NonAutorise  token d'authentification invalide
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, Category category, @Context UriInfo uriInfo){
        if(category.getName() != null) {
            category.setId(id);
            URI uri = uriInfo.getBaseUriBuilder()
                .path(CategoryRepresentation.class)
                .path(id)
                .build();
            
            if(this.categoryResource.update(id, category))
                return Response.created(uri).entity(category).build();
            else
                return Response.ok(uri).entity(category).build();
        } else return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Methode permettant de recuperer la liste des ingredients
     * @param id id de la categorie dont on souhaite recuperer les ingredients
     * @return liste des ingredients associes
     * 
     * @api {get} /categories/:id/ingredients Recuperation des ingredients associes a une categorie
     * @apiName GetIngredientsCategories
     * @apiGroup Categories
     * 
     * @apiParam {String} :id id de la catégorie
     * 
     * @apiSuccess {ListIngredients} ingredients   Liste des ingredients associes
     * @apiError (204) IngredientsInexistants   aucun ingredient n'est associe a la categorie
     */
    @GET
    @Path("/{id}/ingredients")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAssociatedIngredients(@PathParam("id") String id) {
        Category c = this.categoryResource.findById(id);
        
        if(c != null) {
            //On continue la recherche des ingredients
            List<Ingredient> ingredients = c.getIngredients();
            
            if(ingredients.size() > 0)
                return Response.ok(ingredients).build();
            else
                return Response.noContent().build();
        } else {
            return Response.noContent().build();
        }
    }
}
