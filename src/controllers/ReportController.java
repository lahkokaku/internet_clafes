package controllers;

import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.PC;
import models.Report;

public class ReportController {

	public static Vector<Report> getAllReportData(){
		return Report.getAllReportData();
	}
	
	public static int addNewReport(String userRole, int pcID, String reportNote) {
		PC pc = PCController.getPCDetail(pcID);
		
		if(pc == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("PC not found");
			alert.showAndWait();
			return -1;
		}
		
		if(reportNote.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Report Note must be filled");
			alert.showAndWait();
			return -1;
		}
		
		return Report.addNewReport(userRole, pcID, reportNote);		
	}

}
