package boundary;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entity.Sandwich;

@Path("/sandwichs")
@Stateless
public class SandwichRepresentation {

	@EJB
	SandwichResource sandwichRessources;

	@GET
	public String findAll() {
		return "hello world";

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Create(Sandwich s, @Context UriInfo uriInfo) {
		Sandwich sandwich = this.sandwichRessources.save(s);
		URI uri = uriInfo.getAbsolutePathBuilder().path(s.getId()).build();
		return Response.created(uri).entity(s).build();

	}
}
