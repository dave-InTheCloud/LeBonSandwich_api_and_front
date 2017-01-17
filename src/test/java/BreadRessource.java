/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maxime
 */
public class BreadRessource {
    
    private Client client;
    private WebTarget target;
    
    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://localhost:8080/LeBonSandwich/api/bread/");
    }

    @Test
    public void testBread(){
        JsonObjectBuilder insBuilder = Json.createObjectBuilder();
        JsonObject jsonCreate = insBuilder
                .add("name","test")
                .add("size","2").build();
        

        
        //creation
        Response postReponse = this.target.request(MediaType.APPLICATION_JSON).post(Entity.json(jsonCreate));
        
        

        assertThat(postReponse.getStatus(),is(201));
        
      
        
        String location = postReponse.getHeaderString("location");
        System.out.println("location : "+location);
        
        // find
        JsonObject getReponse = this.client.target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(getReponse.getString("name").contains("test"));
        
        //find all
        Response findAllReponse = this.target.request(MediaType.APPLICATION_JSON).get();
        assertThat(findAllReponse.getStatus(),is(200));
        JsonArray all = findAllReponse.readEntity(JsonArray.class);
        assertFalse(all.isEmpty());
        
        //delete
         Response deleteReponse = this.target.path("1").request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteReponse.getStatus(),is(204));
        
    }
   
}
