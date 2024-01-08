package models;

import java.sql.SQLException;
import java.util.Vector;

import util.database.DatabaseManager;

public class User {

	// Attributes
	private int userID;
	private String userName;
	private String userPassword;
	private int userAge;
	private String userRole;
	
	// Constructor
	public User(int userID, String userName, String userPassword, int userAge, String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	public User(String userName, String userPassword, int userAge, String userRole) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	
	// Getter & Setter
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	// Logics
	public static Vector<User> getAllUserData(){
		Vector<User> users = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `users`", 
				sp->{
					
				}, 
				parser->{
					Vector<User> result = new Vector<>();
					try {
						while(parser.next()) {
							User newUser = new User(
								parser.getInt("userID"), 
								parser.getString("userName"), 
								parser.getString("userPassword"), 
								parser.getInt("userAge"), 
								parser.getString("userRole")
							);
							
							result.add(newUser);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					return result;
				});
				
		return users;
	}
	
	public static Vector<User> getAllTechnician(){
		Vector<User> users = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `users` WHERE `userRole` = 'Computer Technician'", 
				sp->{
					
				}, 
				parser->{
					Vector<User> result = new Vector<>();
					try {
						while(parser.next()) {
							User newUser = new User(
								parser.getInt("userID"), 
								parser.getString("userName"), 
								parser.getString("userPassword"), 
								parser.getInt("userAge"), 
								parser.getString("userRole")
							);
							
							result.add(newUser);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					return result;
				});
				
		return users;
	}
	
	public static int addNewUser(String userName, String userPassword, int userAge) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"INSERT INTO `users` (`userName`, `userPassword`, `userAge`, `userRole`) VALUES"
				+ "(?, ?, ?, 'Customer')", 
				sp->{
					int i = 1;
					
					try {
						sp.setString(i++, userName);
						sp.setString(i++, userPassword);
						sp.setInt(i++, userAge);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	}
	
	public static User getUserData(String userName, String userPassword) {
		Vector<User> users = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `users` WHERE `userName` = ? AND `userPassword` = ?", 
				sp->{
					try {
						sp.setString(1, userName);
						sp.setString(2, userPassword);						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}, 
				parser->{
					Vector<User> result = new Vector<>();
					try {
						while(parser.next()) {
							User newUser = new User(
								parser.getInt("userID"), 
								parser.getString("userName"), 
								parser.getString("userPassword"), 
								parser.getInt("userAge"), 
								parser.getString("userRole")
							);
							
							result.add(newUser);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					return result;
				});
		
		if(users.isEmpty())
			return null;
		
		return users.get(0);
	}
	
	// Kami menambahkan overloading ke function getUserData yang menerima parameter userID agar memudahkan pemanggilan data user
	public static User getUserData(int userID) {
		Vector<User> users = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `users` WHERE `userID` = ?", 
				sp->{
					try {
						sp.setInt(1, userID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}, 
				parser->{
					Vector<User> result = new Vector<>();
					try {
						while(parser.next()) {
							User newUser = new User(
								parser.getInt("userID"), 
								parser.getString("userName"), 
								parser.getString("userPassword"), 
								parser.getInt("userAge"), 
								parser.getString("userRole")
							);
							
							result.add(newUser);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					return result;
				});
		
		if(users.isEmpty())
			return null;
		
		return users.get(0);
	}
	
	public static int changeUserRole(int userID, String newUserRole) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"UPDATE `users`"
				+ "SET `userRole` = ?"
				+ "WHERE `userID` = ?", 
				sp->{
					int i = 1;
					try {
						sp.setString(i++, newUserRole);
						sp.setInt(i++, userID);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
	}
	
}
