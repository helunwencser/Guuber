package guuber.cmu.edu.db;

/**
 * This class defines MySQL statement used in other class
 * */
public class MySQLStatement {
	
	/* insert statement */
	public static String INSERT = "insert into user_table " + 
									"(username, password, usertype, email, gender, carid) " +
									"values (?, ?, ?, ?, ?, ?)";
	
	/* select user by username and password */
	public static String SELECT_BY_USERNAME_PASSWORD = "select * from user_table where username = ? and password = ?";
	
	/* select user by username */
	public static String SELECT_BY_USERNAME = "select * from user_table where username = ?";
	
	/* delete user by username */
	public static String DELETE_BY_USERNAME = "delete from user_table where username = ?";

}
