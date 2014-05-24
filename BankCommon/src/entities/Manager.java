package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "managers")
public class Manager extends User implements Serializable {

	private static final long serialVersionUID = -501990514846486265L;

	private long id;
	private String name;
	private boolean createRule;
	private boolean updateRule;
	private boolean deleteRule;

	public Manager() {
	}

	public Manager(String login, String password, String name,
			boolean createRule, boolean updateRule, boolean deleteRule) {
		super(UserType.MANAGER, login, password);
		this.name = name;
		this.createRule = createRule;
		this.deleteRule = deleteRule;
		this.updateRule = updateRule;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long getId() {
		return id;
	}

	@Column(name = "NAME",nullable = false)
	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isCreateRule() {
		return createRule;
	}

	public void setCreateRule(boolean createRule) {
		this.createRule = createRule;
	}

	public boolean isUpdateRule() {
		return updateRule;
	}

	public void setUpdateRule(boolean updateRule) {
		this.updateRule = updateRule;
	}

	public boolean isDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(boolean deleteRule) {
		this.deleteRule = deleteRule;
	}

}
