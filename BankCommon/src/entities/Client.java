package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clients")
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private long inn;
    private String fio;
    private String adress;
    private String email;
    private String phone;

    private List<Order> orders = new ArrayList<Order>();
    private List<CreditProgram> creditPrograms = new ArrayList<CreditProgram>();

    @ManyToMany
    @JoinTable(name = "client_programs",
            joinColumns = {@JoinColumn(name = "CLIENT_INN")},
            inverseJoinColumns = {@JoinColumn(name = "PROGRAM_ID")})
    public List<CreditProgram> getCreditPrograms() {
        return creditPrograms;
    }

    public Client() {
    }

    public Client(String password,
                  String adress, String email, String fio, long inn,
                  String phone) {
        super(UserType.Client, String.valueOf(inn), password);
        this.adress = adress;
        this.email = email;
        this.fio = fio;
        this.inn = inn;
        this.phone = phone;
    }

    public Client(String inn2) {
		// TODO Auto-generated constructor stub
	}

	@OneToMany(targetEntity = Order.class, mappedBy = "client")
    public List<Order> getOrders() {
        return orders;
    }

    @Column(name = "FIO", nullable = false)
    public String getFio() {
        return fio;
    }

    @Column(name = "ADDRESS", nullable = false)
    public String getAdress() {
        return adress;
    }

    @Column(name = "EMAIL", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(name = "PHONE", unique = true, nullable = false)
    public String getPhone() {
        return phone;
    }

    @Id
    @Column(name = "INN", unique = true, nullable = false)
    public long getInn() {
        return inn;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setCreditPrograms(List<CreditProgram> creditPrograms) {
        this.creditPrograms = creditPrograms;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addProgram(CreditProgram creditProgram) {
        this.creditPrograms.add(creditProgram);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(fio);
        sb.append(" ").append(inn).append(" ").
                append(email).append(" ").append(phone).append(" ").
                append(adress);
        return sb.toString();
    }
}
