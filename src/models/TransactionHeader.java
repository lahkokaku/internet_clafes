package models;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import controllers.UserController;
import util.database.DatabaseManager;

public class TransactionHeader {

	// Attributes
	private int transactionID, staffID;
	private String staffName;
	private LocalDate transactionDate;
	
	// Constructor
	public TransactionHeader(int transactionID, int staffID, String staffName, LocalDate transactionDate) {
		super();
		this.transactionID = transactionID;
		this.staffID = staffID;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}
	public TransactionHeader(int staffID, String staffName, LocalDate transactionDate) {
		super();
		this.staffID = staffID;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}
	
	// Getter & Setter
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	// Logics
	public static Vector<TransactionHeader> getAllTransactionHeaderData(){
		Vector<TransactionHeader> transactionHeaders = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `transaction_headers`", 
				sp->{
					
				}, 
				parser->{
					Vector<TransactionHeader> result = new Vector<>();
					
					try {
						while(parser.next()) {
							TransactionHeader th = new TransactionHeader(
									parser.getInt("transactionID"), 
									parser.getInt("staffID"), 
									parser.getString("staffName"), 
									parser.getDate("transactionDate").toLocalDate());
							
							result.add(th);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return result;
				});
		return transactionHeaders;
	}
	
	public static int addNewTransactionHeader(int staffID, LocalDate transactionDate) {
		User user = UserController.getUserData(staffID);
		
		DatabaseManager.getInstance().executePrepUpdate(
				"INSERT INTO `transaction_headers` (`staffID`, `staffName`, `transactionDate`) VALUES"
				+ "(?, ?, ?)", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, staffID);
						sp.setString(i++, user.getUserName());
						sp.setDate(i++, Date.valueOf(transactionDate));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
		
		return getAllTransactionHeaderData().size();
	}
	
}
