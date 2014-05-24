package dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import dao.UserDAO;


import entities.User;


public class UserHibernateDAO extends GenericHibernateDAO<User> implements UserDAO{
	  
	 public static final String USER_LOGIN_FIELD = "login";
	public static final String USER_PASSWORD_FIELD = "password";
	 
	  
	 @Override
	    public User findByPassLog(String login,String password) {
	        Criteria criteria = getCurrentSession().createCriteria(User.class);
	        criteria.add(Restrictions.eq(USER_LOGIN_FIELD,login));
	        criteria.add(Restrictions.eq(USER_PASSWORD_FIELD,password));
	        return (User)criteria.uniqueResult();
	    }
	
}
