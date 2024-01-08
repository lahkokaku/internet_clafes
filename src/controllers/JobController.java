package controllers;

import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Job;
import models.PC;
import models.User;

public class JobController {

	public static Vector<Job> GetAllJobData() {
		return Job.GetAllJobData();
	}
	
	public static int AddNewJob(int userID, int pcID) {
		PC pc = PCController.getPCDetail(pcID);
		Vector<User> users = UserController.getAllUserData();
		
		if(pc == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("PC not found");
			alert.showAndWait();
			return -1;
		}
		
		boolean userExists = users.stream().anyMatch(user -> user.getUserID() == userID);
		
		 if (!userExists) {
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setContentText("User not found");
		        alert.showAndWait();
		        return -1;
		 }
		
		return Job.AddNewJob(userID, pcID);	
	}
	
	public static int UpdateJobStatus(int jobID, String jobStatus) {
		
		return Job.UpdateJobStatus(jobID, jobStatus);
		
	}
	
	public static Vector<Job> GetTechnicianJob(int userID){
		
		Vector<User> users = UserController.getAllUserData();
		boolean userExists = users.stream().anyMatch(user -> user.getUserID() == userID);
		
		 if (!userExists) {
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setContentText("User not found");
		        alert.showAndWait();
		        return new Vector<>();
		 }
		 
		 return Job.GetTechnicianJob(userID);
		
	}
	
	public static Job getJobDetail(int jobID) {
		return Job.getJobDetail(jobID);
	}
	
	public static void getPcOnWorkingList(int pcID) {
		// kalau mengikuti sequence diagram, method ini tidak terpakai
	}

}
