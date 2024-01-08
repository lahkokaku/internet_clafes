package models;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import util.database.DatabaseManager;

public class PCBook {

	// Attributes
	private int bookID, pcID, userID;
	private LocalDate bookedDate;
	
	// Constructor
	public PCBook(int bookID, int pcID, int userID, LocalDate bookedDate) {
		super();
		this.bookID = bookID;
		this.pcID = pcID;
		this.userID = userID;
		this.bookedDate = bookedDate;
	}
	
	public PCBook(int pcID, int userID, LocalDate bookedDate) {
		super();
		this.pcID = pcID;
		this.userID = userID;
		this.bookedDate = bookedDate;
	}

	// Getter & Setter
	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getPcID() {
		return pcID;
	}

	public void setPcID(int pcID) {
		this.pcID = pcID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public LocalDate getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDate bookedDate) {
		this.bookedDate = bookedDate;
	}
	
	// Logics
	public static Vector<PCBook> getAllPCBookedData(){
		return DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `pc_books`", 
				sp->{
					
				},
				parser->{
					Vector<PCBook> result = new Vector<>();
					try {
						while(parser.next()) {
							PCBook pcBook = new PCBook(
									parser.getInt("bookID"),
									parser.getInt("pcID"),
									parser.getInt("userID"),
									parser.getDate("bookedDate").toLocalDate());
							
							result.add(pcBook);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return result;
				});
	}
	
	public static PCBook getPCBookedData(int pcID, LocalDate bookedDate){
		Vector<PCBook> pcBooks = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `pc_books` WHERE `pcID` = ? AND `bookedDate` = ?", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, pcID);
						sp.setDate(i++, Date.valueOf(bookedDate));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				},
				parser->{
					Vector<PCBook> result = new Vector<>();
					try {
						while(parser.next()) {
							PCBook pcBook = new PCBook(
									parser.getInt("bookID"),
									parser.getInt("pcID"),
									parser.getInt("userID"),
									parser.getDate("bookedDate").toLocalDate());
							
							result.add(pcBook);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return result;
				});
		
		if(pcBooks.isEmpty()) return null;
		
		return pcBooks.get(0);
	}
	
	public static PCBook getPCBookedDetail(int bookID){
		Vector<PCBook> pcBooks = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `pc_books` WHERE `bookID` = ?", 
				sp->{
					try {
						sp.setInt(1, bookID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				},
				parser->{
					Vector<PCBook> result = new Vector<>();
					try {
						while(parser.next()) {
							PCBook pcBook = new PCBook(
									parser.getInt("bookID"),
									parser.getInt("pcID"),
									parser.getInt("userID"),
									parser.getDate("bookedDate").toLocalDate());
							
							result.add(pcBook);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return result;
				});
		
		if(pcBooks.isEmpty()) return null;
		
		return pcBooks.get(0);
	}
	
	public static int addNewBook(int pcID, int userID, LocalDate bookedDate) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"INSERT INTO `pc_books` (`pcID`, `userID`, `bookedDate`) VALUES"
				+ "(?, ?, ?)", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, pcID);
						sp.setInt(i++, userID);
						sp.setDate(i++, Date.valueOf(bookedDate));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
	
	public static int deleteBookData(int bookID) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"DELETE FROM `pc_books`"
				+ "WHERE `bookID` = ?", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, bookID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}

	public static Vector<PCBook> getPCBookedByDate(LocalDate date) {
		Vector<PCBook> pcBooks = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `pc_books`"
				+ "WHERE `bookedDate` = ?", 
				sp->{
					try {
						sp.setDate(1, Date.valueOf(date));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}, 
				parser->{
					Vector<PCBook> result = new Vector<>();
					try {
						while (parser.next()) {
							PCBook pcBook = new PCBook(
									parser.getInt("bookID"), 
									parser.getInt("pcID"), 
									parser.getInt("userID"), 
									parser.getDate("bookedDate").toLocalDate());
							
							result.add(pcBook);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return result;
				});
		
		return pcBooks;
	}

	public static int assignUserToNewPC(int bookID, int newPcID) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"UPDATE `pc_books` "
				+ "SET `pcID` = ? "
				+ "WHERE `bookID` = ?", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, newPcID);
						sp.setInt(i++, bookID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
	
	public static void finishBook(Vector<PCBook> pcBooks) {
		// kalau mengikuti dari sequnce diagram, method ini tidak terpakai
	}
}
