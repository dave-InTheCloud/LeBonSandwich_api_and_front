package boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import entity.OrderSandwich;
import entity.Sandwich;
import exception.OrderBadRequest;
import exception.OrderPayed;

/**
 * Representation d'une ressource OrderSandwich
 */
@Path("/orders")
@Stateless
public class OrderRepresentation {
    /**
     * Ressource OrderSandwich
     */
    @EJB
    private OrderRessource orderRessource;

    /**
     * Methode permettant d'ajouter une commande
     *
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(OrderSandwich o, @Context UriInfo uriInfo) {
        try {
            OrderSandwich res = this.orderRessource.save(o);
            URI uri = uriInfo.getAbsolutePathBuilder().path(res.getId()).build();
            return Response.created(uri)
                    .entity(res)
                    .build();
        } catch (OrderBadRequest e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Methode permettant de recuperer la liste des commandes
     *
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP contentn la liste des commandes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@Context UriInfo uriInfo) {
        try {
            List<OrderSandwich> l = this.orderRessource.findAll();

            GenericEntity<List<OrderSandwich>> list = new GenericEntity<List<OrderSandwich>>(l) {
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
    @Path("{id}")
    public Response findById(@PathParam("id") String id) {

        try {
            OrderSandwich s = this.orderRessource.findById(id);
            return Response.ok(s, MediaType.APPLICATION_JSON).build();
        } catch (NoContentException e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }

    }


    @DELETE
    @Path("{id}")
    public  Response finById(@PathParam("id") String id){
        try {
            this.orderRessource.delete(id);
            return Response.ok().build();
        } catch (NoContentException e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }

    }




    @POST
    @Path("{id}/pay")
    public Response payed(@PathParam("id") String id){
        try{
            this.orderRessource.pay(id);
            return Response.ok("satus : "+OrderSandwich.PAYER +"(Payé)", MediaType.TEXT_PLAIN).build();
        }catch (NoContentException e){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }catch (OrderPayed e){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.ok().entity(errorJson).build();
        }
    }


    @Path("{idOrder}/addSandwich")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSandwich(@QueryParam("idSandwich") String idSandwich, @PathParam("idOrder") String idOrder){
        try{
            OrderSandwich o = this.orderRessource.addSandwich(idSandwich, idOrder);
            return Response.ok(o, MediaType.APPLICATION_JSON).build();
        }catch (OrderBadRequest e ){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }
    }

    @Path("{idOrder}/editSandwich")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editSandwich(@QueryParam("idSandwich") String idSandwich, @PathParam("idOrder") String idOrder, Sandwich s){
        try{
            OrderSandwich o = this.orderRessource.editSandwich(idSandwich, idOrder, s);
            return Response.ok(o, MediaType.APPLICATION_JSON).build();
        }catch (OrderBadRequest e ){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }
    }

}
