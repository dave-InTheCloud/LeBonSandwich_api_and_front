package boundary;

import entity.Sandwich;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Representation d'une ressource Sandwich
 */
@Path("/sandwiches")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class SandwichRepresentation {

    @EJB
    SandwichResource sandwichResource;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Sandwich s, @Context UriInfo uriInfo) {
        try {
            sandwichResource.create(s);
            URI uri = uriInfo.getAbsolutePathBuilder().path(s.getId()).build();
            return Response.created(uri).entity(s).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


}
