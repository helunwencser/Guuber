package guuber.cmu.edu.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides the CRUD operations on user_table
 * */
public class DBOperation {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/guuber";
	
	private static String USER = "root";
	private static String PASS = "helunwen";
	
	private Connection conn = null;
	
	private PreparedStatement insertStatement = null;
	
	private PreparedStatement selectStatement = null;
	
	/**
	 * setup database connection and prepare statements
	 * */
	public DBOperation() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			insertStatement = conn.prepareStatement(MySQLStatement.INSERT);
			selectStatement = conn.prepareStatement(MySQLStatement.SELECT_BY_USERNAME_PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert an user into author_table
	 * @param user	
	 * 		  the user to be inserted
	 * */
	public void insertAuthor(User user) {
		try {
			this.insertStatement.setString(1, user.getUsername());
			this.insertStatement.setString(2, user.getPassword());
			this.insertStatement.setString(3, user.getUsertype());
			this.insertStatement.setString(4, user.getGender());
			this.insertStatement.setString(5, user.getEmail());
			this.insertStatement.setString(6, user.getCarId());
			this.insertStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Select user from database by username and password
	 * @param username - the name of user
	 * 
	 * @param password - password
	 * 
	 * @return if there is a record in database, return the user;
	 * 			otherwise, return null
	 * */
	public User selectByUsernameAndPassword(String username, String password) {
		try {
			this.selectStatement.setString(1, username);
			this.selectStatement.setString(2, password);
			ResultSet resultSet = this.selectStatement.executeQuery();
			if(resultSet.next()) {
				return new User(
						resultSet.getString(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getString(5),
						resultSet.getString(6)
						);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method close all database related resources. Please remember to call
	 * this function after using InsertAuthor
	 * */
	public void closeResources() {
		try {
			if(this.insertStatement != null) {
				this.insertStatement.close();	
			}
			if(this.selectStatement != null) {
				this.selectStatement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
