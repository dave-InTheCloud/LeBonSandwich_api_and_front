
package boundary;

import java.net.URI;
import java.util.ArrayList;
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

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class CategoryRepresentation {
	
	@EJB
    private CategoryRessource categoryResource;
	
	 @POST
	 @Path("/{name}")
	    public Response addIngredient(@PathParam("name") String name, @Context UriInfo uriInfo) {
		 Category c = this.categoryResource.save(new Category(name, new ArrayList<>()));
	        URI uri = uriInfo.getAbsolutePathBuilder().path(c.getId()).build();
	        return Response.created(uri)
	                .entity(c)
	                .build();
	    }
	  
	
	 @GET
	 public Response findAll(@Context UriInfo uriInfo){
		 List<Category> l  = this.categoryResource.findAll();
		  
		 GenericEntity<List<Category>> list = new GenericEntity<List<Category>>(l) {};
		 
		 return Response.ok(list, MediaType.APPLICATION_JSON).build();
		 
	 }
}
