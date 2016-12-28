package boundary;

import java.net.URI;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entity.Commentaire;

@Path("/sandwich")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class IngredientRepresentation {
	

	
	 @POST
	    public Response addCommentaire(Commentaire commentaire, @QueryParam("messageId") String messageId, @Context UriInfo uriInfo) {
	        Commentaire c = this.commentResource
	                .ajouteCommentaire(messageId, new Commentaire(commentaire.getTexte(), commentaire.getAuteur()));
	        URI uri = uriInfo.getBaseUriBuilder()
	                .path(MessageRepresentation.class)
	                .path(messageId)
	                .path(CommentaireRepresentation.class)
	                .path(c.getId())
	                .build();
	        return Response.created(uri).entity(c).build();
	    }
}
