
package boundary;

import java.net.URI;
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
import provider.Secured;

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
     */
    @POST
    @Secured
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
     */
    @DELETE
    @Secured
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
     */
    @PUT
    @Secured
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

}
