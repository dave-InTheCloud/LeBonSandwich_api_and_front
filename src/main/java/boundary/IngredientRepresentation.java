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

@Path("/ingredient")
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class IngredientRepresentation {

	@EJB
	private IngredientRessource ingredientResource;
	@EJB
	private CategoryRessource categoryRessource;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addIngredient(CategoryBindIngredient c, @Context UriInfo uriInfo) {
		Ingredient i = this.ingredientResource.save(c);
		URI uri = uriInfo.getAbsolutePathBuilder().path(i.getId()).build();
		return Response.created(uri).entity(i).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		List<Ingredient> l = this.ingredientResource.findAll();

		GenericEntity<List<Ingredient>> list = new GenericEntity<List<Ingredient>>(l) {
		};

		return Response.ok(list, MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") String id) {
		Ingredient i = this.ingredientResource.findById(id);

		return Response.ok(i, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") String id) {
		this.ingredientResource.delete(id);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateIngredient(@PathParam("id") String id, CategoryBindIngredient c) {
		Ingredient i = this.ingredientResource.update(id, c);

		return Response.ok(i, MediaType.APPLICATION_JSON).build();
	}

}
