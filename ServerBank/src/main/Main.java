package main;

import dao.hibernate.*;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

    public static void main(String args[]) {

        GenericHibernateDAO<Client> clientHibernateDAO = new ClientHibernateDAO();
        GenericHibernateDAO<User> userHibernateDAO = new UserHibernateDAO();
        GenericHibernateDAO<CreditProgram> pHibernateDAO = new CreditProgramHibernateDAO();
        GenericHibernateDAO<Order> oHibernateDAO = new OrderHibernateDAO();
    
        Client client1 = new Client( "passwd", "addr1", "email1", "fio1", 12345678, "063125223");
        Client client2 = new Client( "passwd", "addr2", "email2", "fio2", 123456789l, "092345687");
        Client client3 = new Client( "passwd", "addr3", "email3", "fio3", 123452346878l, "023414541");
        clientHibernateDAO.makePersistent(client1);
        clientHibernateDAO.makePersistent(client2);
        clientHibernateDAO.makePersistent(client3);
	
        List<Client> clients = clientHibernateDAO.findAll();
        for (Client c : clients) {
            System.out.println(c.getInn());

        }
        
        
        
        Manager manager = new Manager("man_login", "pass", "vasya",false,false,false);
        userHibernateDAO.makePersistent(manager);
        Admin admin = new Admin("admin", "admin");
        userHibernateDAO.makePersistent(admin);
        CreditProgram creditProgram1 = new CreditProgram("crname", "sjort", "longg  dad", 123, 123516321546l, 12);
        pHibernateDAO.makePersistent((creditProgram1));
        CreditProgram creditProgram2 = new CreditProgram("crn", "sjo", "gfgbdgb rfgt tgbr trt trgb rtg gtbb f end", 123, 123516321546l, 12);
        pHibernateDAO.makePersistent((creditProgram2));
        Order order1 = new Order(client1,creditProgram1,false,200,300);
        oHibernateDAO.makePersistent(order1);
        Order order2 = new Order(client1,creditProgram2,true,232,2322);
        oHibernateDAO.makePersistent(order2);


        Client client4 = new Client( "passwd", "addr4", "email4", "fio4", 664356346l, "5253542354");
        clientHibernateDAO.makePersistent(client4);

        System.out.println(clientHibernateDAO.findById(123456878));
        HibernateUtil.shutdown();

    }
}
