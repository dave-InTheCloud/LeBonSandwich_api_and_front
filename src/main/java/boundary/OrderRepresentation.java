package boundary;

import java.net.URI;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entity.OrderSandwich;


@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class OrderRepresentation {
	
	@EJB
    private OrderRessource orderResource;
	
	 @POST
	   public Response addOrder(@Context UriInfo uriInfo) {
		 OrderSandwich o = this.orderResource.save(new OrderSandwich());
	        URI uri = uriInfo.getAbsolutePathBuilder().path(o.getId()).build();
	        return Response.created(uri)
	                .entity(o)
	                .build();
	    }
	 
	
}
