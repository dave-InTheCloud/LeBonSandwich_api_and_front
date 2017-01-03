package boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.OrderSandwich;
import entity.Sandwich;

@Stateless // gestion transactionelle (plusieurs users en m�me temps)
public class OrderRessource {

	@PersistenceContext
	EntityManager em;
	
	
	public OrderSandwich save(OrderSandwich o){
		OrderSandwich order = new OrderSandwich();
		order.setId(UUID.randomUUID().toString());
		
		return this.em.merge(order);
	}
	
	public List<OrderSandwich> findAll() {
		Query q = this.em.createNamedQuery("OrderSandwich.findAll", OrderSandwich.class);
		// pour éviter les pbs de cache
		q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
		return q.getResultList();
	}
	
}
