package dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


import dao.ManagerDAO;
import entities.Manager;

public class ManagerHibernateDAO extends GenericHibernateDAO<Manager> implements ManagerDAO{

	
	 public static final String MANAGER_LOGIN_FIELD = "login";
	 
	  @Override
	 public Manager findByLogin(String login) {
	        Criteria criteria = getCurrentSession().createCriteria(Manager.class);
	        criteria.add(Restrictions.eq(MANAGER_LOGIN_FIELD,login));
	        return (Manager)criteria.uniqueResult();
	        
	    }
}
