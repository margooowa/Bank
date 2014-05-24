package dao.hibernate;


import dao.ClientDAO;
import entities.Client;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientHibernateDAO extends GenericHibernateDAO<Client> implements ClientDAO{

    public static final String CLIENT_FIO_FIELD = "fio";
    public static final String CLIENT_INN_FIELD = "inn";
    public static final String CLIENT_PHONE_FIELD = "phone";



    @Override
    public Client findByInn(long inn) {
        Criteria criteria = getCurrentSession().createCriteria(Client.class);
        criteria.add(Restrictions.eq(CLIENT_INN_FIELD,inn));
        return (Client)criteria.uniqueResult();
        
    }

	@Override
	public long updateByInn(long inn) {
		Client entity = findById(inn);
	     Transaction tr = session.beginTransaction();
	     getCurrentSession().merge(entity);
	     tr.commit();
		// TODO Auto-generated method stub
		return inn;
		
	}
	
	@Override
	public long deleteByInn(long inn) {
		Client entity = findById(inn);
	     Transaction tr = session.beginTransaction();
	     getCurrentSession().delete(entity);
	     tr.commit();
		// TODO Auto-generated method stub
		return inn;
		
	}



//    @Override
//    public void update(Client entity) {
//        Client client = (Client) getCurrentSession().get(Client.class,entity.getInn());
//
//    }
}
