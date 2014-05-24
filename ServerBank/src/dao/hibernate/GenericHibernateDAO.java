package dao.hibernate;

import dao.GenericDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class GenericHibernateDAO<T extends Serializable> implements GenericDAO<T> {

    protected SessionFactory sessionFactory;
    protected Session session;
    private Class<T> persistentClass;

//    public void init(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public GenericHibernateDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        try {
            persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            //can be raised when DAO is inherited twice
            persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    @Override
    public T findById(long id) {
        T entity = (T) getCurrentSession().get(persistentClass, id);
        return entity;
    }

    @Override
    public List<T> findAll() {
        Criteria criteria = getCurrentSession().createCriteria(persistentClass);
       // criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public T makePersistent(T entity) {
        Transaction tr = session.beginTransaction();
        getCurrentSession().saveOrUpdate(entity);
        tr.commit();
        return entity;
       
    }
    
    public long deleteById(long id){
     T entity = findById(id);
     Transaction tr = session.beginTransaction();
     getCurrentSession().delete(entity);
     tr.commit();
    // HibernateUtil.shutdown();
	return id;
     }

    public long updateByid(long id){
     T entity = findById(id);
     Transaction tr = session.beginTransaction();
      getCurrentSession().update(entity);
       tr.commit();
      getCurrentSession().flush();
      
     return id;
    	}




    protected Session getCurrentSession() {
//        return this.sessionFactory.getCurrentSession();
        return this.session;
    }

}