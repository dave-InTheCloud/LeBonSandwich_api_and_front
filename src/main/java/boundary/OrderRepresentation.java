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

import entity.OrderSandwich;

/**
 * Representation d'une ressource Order
 */
@Path("/order")
@Stateless
public class OrderRepresentation {
    /**
     * Ressource Order
     */
    @EJB
    private OrderRessource orderRessource;
    
    /**
     * Methode permettant d'ajouter une commande
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(@Context UriInfo uriInfo) {
        OrderSandwich o = this.orderRessource.save();
        URI uri = uriInfo.getAbsolutePathBuilder().path(o.getId()).build();
        return Response.created(uri)
                .entity(o)
                .build();
    }
    
    /**
     * Methode permettant de recuperer la liste des commandes
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP contentn la liste des commandes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@Context UriInfo uriInfo){
        List<OrderSandwich> l  = this.orderRessource.findAll();
        
        GenericEntity<List<OrderSandwich>> list = new GenericEntity<List<OrderSandwich>>(l) {};
        
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
    
    
}
