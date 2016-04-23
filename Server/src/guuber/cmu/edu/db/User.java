package guuber.cmu.edu.db;
import java.io.Serializable;

/**
 * This class defines a user
 * */
public class User implements Serializable {
	
	/**
	 * TODO: this should be the same as in client
	 */
	private static final long serialVersionUID = -7638071369434868330L;
	
	private String username;
	private String password;
	private String usertype;
	private String gender;
	private String email;
	private String carid;
	
	public User(
			String username,
			String password,
			String usertype,
			String gender,
			String email,
			String carid
			) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.gender = gender;
		this.email = email;
		this.carid = carid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCarId() {
		return carid;
	}
	
	public void setCarId(String carid) {
		this.carid = carid;
	}
}
