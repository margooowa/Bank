package bussiness;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import entities.Client;
import entities.CreditProgram;
import entities.Manager;
import entities.Order;
import entities.User;

  public interface IFacade extends Remote{

	User findByPassLogUser(String logi,String pass) throws RemoteException;
	
		
	  List<Client> findAllClient()  throws RemoteException ;

	  List<Manager> findAllManager()  throws RemoteException;

	  List<Order> findAllOrders()  throws RemoteException;

	  List<CreditProgram> findAllCreditPrograms()  throws RemoteException;	
	  
//////////////////////////
	  Client makePersistentClient(Client entity)  throws RemoteException;

	  Manager makePersistentManager(Manager entity)  throws RemoteException;

	  CreditProgram makePersistentCreditProgram(CreditProgram entity)  throws RemoteException;

	  Order makePersistentOrder(Order entity)  throws RemoteException;

	////////////////////////////////
	
	  CreditProgram findByIdCreditProgram(long id)  throws RemoteException;


////////////////////////////////////////
	

	  long deleteByIdManager(long id)  throws RemoteException;
	
	  long deleteByIdCreditProgram(long id)  throws RemoteException;
	
	/////////////////////////////////////////////////
	

	  long updateByIdManager(long id)  throws RemoteException;
	
	  long updateByIdCreditProgram(long id) throws RemoteException ;
	  
	  long updateByIdOrder(long id)  throws RemoteException;
	//////////////////////////////////////////////////////

	
	//////////////////////////////////////////
	
	  List<Order> findByInnOrders(String inn)  throws RemoteException;
	///////////////////////////////////////////////////
	

      Client findByInnClient(long inn)  throws RemoteException;
    
      long updateByInnClient(long inn)  throws RemoteException;
    
      long deleteByInnClient(long inn)  throws RemoteException;
  //////////////////////////////////////////
      Manager findByLoginManager(String login)  throws RemoteException;
      
      
      void updateCreditProgram(Map<String,String> map) throws  RemoteException;
      
      void updateManager(Map<String,String> map) throws  RemoteException;
      
      void updateClient(Map<String,String> map) throws  RemoteException;
      
      void updateStatusOrder(Map<String,String> map) throws  RemoteException;
}
  
  
