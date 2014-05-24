package bussiness;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHibernateUtil extends Remote{
	
	void closeSession() throws RemoteException;

}
