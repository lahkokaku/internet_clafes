package controllers;

import java.time.LocalDate;
import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.PCBook;
import stores.UserStore;

public class PCBookController {

	public static Vector<PCBook> getAllPCBookedData(){
		return PCBook.getAllPCBookedData();
	}
	
	public static PCBook getPCBookedData(int pcID, LocalDate bookedDate) {
		return PCBook.getPCBookedData(pcID, bookedDate);
	}
	
	public static PCBook getPCBookedDetail(int bookID) {
		return PCBook.getPCBookedDetail(bookID);
	}
	
	public static Vector<PCBook> getPCBookedByDate(LocalDate date){
		return PCBook.getPCBookedByDate(date);
	}
	
	public static int addNewBook(int pcID, int userID, LocalDate bookedDate) {
		PCBook pcBook = getPCBookedData(pcID, bookedDate);
		
		if(pcBook != null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("PC already booked for that date");
			alert.showAndWait();
			return -1;
		}
		
		return PCBook.addNewBook(pcID, userID, bookedDate);
	}
	
	public static int deleteBookData(int bookID) {
		PCBook pcBook = getPCBookedDetail(bookID);
		
		if(pcBook == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Booking not found");
			alert.showAndWait();
			return -1;
		}
		
		return PCBook.deleteBookData(bookID);
	}

	public static int assignUserToNewPC(int bookID, int newPcID) {
		PCBook pcBook = getPCBookedDetail(bookID);
		PCBook targetPcBook = PCBookController.getPCBookedData(newPcID, pcBook.getBookedDate());
		if(targetPcBook != null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("PC is booked for that date");
			alert.showAndWait();
			return -1;
		}
		return PCBook.assignUserToNewPC(bookID, newPcID);
	}
	
	public static int finishBook(Vector<PCBook> pcBooks) {
		if(pcBooks.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("No bookings found");
			alert.showAndWait();
			return -1;
		}
		
		if(!pcBooks.get(0).getBookedDate().isBefore(LocalDate.now())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Book time hasn't passed yet");
			alert.showAndWait();
			return -1;
		}
		
		for (PCBook pcBook : pcBooks) {
			PCBookController.deleteBookData(pcBook.getBookID());
		}
		
		return TransactionController.addNewTransaction(UserStore.getInstance().getUserID(), LocalDate.now(), pcBooks);
	}
	
}
