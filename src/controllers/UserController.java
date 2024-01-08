package controllers;

import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.User;
import stores.UserStore;
import util.AdditionalValidations;
import util.StageManager;
import views.LoginView;
import views.ViewAllPCView;

public class UserController {

	public static Vector<User> getAllUserData(){		
		return User.getAllUserData();
	}
	
	public static Vector<User> getAllTechnician(){
		return User.getAllTechnician();
	}
	
	public static boolean addNewUser(String userName, String userPassword, String confPassword, int userAge) {
		if(userName.length() < 7) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Name must at least be 7 characters long");
			alert.showAndWait();
			return false;
		}
		
		Vector<User> users = getAllUserData();
		
		for (User user : users) {
			if(user.getUserName().equals(userName)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Name is already taken");
				alert.showAndWait();
				return false;
			}
		}
		
		if(userPassword.length() < 6) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Password must at least be 6 characters long");
			alert.showAndWait();
			return false;
		}
		
		if(!AdditionalValidations.isAlphanumeric(userPassword)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Password must be alphanumerical");
			alert.showAndWait();
			return false;
		}
		
		if(confPassword.length() <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Confirm Password must be filled");
			alert.showAndWait();
			return false;
		}
		
		if(!userPassword.equals(confPassword)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Confirm Password doesn't match password");
			alert.showAndWait();
			return false;
		}
		
		User.addNewUser(userName, userPassword, userAge);
		
		return true;
	}
	
	public static User getUserData(String userName, String userPassword) {
		if(userName.length() <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Name must be filled");
			alert.showAndWait();
			return null;
		}
		if(userPassword.length() <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Password must be filled");
			alert.showAndWait();
			return null;
		}
		
		User user = User.getUserData(userName, userPassword);
		
		return user;
	}
	
	// Kami menambahkan overloading ke function getUserData yang menerima parameter userID agar memudahkan pemanggilan data user
	public static User getUserData(int userID) {
		return User.getUserData(userID);
	}
	
	public static boolean changeUserRole(int userID, String newUserRole) {
		Vector<User> users = getAllUserData();
		
		for (User user : users) {
			if(userID == user.getUserID() && !user.getUserRole().equals("Customer")) {
				User.changeUserRole(userID, newUserRole);
				return true;
			}
		}
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Staff not found!");
		alert.showAndWait();
		return false;
	}

	// Logic tambahan untuk membantu proses autentikasi
	public static void login(String userName, String userPassword) {
		User user = getUserData(userName, userPassword);
		
		if(user == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Invalid Credentials");
			alert.showAndWait();
			return;
		}
		
		UserStore.getInstance().setUser(user);
		StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Success");
		alert.setContentText("Login Successful");
		alert.showAndWait();
	}
	
	// Logic tambahan untuk membantu proses de-autentikasi
	public static void logout() {
		UserStore.getInstance().removeUser();
		StageManager.getInstance().setScene(new LoginView().getScene());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Success");
		alert.setContentText("Logout Successful");
		alert.showAndWait();
	}
	
}
