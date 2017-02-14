package boundary;

import entity.Sandwich;
import entity.SandwichBindIngredientsAndBread;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Representation d'une ressource Sandwich
 */
@Path("/sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class SandwichRepresentation {

    @EJB
    SandwichResource sandwichResource;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(SandwichBindIngredientsAndBread s, @Context UriInfo uriInfo) {
        try {
            Sandwich res = sandwichResource.create(s);
            URI uri = uriInfo.getAbsolutePathBuilder().path(res.getId()).build();
            return Response.created(uri).entity(res).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {

        List<Sandwich> l = this.sandwichResource.findAll();

        GenericEntity<List<Sandwich>> list = new GenericEntity<List<Sandwich>>(l) {
        };

        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {

        try {
            this.sandwichResource.delete(id);
            return Response.ok().build();

        } catch (EntityNotFoundException e) {

            return Response.noContent().build();
        }
    }


}
