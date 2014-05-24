package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "credit_programs")
public class CreditProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String shortDesc;
    private String longDesc;
    private long term;
    private long minSum;
    private long maxSum;
    private List<Client> clients = new ArrayList<Client>();
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany(targetEntity = Order.class,
             cascade = CascadeType.REMOVE)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_programs",
            joinColumns = {@JoinColumn(name = "PROGRAM_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CLIENT_INN")})
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public CreditProgram() {
    }

    public CreditProgram(String name, String shortDesc, String longDesc, long minSum, long maxSum, long term) {
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.minSum = minSum;
        this.maxSum = maxSum;
        this.term = term;
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Column(name = "NAME", length = 45)
    public String getName() {
        return name;
    }

    @Column(name = "SHORT_DESC", length = 100)
    public String getShortDescription() {
        return shortDesc;
    }

    @Column(name = "LONG_DESC", length = 512)
    public String getLongDescription() {
        return longDesc;
    }

    @Column(name = "MIN_SUM")
    public long getMinSum() {
        return minSum;
    }

    @Column(name = "MAX_SUM")
    public long getMaxSum() {
        return maxSum;
    }

    @Column(name = "TERM")
    public long getCreditTimeExpiration() {
        return term;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortDescription(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public void setLongDescription(String longDesc) {
        this.longDesc = longDesc;
    }

    public void setCreditTimeExpiration(long term) {
        this.term = term;
    }

    public void setMinSum(long minSum) {
        this.minSum = minSum;
    }

    public void setMaxSum(long maxSum) {
        this.maxSum = maxSum;
    }


}
