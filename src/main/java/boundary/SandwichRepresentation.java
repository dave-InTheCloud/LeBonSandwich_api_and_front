package boundary;

import entity.Bread;
import entity.Ingredient;
import entity.Sandwich;
import entity.SandwichBindIngredientsAndBread;
import exception.SandwichBadRequest;
import exception.SandwichNotFoundExeception;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import provider.Secured;

/**
 * Representation d'une ressource Sandwich
 */
@Path("/sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class SandwichRepresentation {
    
    @EJB
            SandwichResource sandwichResource;
    
    /**
     * Methode permettant de creer un sandwich
     * @param s sandwich a creer
     * @param uriInfo contexte sur l'uri
     * @return reponse HTTP avec le sandwich
     *
     * @api {post} /sandwichs/ Creation d'un sandwich
     * @apiName PostSandwichs
     * @apiGroup Sandwichs
     *
     * @apiParam {String} idBread id du pain du sandwich
     * @apiParam {Number} taille taille du sandwich
     * @apiParam {ListIdIngredients} idIngredients liste des id des ingredients qui composent le sandwich
     *
     * @apiSuccess (201) {Sandwich} sandwich   Sandwich cree
     * @apiError (400) idBreadManquant  id du pain manquant
     * @apiError (400) tailleManquant   la taille du sandwich est manquante
     * @apiError (400) idIngredientsManquant la liste des ingredients est manquante
     * @apiError (400) nombreIngredientsIncorrect le nombre d'ingredients est incorrect vis a vis de la taille du sandwich
     */
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(SandwichBindIngredientsAndBread s, @Context UriInfo uriInfo){
        try {
            if (s.getIdIngredients() != null && s.getIdBread() != null){
                Sandwich res = sandwichResource.create(s);
                URI uri = uriInfo.getAbsolutePathBuilder().path(res.getId()).build();
                return Response.created(uri).entity(res).build();
            }else{
                throw new BadRequestException("test");
            }
            
        } catch (SandwichBadRequest e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }
        
    }
    
    /**
     * Methode permettant de mettre a jour un sandwich
     * @param s sandwich mis a jour
     * @param id id du sandwich a modifier
     * @return reponse HTTP - sandwich modifie
     *
     * @api {put} /sandwichs/:id Modification d'un sandwich
     * @apiName PutSandwichs
     * @apiGroup Sandwichs
     *
     * @apiparam {String} :id id du sandwich a modifier
     * @apiParam {String} idBread id du pain du sandwich
     * @apiParam {Number} taille taille du sandwich
     * @apiParam {ListIdIngredients} idIngredients liste des id des ingredients qui composent le sandwich
     *
     * @apiSuccess (200) {Sandwich} sandwich   Sandwich cree
     * @apiError (400) idBreadManquant  id du pain manquant
     * @apiError (400) tailleManquant   la taille du sandwich est manquante
     * @apiError (400) idIngredientsManquant la liste des ingredients est manquante
     * @apiError (400) nombreIngredientsIncorrect le nombre d'ingredients est incorrect vis a vis de la taille du sandwich
     * @apiError (204) sandwichInexistant le sandwich n'existe pas
     */
    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(SandwichBindIngredientsAndBread s, @PathParam("id") String id)   {
        try {
            Sandwich res = this.sandwichResource.update(s, id);
            
            return Response.ok(res, MediaType.APPLICATION_JSON).build();
        } catch (SandwichNotFoundExeception e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder.add("error", e.getMessage()).build();
            
            return Response.noContent().build();
        } catch (SandwichBadRequest e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(errorJson).build();
        }
    }
    
    /**
     * Methode permettant de recuperer la liste des sandwichs
     * @return reponse HTTP - liste des sandwichs
     *
     * @api {get} /sandwichs/ Recuperation liste des sandwichs
     * @apiName GetListSandwichs
     * @apiGroup Sandwichs
     *
     * @apiSuccess (200) {ListSandwich} listSandwich   Liste des sandwichs
     * @apiError (204) SandwichsManquants  aucun sandwich enregistre
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<Sandwich> l = this.sandwichResource.findAll();
            
            GenericEntity<List<Sandwich>> list = new GenericEntity<List<Sandwich>>(l) {
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
     * Methode permettant de recuperer un sandwich
     * @param id id du sandwich a recuperer
     * @return reponse HTTP - sandwich recupere
     *
     * @api {get} /sandwichs/:id Recuperation d'un sandwich
     * @apiName GetSandwichs
     * @apiGroup Sandwichs
     *
     * @apiParam {String} :id id du sandwich
     *
     * @apiSuccess (200) {Sandwich} sandwich   Sandwich recupere
     * @apiError (204) sandwichInexistant  le sandwich n'existe pas
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response findByID(@PathParam("id") String id) {
        try {
            Sandwich s = this.sandwichResource.findById(id);
            return Response.ok(s, MediaType.APPLICATION_JSON).build();
        } catch (SandwichNotFoundExeception e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }
    }
    
    /**
     * Methode permettant de supprimer un sandwich
     * @param id id du sandwich a supprimer
     * @return reponse HTTP
     *
     * @api {delete} /sandwichs/:id Suppresion d'un sandwich
     * @apiName DeleteSandwichs
     * @apiGroup Sandwichs
     *
     * @apiParam {String} :id id du sandwich a supprimer
     *
     * @apiSuccess (200) {null} null   Sandwich supprime
     * @apiError (204) sandwichInexistant  le sandwich n'existe pas
     */
    @DELETE
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        
        try {
            this.sandwichResource.delete(id);
            return Response.ok().build();
        } catch (SandwichNotFoundExeception e) {
            JsonObjectBuilder insBuilder = Json.createObjectBuilder();
            JsonObject errorJson = insBuilder
                    .add("error", e.getMessage()).build();
            return Response.noContent().entity(errorJson).build();
        }
    }
    
    /**
     * Methode permettant de recuperer le pain associe au sandwich
     * @param id id du sandwich
     * @return pain associe
     *
     * @api {get} /sandwichs/:id/breads Recuperation pain associe au sandwich
     * @apiName GetBreadSandwichs
     * @apiGroup Sandwichs
     *
     * @apiParam {String} :id id du sandwich
     *
     * @apiSuccess (200) {Bread} bread   Pain du sandwich
     * @apiError (204) SandwichInexistant  Le sandwich n'existe pas
     */
    @GET
    @Path("/{id}/breads")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAssociatedBread(@PathParam("id") String id) {
        try {
            Sandwich s = this.sandwichResource.findById(id);
            
            //On continue la recherche de la categorie
            Bread bread = s.getBread();
            
            if(bread != null)
                return Response.ok(bread).build();
            else
                return Response.noContent().build();
        } catch (SandwichNotFoundExeception ex) {
            return Response.noContent().build();
        }
    }
    
    /**
     * Methode permettant de recuperer la liste des ingredients associes au sandwich
     * @param id id du sandwich
     * @return liste des ingredients associes
     *
     * @api {get} /sandwichs/:id/ingredients Recuperation liste des ingredients associes au sandwich
     * @apiName GetIngredientsSandwich
     * @apiGroup Sandwichs
     *
     * @apiParam {String} :id id du sandwich
     *
     * @apiSuccess (200) {ListIngredients} listIngredients   Liste des ingredients qui composent le sandwich
     * @apiError (204) SandwichInexistant  le sandwich n'existe pas
     */
    @GET
    @Path("/{id}/ingredients")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAssociatedIngredients(@PathParam("id") String id) {
        try {
            Sandwich s = this.sandwichResource.findById(id);
            
            //On continue la recherche de la categorie
            List<Ingredient> ingredients = s.getIngredients();
            
            if(ingredients.size() > 0)
                return Response.ok(ingredients).build();
            else
                return Response.noContent().build();
        } catch (SandwichNotFoundExeception ex) {
            return Response.noContent().build();
        }
    }
}
