package xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "signins")
public class Signins {
	@XmlElement
	private Signin signin;
	
	public Signins(Signin s){
		this.signin=s;
	}
}
