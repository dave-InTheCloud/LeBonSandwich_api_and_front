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
public class BreadTest {
    
    private Client client;
    private WebTarget target;
    private String token;
    
    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://localhost:8080/LeBonSandwich/api/breads");
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
    public void testCreate(){
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test").build();
        
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        assertThat(postReponse.getStatus(),is(201));
    }
    
    @Test
    public void testFind(){
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test").build();
        
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        
        String location = postReponse.getHeaderString("location");
        
        // find
        JsonObject getReponse = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(getReponse.getString("name").contains("test"));
    }
    
    @Test
    public void testUpdate(){
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test").build();
        
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        
        String location = postReponse.getHeaderString("location");
        
        // find
        JsonObject getReponse = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        String Id=getReponse.getString("id");
        
        //edition
        JsonObject jsonEdit = insBuilder
                .add("name","test2").build();
        
        Response editReponse = this.target.path(Id).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).put(Entity.json(jsonEdit));
        assertThat(editReponse.getStatus(),is(200));
        
        
        //verification
        JsonObject getReponseVerif = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(getReponseVerif.getString("name").contains("test2"));
    }
    
    @Test
    public void testFindAll(){
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test").build();
        
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        
        String location = postReponse.getHeaderString("location");
        
        //find all
        Response findAllReponse = this.target.request(MediaType.APPLICATION_JSON).get();
        assertThat(findAllReponse.getStatus(),is(200));
        JsonArray all = findAllReponse.readEntity(JsonArray.class);
        assertFalse(all.isEmpty());
    }
    
    @Test
       public void testDelete(){
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test").build();
        
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).post(Entity.json(jsonCreate));
        
        String location = postReponse.getHeaderString("location");
        JsonObject getReponse = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        String id=getReponse.getString("id");
           
        //delete
        Response deleteReponse = this.target.path(id).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token).delete();
        assertThat(deleteReponse.getStatus(),is(200));
        
    }
    
}
