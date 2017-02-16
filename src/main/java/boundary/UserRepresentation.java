package boundary;

import control.KeyManagement;
import entity.User;
import exception.AlreadyExistException;
import exception.NoResultException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.net.URI;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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

/**
 * Classe gerant les routes de l'utilisateur
 */

@Path("/users")
@Stateless
public class UserRepresentation {
    /**
     * Attribut injecté permettant la gestion des ressources de l'utilisateur
     */
    @EJB
    UserRessource userRessource;
    
    /**
     * Attribut injecté permettant de gerer les clés d'authorisations
     */
    @Inject
    private KeyManagement keyManagement;
    
    /**
     * Methode permettant de recuperer un utilisateur via son id
     * @param idUser id de l'utilisateur a recuperer
     * @return Reponse HTTP contenant l'utilisateur demandé 
     */
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
    
    /**
     * Methode permettant de recuperer la liste des utilisateurs de l'application
     * @return réponse contenant la liste des utilisateurs de l'application
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsers(){
        try {
            List<User> users = this.userRessource.findAll();
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        } catch (NoResultException ex) {
            return Response.noContent().build();
        }
    }
    
    /**
     * Methode permettant d'ajouter un utilisateur a l'application
     * @param user Utilisateur à ajouter
     * @param uriInfo 
     * @return Reponse contenant l'utilisateur ajouté
     */
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
        } catch (AlreadyExistException e){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                .add("error",e.getMessage()).build();
            return Response.status(Response.Status.CONFLICT).entity(errorJson).build();
        } catch (Exception e){
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                .add("error",e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }
    }
    
    /**
     * Méthode permettant d'identifier un utilisateur
     * @param user utilisateur a identifier
     * @param uriInfo
     * @return Reponse contenant le token d'identification dans le header
     */
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
    
    /**
     * Methode permettant de modifier un utilisateur
     * @param user nouveaux attribut a donner a l'utilisateur
     * @param idUser id de l'utilisateur a modifier
     * @param uriInfo 
     * @return Reponse contenant l'utilisateur modifié
     */
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
    
    
    /**
     * Méthode permettant la suppression d'un utilisateur
     * @param id id de l'utilisateur a supprimer
     * @return Reponse avec un code 200 si le serveur a bien traité la requete
     */
    @DELETE
    @Secured
    @Path("/{idUser}")
    public Response deleteUser(@PathParam("idUser") String id){
        this.userRessource.delete(id);
        return Response.ok().build();
    }
    
    /**
     * Méthode permettant de generer un token pour un utilisateur
     * @param login login de l'utilisateur 
     * @param uriInfo
     * @return token generé
     */
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
    
    /**
     * Méthode permettant de calculer la date d'expiration (utilisé par le token)
     * @param localDateTime 
     * @return 
     */
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
