package entities;

import javax.persistence.*;

/**
 * User: ka4eli
 * Date: 11/21/13
 * Time: 1:11 AM
 */
@Entity
@Table(name = "admins")
public class Admin extends User{

    private int id;
    

    public Admin() {
    }

    public Admin(String login, String password) {
        super(UserType.ADMIN, login, password);
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
