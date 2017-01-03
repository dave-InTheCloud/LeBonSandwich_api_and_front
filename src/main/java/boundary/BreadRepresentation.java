/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entity.Bread;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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


@Path("/bread")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class BreadRepresentation {
    
    
    @EJB
    private BreadRessource breadResource; 
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBread(Bread bread, @Context UriInfo uriInfo){
        Bread b = this.breadResource.save(bread.getName(),bread.getSize());
        URI uri = uriInfo.getAbsolutePathBuilder().path(b.getId()).build();
	return Response.created(uri).entity(b).build();
    }
    

    @GET
    public Response findAll(@Context UriInfo uriInfo){
        List<Bread> l = this.breadResource.findAll();
        GenericEntity<List<Bread>> list = new GenericEntity<List<Bread>>(l) {};
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
       
}