package models;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import util.database.DatabaseManager;

public class Report {

	// Attributes
	private int reportID;
	private String userRole;
	private int pcID;
	private String reportNote;
	private LocalDate reportDate;
	
	// Constructor
	public Report(int reportID, String userRole, int pcID, String reportNote, LocalDate reportDate) {
		super();
		this.reportID = reportID;
		this.userRole = userRole;
		this.pcID = pcID;
		this.reportNote = reportNote;
		this.reportDate = reportDate;
	}
	
	public Report(String userRole, int pcID, String reportNote, LocalDate reportDate) {
		super();
		this.userRole = userRole;
		this.pcID = pcID;
		this.reportNote = reportNote;
		this.reportDate = reportDate;
	}

	// Getters $ Setters
	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getPcID() {
		return pcID;
	}

	public void setPcID(int pcID) {
		this.pcID = pcID;
	}

	public String getReportNote() {
		return reportNote;
	}

	public void setReportNote(String reportNote) {
		this.reportNote = reportNote;
	}

	public LocalDate getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}
	
	// Logic
	public static Vector<Report> getAllReportData(){
		Vector<Report> reports = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `reports`", 
				sp->{
					
				}, 
				parser->{
					Vector<Report> result = new Vector<>();
					try {
						while(parser.next()) {
							Report report = new Report(
									parser.getInt("reportID"), 
									parser.getString("userRole"), 
									parser.getInt("pcID"), 
									parser.getString("reportNote"), 
									parser.getDate("reportDate").toLocalDate());
							
							result.add(report);
						}
					} catch (SQLException e) {
						// TODO: handle exception
					}
					
					return result;
				});
		
		return reports;
	}
	
	public static int addNewReport(String userRole, int pcID, String reportNote) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"INSERT INTO `reports` (`userRole`, `pcID`, `reportNote`, `reportDate`) VALUES"
				+ "(?, ?, ?, ?)", 
				sp->{
					int i = 1;
					try {
						sp.setString(i++, userRole);
						sp.setInt(i++, pcID);
						sp.setString(i++, reportNote);
						sp.setDate(i++, new Date(System.currentTimeMillis()));
					} catch (SQLException e) {
						// TODO: handle exception
					}
				});
	}
	
}
