package boundary;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.NoContentException;

import entity.OrderSandwich;
import entity.OrderBindSandwich;
import entity.Sandwich;
import exception.OrderBadRequest;

/**
 * Ressource d'une commande
 */
@Stateless
public class OrderRessource {
    /**
     * EntityManager
     */
    @PersistenceContext
    EntityManager em;
    
    /**
     * Methode permettant d'enregistrer une commande
     * @return commande enregistree 
     */
    public OrderSandwich save(OrderBindSandwich o) throws  OrderBadRequest{

        Set<Sandwich> listSandwich = new HashSet<Sandwich>();
        List<String> idSandwichs = o.getIdSandwichs();

        for (int i=0; i< idSandwichs.size(); i++){
            if(idSandwichs.get(i) != null){
                Sandwich val = this.em.find(Sandwich.class,idSandwichs.get(i));
                listSandwich.add(val);
            }else{
                throw new OrderBadRequest("Un id de sandwich introuvable");
            }
        }

        String dateEnvoie;
        if(o.getDateEnvoie() != null){
           dateEnvoie = o.getDateEnvoie();
        }else{
            throw  new OrderBadRequest("Date invalide");
        }


        if (listSandwich.size()> 0) {
            OrderSandwich orderSandwich = new OrderSandwich();
            orderSandwich.setSandwichs(listSandwich);
            orderSandwich.setDateEnvoie(dateEnvoie);
            orderSandwich.setId(UUID.randomUUID().toString());
            return this.em.merge(orderSandwich);
        }else{
            throw new OrderBadRequest("List sandwich invalide");
        }

    }
    
    /**
     * Methode permettant de recuperer la liste des commandes
     * @return liste des commandes
     */
    public List<OrderSandwich> findAll() throws NoContentException {
        Query q = this.em.createNamedQuery("Order.findAll", OrderSandwich.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        List<OrderSandwich> res =  q.getResultList();
        if(res.size() != 0){
            return res;
        }else{
            throw (new NoContentException("Pas de commandes pour le moment"));
        }
    }


    public OrderSandwich findById(String id) throws NoContentException{
        OrderSandwich res = this.em.find(OrderSandwich.class, id);
        if (res != null){
            return res;
        }else {
            throw (new NoContentException("Pas de commandes pour le moment"));
        }
    }

    public void delete(String id) throws  NoContentException {
        OrderSandwich res = this.em.find(OrderSandwich.class, id);
        if(res == null){
            throw  new NoContentException("id de sandwich non existant");
        }
        this.em.remove(res);
    }
}
