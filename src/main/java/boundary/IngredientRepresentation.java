package boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entity.Category;
import entity.Ingredient;

@Path("/ingredient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class IngredientRepresentation {
	
	@EJB
    private IngredientRessource ingredientResource;
	
	 @POST
	 @Path("/{idcateg}/{name}")
	    public Response addIngredient(@PathParam("idcateg") String categ, @PathParam("name") String name, @Context UriInfo uriInfo) {
		 Ingredient i = this.ingredientResource.save(new Ingredient(name),categ);
	        URI uri = uriInfo.getAbsolutePathBuilder().path(i.getId()).build();
	        return Response.created(uri)
	                .entity(i)
	                .build();
	    }
	 
	 @GET
	 public Response findAll(@Context UriInfo uriInfo){
		 List<Ingredient> l  = this.ingredientResource.findAll();
		  
		 GenericEntity<List<Ingredient>> list = new GenericEntity<List<Ingredient>>(l) {};
		 
		 return Response.ok(list, MediaType.APPLICATION_JSON).build();
		 
	 }
}
