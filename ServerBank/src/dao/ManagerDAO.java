package dao;

import entities.Manager;

public interface ManagerDAO extends GenericDAO<Manager>{
	
	Manager findByLogin(String login);
}
