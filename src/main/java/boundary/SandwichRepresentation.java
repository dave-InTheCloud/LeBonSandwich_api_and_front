package boundary;

import entity.Sandwich;
import entity.SandwichBindIngredientsAndBread;
import exception.SandwichBadRequest;
import exception.SandwichNotFoundExeception;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
    public Response create(SandwichBindIngredientsAndBread s, @Context UriInfo uriInfo){
        try {
            if (s.getIdIngredients() != null && s.getIdBread() != null){
                Sandwich res = sandwichResource.create(s);
                URI uri = uriInfo.getAbsolutePathBuilder().path(res.getId()).build();
                return Response.created(uri).entity(res).build();
            }else{
                throw new BadRequestException("test");
            }

        } catch (SandwichBadRequest e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(SandwichBindIngredientsAndBread s, @PathParam("id") String id)   {
        try {
            Sandwich res = this.sandwichResource.update(s, id);

            return Response.ok(res, MediaType.APPLICATION_JSON).build();
        } catch (SandwichNotFoundExeception e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder.add("error", e.getMessage()).build();
            return Response.noContent().build();
        } catch (SandwichBadRequest e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<Sandwich> l = this.sandwichResource.findAll();

            GenericEntity<List<Sandwich>> list = new GenericEntity<List<Sandwich>>(l) {
            };

            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }catch (NoContentException e){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response findByID(@PathParam("id") String id) {
        try {
            Sandwich s = this.sandwichResource.findById(id);
            return Response.ok(s, MediaType.APPLICATION_JSON).build();
        } catch (SandwichNotFoundExeception e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {

        try {
            this.sandwichResource.delete(id);
            return Response.ok().build();
        } catch (SandwichNotFoundExeception e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }
    }

}
