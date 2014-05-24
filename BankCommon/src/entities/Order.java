package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = -8349683804836489722L;

    private long id;
    private Client client;
    private Date date;
    private String status;
	private CreditProgram creditProgram;
	private boolean gurantee;
	private long creditSum;
	private long salary;

    public Order() {
    }

    public Order(Client client,CreditProgram creditProgram,boolean guarantee,long creditSum,long salary) {
        this.client = client;
        this.creditProgram = creditProgram;
        this.gurantee = guarantee;
        this.creditSum = creditSum;
        this.salary = salary;
        this.status = "Waiting";
       
        this.date = new Date(System.currentTimeMillis());
    }

    @Id
	@GeneratedValue
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "CLIENT_INN",nullable = false)
    public Client getClient() {
        return client;
    }

    @ManyToOne
    @JoinColumn(name="CREDIT_PROGRAM_ID",nullable = false)
    public CreditProgram getCreditProgram() {
        return creditProgram;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }
       

    @Column(name = "Gurantee")
    public boolean isGurantee() {
		return gurantee;
	}

    @Column(name = "CREDIT_SUM")
    public long getCreditSum() {
		return creditSum;
	}

    @Column(name = "SALARY") 
    public long getSalary() {
		return salary;
	}

	public void setClient(Client client) {
        this.client = client;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    public void setGurantee(boolean gurantee) {
		this.gurantee = gurantee;
	}

	public void setCreditSum(long creditSum) {
		this.creditSum = creditSum;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public void setCreditProgram(CreditProgram creditProgram) {
		this.creditProgram = creditProgram;
	}

	
}
