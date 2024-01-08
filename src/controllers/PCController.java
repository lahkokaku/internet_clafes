package controllers;

import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.PC;

public class PCController {

	public static Vector<PC> getAllPCData() {
		return PC.getAllPCData();
	}
	
	public static PC getPCDetail(int pcID) {
		return PC.getPCDetail(pcID);
	}
	
	public static int addNewPC(int pcID) {	
		PC pc = PCController.getPCDetail(pcID);
		
		if(pc != null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("PC already exist");
			alert.showAndWait();
			return -1;
		}
		
		return PC.addNewPC(pcID);
	}
	
	public static int updatePC(int pcID, String pcCondition) {
		PC pc = getPCDetail(pcID);
		
		if(pc == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("PC not found");
			alert.showAndWait();
			return -1;
		}
		
		if(pcCondition.length() <= 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("PC Condition must be filled");
			alert.showAndWait();
			return -1;
		}
		
		return PC.updatePC(pcID, pcCondition);
	}
	
	public static int deletePC(int pcID) {
		PC pc = getPCDetail(pcID);
		
		if(pc == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("PC not found");
			alert.showAndWait();
			return -1;
		}
		
		return PC.deletePC(pcID);
	}

}
