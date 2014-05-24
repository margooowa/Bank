package dao;

import entities.Client;

import java.util.List;


public interface ClientDAO extends GenericDAO<Client> {



    Client findByInn(long inn);
    
    long updateByInn(long inn);
    
    long deleteByInn(long inn);


}
