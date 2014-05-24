package xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import entities.User;

@XmlRootElement(name="signin")
public class Signin {
	public Signin(User user2) {
		this.user = user2;
		this.time = System.currentTimeMillis();
	}
	@XmlElement
	private User user;
	@XmlElement
	private Long time;
}
