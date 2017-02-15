package boundary;

import entity.User;
import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/users")
@Stateless
public class UserRepresentation {
    @EJB 
    UserRessource userRessource;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idUser}")
    public Response findUser(@PathParam("idUser") String idUser){
        User u = this.userRessource.findById(idUser);
        if(u != null)
            return Response.ok(u).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsers(){
        List<User> users = this.userRessource.findAll();
        return Response.ok(users, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user, @Context UriInfo uriInfo){
        try {
            User u = this.userRessource
                    .save(user);
            URI uri = uriInfo.getBaseUriBuilder()
                    .path(UserRepresentation.class)
                    .path(u.getId())
                    .build();
            
            return Response.created(uri).entity(u).build();
        }  catch(Exception e) {
            //Si jamais il manque des arguments dans la requete
            return Response.status(Response.Status.BAD_REQUEST).build();
        } 
    }
}
