package bussiness;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import dao.ClientDAO;
import dao.CreditProgramDAO;
import dao.ManagerDAO;
import dao.OrderDAO;
import dao.UserDAO;
import dao.hibernate.ClientHibernateDAO;
import dao.hibernate.CreditProgramHibernateDAO;
import dao.hibernate.ManagerHibernateDAO;
import dao.hibernate.OrderHibernateDAO;
import dao.hibernate.UserHibernateDAO;
import entities.Client;
import entities.CreditProgram;
import entities.Manager;
import entities.Order;
import entities.User;




public class Facade  extends UnicastRemoteObject implements IFacade {

	public Facade() throws RemoteException {
		super();
		FileHandler fileTxt=null;
		try {
			fileTxt = new FileHandler("eLog.txt", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
        SimpleFormatter formatter = new SimpleFormatter();
        fileTxt.setFormatter(formatter);
        LOGGER.addHandler(fileTxt);
        LOGGER.setLevel(Level.INFO);
		// TODO Auto-generated constructor stub
	}
	ClientDAO clientDAO = new ClientHibernateDAO();
	CreditProgramDAO creditProgramDAO = new CreditProgramHibernateDAO();
	UserDAO userDAO = new UserHibernateDAO();
	ManagerDAO managerDAO = new ManagerHibernateDAO();
	OrderDAO orderDAO = new OrderHibernateDAO();
/////////////////
	
private static Logger LOGGER = Logger.getLogger("info");


	
		
	
	public List<Client> findAllClient()  throws RemoteException {
		return clientDAO.findAll();
	}

	public List<Manager> findAllManager()  throws RemoteException{
		return managerDAO.findAll();
	}

	public List<Order> findAllOrders()  throws RemoteException{
		return orderDAO.findAll();
	}

	public List<CreditProgram> findAllCreditPrograms()  throws RemoteException{
		return creditProgramDAO.findAll();
	}
//////////////////////////
	public Client makePersistentClient(Client entity)  throws RemoteException{
		return clientDAO.makePersistent(entity);
	}

	public Manager makePersistentManager(Manager entity)  throws RemoteException{
		return managerDAO.makePersistent(entity);
	}

	public CreditProgram makePersistentCreditProgram(CreditProgram entity)  throws RemoteException{
		return creditProgramDAO.makePersistent(entity);
	}

	public Order makePersistentOrder(Order entity)  throws RemoteException{
		return orderDAO.makePersistent(entity);
	}

	////////////////////////////////
	
	

	public CreditProgram findByIdCreditProgram(long id)  throws RemoteException{
		return creditProgramDAO.findById(id);
	}

	
////////////////////////////////////////
	

	public long deleteByIdManager(long id)  throws RemoteException{
		return managerDAO.deleteById(id);
	}
	
	public long deleteByIdCreditProgram(long id)  throws RemoteException{
		return creditProgramDAO.deleteById(id);
	}
	
	
/////////////////////////////////////////////////
	

	public long updateByIdManager(long id)  throws RemoteException{
		return managerDAO.updateByid(id);
	}
	
	public long updateByIdCreditProgram(long id) throws RemoteException {
		return creditProgramDAO.updateByid(id);
	}
	
	public long updateByIdOrder(long id)  throws RemoteException{
		return orderDAO.updateByid(id);
	}
	//////////////////////////////////////////////////////
	
	public User findByPassLogUser(String login,String password) throws RemoteException{
	User user = userDAO.findByPassLog(login,password);
	try {
		log(user);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return user;
	}
	
	private void log(User user) throws IOException{
	        	
	            String info = "Login: "+user.getLogin()+", User Type: "+user.getuserType();
	            LOGGER.info(info);
            
	        	}
	
	//////////////////////////////////////////
	
	public List<Order> findByInnOrders(String inn)  throws RemoteException{
		return orderDAO.findByInn(inn);
	}
	///////////////////////////////////////////////////
	

    public Client findByInnClient(long inn)  throws RemoteException{
    	return clientDAO.findByInn(inn);
    }
    
    public long updateByInnClient(long inn)  throws RemoteException{
		return clientDAO.updateByInn(inn);
	}
    
    public long deleteByInnClient(long inn)  throws RemoteException{
		return clientDAO.deleteByInn(inn);
	}
  //////////////////////////////////////////
    public Manager findByLoginManager(String login)  throws RemoteException{
    	return managerDAO.findByLogin(login);
    }

    public void editCreditProgram(String name, Long minSum, Long maxSum, Long termin,
			String shortDescr, String longDescr,Long id)throws RemoteException {
					
		CreditProgram cp = creditProgramDAO.findById(id);
							cp.setName(name);
							cp.setMinSum(minSum);
							cp.setMaxSum(maxSum);
							cp.setCreditTimeExpiration(termin);
							cp.setShortDescription(shortDescr);
							cp.setLongDescription(longDescr);
					
	}
	

    public void updateCreditProgram(Map<String, String> map) throws RemoteException {
        CreditProgram cr = creditProgramDAO.findById(Long.parseLong(map.get("id")));
        cr.setName(map.get("name"));
        cr.setMinSum(Long.parseLong(map.get("minSum")));
        cr.setMaxSum(Long.parseLong( map.get("maxSum")));
        cr.setShortDescription(map.get("shortDesc"));
        cr.setLongDescription(map.get("longDesc"));
        cr.setCreditTimeExpiration(Long.parseLong(map.get("term")));
        creditProgramDAO.makePersistent(cr);
    }

	@Override
	public void updateManager(Map<String, String> map) throws RemoteException {
		Manager man = managerDAO.findById(Long.parseLong(map.get("id")));
		man.setLogin(map.get("login"));
		man.setPassword(map.get("password"));
		man.setName(map.get("name"));
		man.setCreateRule(Boolean.parseBoolean(map.get("createRule")));
		man.setUpdateRule(Boolean.parseBoolean(map.get("updateRule")));
		man.setDeleteRule(Boolean.parseBoolean(map.get("deleteRule")));
		managerDAO.makePersistent(man);
		
	}

	@Override
	public void updateClient(Map<String, String> map) throws RemoteException {
		Client client = clientDAO.findByInn(Long.parseLong(map.get("inn")));
		client.setFio(map.get("fio"));
		client.setAdress(map.get("adress"));
		client.setEmail(map.get("email"));
		client.setPhone(map.get("phone"));
		client.setPassword(map.get("password"));
		
		clientDAO.makePersistent(client);
		
		
	}

	@Override
	public void updateStatusOrder(Map<String, String> map)
			throws RemoteException {
		Order order = orderDAO.findById(Long.parseLong(map.get("id")));
		order.setStatus(map.get("status"));
		orderDAO.makePersistent(order);
	}
			
	
		
	}


	


	
	
	
	
	
