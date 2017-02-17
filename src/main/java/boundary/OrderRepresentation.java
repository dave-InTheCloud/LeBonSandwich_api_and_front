package boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import entity.OrderSandwich;
import exception.OrderBadRequest;
import exception.OrderPayed;
import provider.Secured;

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
     * 
     * @api {post} /orders Creation d'une nouvelle commande
     * @apiName PostOrder
     * @apiGroup Orders
     *
     * @apiParam {String} dateEnvoie  date d'expedition de la commande
     *
     * @apiSuccess (201) {Order} order   Commande creee
     * @apiError (400)  DateEnvoieManquant   la date d'envoi est manquante
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
     * @return reponse HTTP contenant la liste des commandes
     * 
     * @api {get} /orders Recuperation liste de commandes
     * @apiName GetOrdersList
     * @apiGroup Orders
     *
     * @apiSuccess (200) {OrdersList} ordersList   Liste des commandes
     * @apiError (204)  CommandeInexistant   aucune commande existante
     * @apiError (401)  NonAutorise token d'authentification invalide
     */
    @GET
    @Secured
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

    /**
     * Methode permettant de recuperer une commande
     * @param id id de la commmande
     * @return reponse HTTP - commande recuperee
     * 
     * @api {get} /orders/:id Recuperation d'une commande
     * @apiName GetOrders
     * @apiGroup Orders
     * 
     * @apiParam {String} :id id de la commande
     *
     * @apiSuccess (200) {Orders} orders   Commande recuperee
     * @apiError (204)  CommandeInexistant   la commande n'existe pas
     */
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

    /**
     * Methode permettant de supprimer une commande
     * @param id id de la commande a supprimer
     * @return reponse HTTP
     * 
     * @api {delete} /orders/:id Suppression de commande
     * @apiName DeleteOrders
     * @apiGroup Orders
     * 
     * @apiParam {String} :id id de la commande
     *
     * @apiSuccess (200) {null} null   Commande supprimee
     * @apiError (204)  CommandeInexistant   la commande n'existe pas
     * @apiError (401)  NonAutorise token d'authentification invalide
     */
    @DELETE
    @Secured
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

    /**
     * Methode permettant de payer la commande
     * @param id id de la commande a payer
     * @return  reponse HTTP
     * 
     * @api {post} /orders/:id/pay Payer une commande
     * @apiName PayOrders
     * @apiGroup Orders
     * 
     * @apiParam {String} :id id de la commande
     *
     * @apiSuccess (200) {null} null   La commande a ete payee
     * @apiSuccess (200) {null} null   La commande a deja ete payee
     * @apiError (204)  CommandeInexistant   aucune commande existante
     */
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

    /**
     * Methode permettant d'ajouter un sandwich a la commande
     * @param idSandwich id du sandwich ajoute a la commande
     * @param idOrder id de la commande
     * @return reponse HTTP
     * 
     * @api {post} /orders/:id/addSandwich?idSandwich Ajouter sandwich a la commande
     * @apiName AddSandwichOrder
     * @apiGroup Orders
     * 
     * @apiParam {String} :id id de la commande
     * @apiParam {String} idSandwich id du sandwich
     *
     * @apiSuccess (200) {Order} Order  Le sandwich a ete ajoute a la commande
     * @apiError (400)  CommandeInexistant   aucune commande existante
     */
    @Path("{idOrder}/sandwichs")
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

}
