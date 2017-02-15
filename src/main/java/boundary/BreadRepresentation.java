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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Representation des Pains
 */
@Path("/breads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class BreadRepresentation {
    /**
     * Ressource des pains
     */
    @EJB
    private BreadRessource breadResource;
    
    /**
     * Methode permettant d'ajouter un nouveau pain (methode HTTP: POST)
     * @param bread pain a ajouter
     * @param uriInfo informations sur l'URI
     * @return reponse HTTP
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBread(Bread bread, @Context UriInfo uriInfo){
        Bread b = this.breadResource.save(bread.getName(),bread.getSize());
        URI uri = uriInfo.getAbsolutePathBuilder().path(b.getId()).build();
        
        System.out.println("[POST]Enregistrement d'un nouveau Pain");
        
        return Response.created(uri).entity(b).build();
    }
    
    
       /**
     * Methode PUT permettant de modifier du pain
     * @param destination JSON avec tous les attribut du nouveau pain
     * @param id    id du pain a modifier
     * @param uriInfo
     * @return Réponse contenant le nouveau pain si la modification a bien eu lieu
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateIngredient(@PathParam("id") String id,Bread bread) {
        Bread i = this.breadResource.update(id,bread);
        
        if(i != null){
            return Response.ok(i, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        
    }
    
    /**
     * Methode permettant d'obtenir les donnees d'un pain defini par son id
     * @param id identificateur du pain a obtenir
     * @return pain correspondant a l'id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") String id){
        Bread bread = this.breadResource.findById(id);
        
        return Response.ok(bread, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Methode permettant d'obtenir la liste de tous les pains
     * @param uriInfo
     * @return  reponse HTTP comportant la liste des pains
     */
    @GET
    public Response findAll(@Context UriInfo uriInfo){
        List<Bread> l = this.breadResource.findAll();
        GenericEntity<List<Bread>> list = new GenericEntity<List<Bread>>(l) {};
        
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * Methode permettant de supprimer un pain defini par son identificateur
     * @param id identificateur du pain a supprimer
     * @return reponse HTTP
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id){
        this.breadResource.delete(id);
        
        return Response.ok().build();
    }
}