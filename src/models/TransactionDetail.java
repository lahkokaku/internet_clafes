package models;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Vector;

import controllers.UserController;
import util.database.DatabaseManager;

public class TransactionDetail {

	/**
	 * Asumsi
	 * Disini kami menambahkan attribute 'customerID' karena:
	 * -Pada soal .docx disebutkan bahwa TransactionDetail memegang attribute 'customerID' (dalam userID)
	 * -Lebih masuk akal apabila customerID dimasukan sebagai foreign key karena 'customerID' adalah primary key sebenarnya dari tabel 'users'
	 * -Pada diagram, semua pemanggilan function yang berkaitan dengan user ke TransactionDetail model atau controller menggunakan parameter userID bukan userName
	 */
	
	// Attributes
	private int transactionID, pcID, customerID;
	private String customerName;
	private LocalTime bookedTime;
	
	// Constructor
	public TransactionDetail(int transactionID, int pcID, int customerID, String customerName, LocalTime bookedTime) {
		super();
		this.transactionID = transactionID;
		this.pcID = pcID;
		this.customerID = customerID;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
	}
	
	public TransactionDetail(int pcID, int customerID, String customerName, LocalTime bookedTime) {
		super();
		this.pcID = pcID;
		this.customerID = customerID;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
	}

	// Getter & Setter
	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getPcID() {
		return pcID;
	}

	public void setPcID(int pcID) {
		this.pcID = pcID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalTime getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(LocalTime bookedTime) {
		this.bookedTime = bookedTime;
	}
	
	// Logics
	public static Vector<TransactionDetail> getUserTransactionDetail(int userID){
		Vector<TransactionDetail> transactionDetails = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `transaction_details`"
				+ "WHERE customerID = ?", 
				sp->{
					try {
						sp.setInt(1, userID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}, 
				parser->{
					Vector<TransactionDetail> result = new Vector<>();
					try {
						while(parser.next()) {
							TransactionDetail transactionDetail = new TransactionDetail(
								parser.getInt("transactionID"),
								parser.getInt("pcID"),
								parser.getInt("customerID"),
								parser.getString("customerName"),
								parser.getTime("bookedTime").toLocalTime());
							result.add(transactionDetail);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return result;
				});
		
		return transactionDetails;
	}
	
	public static Vector<TransactionDetail> getAllTransactionDetail(int transactionID){
		Vector<TransactionDetail> transactionDetails = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `transaction_details`"
				+ "WHERE transactionID = ?", 
				sp->{
					try {
						sp.setInt(1, transactionID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}, 
				parser->{
					Vector<TransactionDetail> result = new Vector<>();
					try {
						while(parser.next()) {
							TransactionDetail transactionDetail = new TransactionDetail(
								parser.getInt("transactionID"),
								parser.getInt("pcID"),
								parser.getInt("customerID"),
								parser.getString("customerName"),
								parser.getTime("bookedTime").toLocalTime());
							result.add(transactionDetail);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return result;
				});
		
		return transactionDetails;
	}
	
	public static int addTransactionDetail(int transactionID, Vector<PCBook> pcBooks) {
		int result = 0;
		
		for (PCBook pcBook : pcBooks) {
			User user = UserController.getUserData(pcBook.getUserID());
			result += DatabaseManager.getInstance().executePrepUpdate(
					"INSERT INTO `transaction_details` (`transactionID`, `pcID`, `customerID`, `customerName`, `bookedTime`) VALUES"
					+ "(?, ?, ?, ?, ?)", 
					sp->{
						try {
							int i = 1;
							sp.setInt(i++, transactionID);
							sp.setInt(i++, pcBook.getPcID());
							sp.setInt(i++, pcBook.getUserID());
							sp.setString(i++, user.getUserName());
							sp.setTime(i++, Time.valueOf(LocalTime.now()));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
		}
		
		return result;
	}
}
