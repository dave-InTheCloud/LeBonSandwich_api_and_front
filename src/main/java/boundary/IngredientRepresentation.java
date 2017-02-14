package boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entity.CategoryBindIngredient;
import entity.Ingredient;

/**
 * Representation d'un Ingredient
 */
@Path("/ingredients")
@Stateless
public class IngredientRepresentation {
    /**
     * Ressource d'un Ingredient
     */
    @EJB
    private IngredientRessource ingredientResource;
    
    /**
     * Ressource d'une Categorie
     */
    @EJB
    private CategoryRessource categoryRessource;
    
    /**
     * Methode permettant d'ajouter un ingredient
     * @param ingredient ingredient a ajouter (binde avec une categorie)
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addIngredient(CategoryBindIngredient c, @Context UriInfo uriInfo) {
        try {
            Ingredient i = this.ingredientResource.save(c);
            URI uri = uriInfo.getAbsolutePathBuilder().path(i.getId()).build();
            return Response.created(uri).entity(i).build();
        } catch(Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    /**
     * Methode permettant de recuperer la liste de tous les Ingredients
     * @return reponse HTTP
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Ingredient> l = this.ingredientResource.findAll();
        
        GenericEntity<List<Ingredient>> list = new GenericEntity<List<Ingredient>>(l) {
        };
        
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
        
    }
    
    /**
     * Methode permettant de recuperer un ingredient a partir de son identificateur
     * @param id identificateur d'un ingredient
     * @return reponse HTTP comportant l'ingredient
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        Ingredient i = this.ingredientResource.findById(id);
        
        if(i == null)
            return Response.noContent().build();
            
        return Response.ok(i, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Methode permettant de supprimer un ingredient a partir de son identificateur
     * @param id identificateur d'un ingredient
     * @return reponse HTTP
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            this.ingredientResource.delete(id);
        
            return Response.ok().build();
        } catch(Exception e) {
            return Response.noContent().build();
        }
    }
    
    /**
     * Methode permettant de mettre a jour un ingredient
     * @param id identifiant de l'ingredient
     * @param c ingredient binde avec la categorie
     * @return reponse HTTP
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIngredient(@PathParam("id") String id, CategoryBindIngredient c) {
        Ingredient i = this.ingredientResource.update(id, c);
        
        return Response.ok(i, MediaType.APPLICATION_JSON).build();
    }
    
}
