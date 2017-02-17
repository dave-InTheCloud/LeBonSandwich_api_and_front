package boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.OrderSandwich;

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
    public OrderSandwich save(){
        OrderSandwich order = new OrderSandwich();
        order.setId(UUID.randomUUID().toString());
        
        return this.em.merge(order);
    }
    
    /**
     * Methode permettant de recuperer la liste des commandes
     * @return liste des commandes
     */
    public List<OrderSandwich> findAll() {
        Query q = this.em.createNamedQuery("OrderSandwich.findAll", OrderSandwich.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }
    
}
