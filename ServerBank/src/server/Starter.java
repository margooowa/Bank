package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import bussiness.Facade;
import bussiness.Contant;


public class Starter {
public static void main(String [] args) throws RemoteException, AlreadyBoundException{
	Facade f = new Facade();
	Registry registry = LocateRegistry.createRegistry(Contant.RMI_PORT);
	registry.bind(Contant.RMI_ID, f);
	
	System.out.println("Start is started");
}
}
