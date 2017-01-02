package boundary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.OrderSandwich;
import entity.Sandwich;

@Stateless // gestion transactionelle (plusieurs users en même temps)
public class OrderRessource {

	@PersistenceContext
	EntityManager em;
	
	
	public OrderSandwich save(OrderSandwich o){
		OrderSandwich order = new OrderSandwich();
		order.setId(UUID.randomUUID().toString());
		
		return order;
	}
}
