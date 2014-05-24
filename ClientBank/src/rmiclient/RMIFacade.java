package rmiclient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bussiness.Contant;
import bussiness.IFacade;

public class RMIFacade {
	
	private static IFacade facade;
	
	public static IFacade getFacade(){
		if(facade==null){
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost",Contant.RMI_PORT);
				facade = (IFacade) registry.lookup(Contant.RMI_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return facade;
	}

}
