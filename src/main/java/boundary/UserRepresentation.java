package boundary;

import control.KeyManagement;
import entity.User;
import exception.NoResultException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.net.URI;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import provider.Secured;

@Path("/users")
@Stateless
public class UserRepresentation {
    @EJB
            UserRessource userRessource;
    
    @Inject
    private KeyManagement keyManagement;
    
    @GET
    @Secured
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
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsers(){
        try {
            List<User> users = this.userRessource.findAll();
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        } catch (NoResultException ex) {
            System.out.println(">>>>>>>>>>>>> No content");
            return Response.noContent().build();
        }
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
            System.out.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/signin")
    @Produces("application/json")
    @Consumes("application/json")
    public Response authentifieUtilisateur(User user, @Context UriInfo uriInfo) {
        try {
            User u = this.userRessource.findByEmail(user.getEmail());
            u.authentifie(user);
            
            // On fournit un token
            String token = "Bearer "+issueToken(u.getName(), uriInfo);
            return Response.ok().header(AUTHORIZATION, token).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @PUT
    @Secured
    @Path("/{idUser}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user, @PathParam("idUser") String idUser, @Context UriInfo uriInfo){
        user.setId(idUser);
        user = this.userRessource.update(user);
        URI uri = uriInfo.getBaseUriBuilder()
                .path(UserRepresentation.class)
                .path(user.getId())
                .build();
        return Response.ok(uri).entity(user).build();
    }
    
    
    @DELETE
    @Secured
    @Path("/{idUser}")
    public Response deleteUser(@PathParam("idUser") String id){
        this.userRessource.delete(id);
        return Response.ok().build();
    }
    
    private String issueToken(String login, UriInfo uriInfo) {
        Key key = this.keyManagement.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(10L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }
    
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
