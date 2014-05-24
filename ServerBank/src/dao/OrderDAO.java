package dao;

import java.util.List;


import entities.Client;
import entities.Order;


public interface OrderDAO extends GenericDAO<Order>{
	
	 List<Order>findByInn(String inn);
	
}
