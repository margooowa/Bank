package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@MappedSuperclass
public abstract class User implements Serializable {

   
	private String login;
    private String password;

    private UserType userType;

    protected User() {
    }

    public User(UserType userType, String login, String password) {
        this.userType = userType;
        this.login = login;
        this.password = password;
    }

    @Column(name = "LOGIN", unique = true, nullable = false)
    public String getLogin() {
        return login;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "USER_TYPE", nullable = false)
    public UserType getuserType() {
        return userType;
    }

    public enum UserType {
        ADMIN(1),
        MANAGER(2),
        Client(3);

        private final int id;

        private UserType(int id) {
            this.id = id;
        }

    }
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
