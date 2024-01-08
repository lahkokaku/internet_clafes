package controllers;

import java.time.LocalDate;
import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.PCBook;
import models.TransactionDetail;
import models.TransactionHeader;


public class TransactionController {

	public static int addNewTransaction(int staffID, LocalDate transactionDate, Vector<PCBook> pcBooks) {
		if(pcBooks.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("No booking schedules found");
			alert.showAndWait();
			return -1;
		}
		
		if(pcBooks.get(0).getBookedDate().isAfter(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("The Booking Date hasn't passed yet");
			alert.showAndWait();
			return -1;
		}
		
		int transactionID = TransactionHeader.addNewTransactionHeader(staffID, transactionDate);
		
		return TransactionDetail.addTransactionDetail(transactionID, pcBooks);
	}

	public static Vector<TransactionHeader> getAllTransactionHeader(){
		return TransactionHeader.getAllTransactionHeaderData();
	}
	
	public static Vector<TransactionDetail> getAllTransactionDetail(int transactionID){
		return TransactionDetail.getAllTransactionDetail(transactionID);
	}
	
	public static Vector<TransactionDetail> getUserTransactionDetails(int userID){
		return TransactionDetail.getUserTransactionDetail(userID);
	}
}
