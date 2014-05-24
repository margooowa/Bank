package util;

import java.rmi.RemoteException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import bussiness.IHibernateUtil;

public class HibernateUtil implements IHibernateUtil{

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        synchronized (HibernateUtil.class) {
            if (sessionFactory == null) {
                buildSessionFactory();
            }
            return sessionFactory;
        }
    }
public void closeSession() throws RemoteException{
	shutdown();
}
    public static void shutdown()  {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}