package dao.hibernate;

import dao.OrderDAO;
import entities.Client;
import entities.Order;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class OrderHibernateDAO extends GenericHibernateDAO<Order> implements OrderDAO{



	@Override
	public List<Order> findByInn(String inn) {
		Query q = getCurrentSession().createQuery("from Order where CLIENT_INN = :inn");
		q.setParameter("inn", inn);
        return q.list();
	}
}
