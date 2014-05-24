package dao;



import entities.User;

public  interface UserDAO  extends GenericDAO<User>{
	User findByPassLog(String login,String password);
}
