/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maxime
 */
public class IngredientTest {
    
    private Client client;
    private WebTarget target;
    private String token;
    
    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://localhost:8080/LeBonSandwich/api/categories");
    }

    @Before
    public void initToken(){
        String baseUri = "http://localhost:8080/LeBonSandwich/api/users";
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test")
                .add("email", "test@test.fr")
                .add("password", "test")
                .build();
        Response postReponse = this.client.target(baseUri)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jsonCreate));
        
        jsonCreate = insBuilder
                .add("email", "test@test.fr")
                .add("password", "test")
                .build();
        postReponse = this.client.target(baseUri+"/signin")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jsonCreate));
        this.token = postReponse.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).toString();
    }
    
    @Test
    public void testIngredient(){
         JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test5651").build();
        

      
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        assertThat(postReponse.getStatus(),is(201));
        

        String location = postReponse.getHeaderString("location");
        System.out.println("location : "+location);
        
        // find
        JsonObject getReponse = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(getReponse.getString("name").contains("test"));
        
        String Id=getReponse.getString("id");
        
        this.target = this.client.target("http://localhost:8080/LeBonSandwich/api/ingredients");
        
        
        
         jsonCreate = insBuilder
                .add("nameIng","Steak haché")
                .add("idCateg",Id).build();
         
         System.out.println(jsonCreate);
        
         //creation
        postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        assertThat(postReponse.getStatus(),is(201));
        
        location = postReponse.getHeaderString("location");
        System.out.println("location : "+location);
        
        // find
        getReponse = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        String idIngredient = getReponse.getString("id");
        assertTrue(getReponse.getString("name").contains("Steak haché"));
        
        String IdCateg =this.client.target("http://localhost:8080/LeBonSandwich/api/categories").request(MediaType.APPLICATION_JSON).get().readEntity(JsonArray.class).getJsonObject(0).getString("id");
        
          //edition
        JsonObject jsonEdit = insBuilder
                .add("nameIng","Steak haaaaaaaché")
                .add("idCateg",IdCateg).build();
        
        Response editReponse = this.target.path(idIngredient).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).put(Entity.json(jsonEdit));
        assertThat(editReponse.getStatus(),is(200));
        
        //verification
        JsonObject getReponseVerif = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(getReponseVerif.getString("name").contains("Steak haaaaaaaché"));
        
        
         //find all
        Response findAllReponse = this.target.request(MediaType.APPLICATION_JSON).get();
        assertThat(findAllReponse.getStatus(),is(200));
        JsonArray all = findAllReponse.readEntity(JsonArray.class);
        assertFalse(all.isEmpty());
        
        
        //delete
        Response deleteReponse = this.target.path(idIngredient).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).delete();
        assertThat(deleteReponse.getStatus(),is(200));
        
   
        
        
        
        
    }
   
}
