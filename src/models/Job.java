package models;

import java.sql.SQLException;
import java.util.Vector;

import util.database.DatabaseManager;

public class Job {

	private int jobID, userID, pcID;
	
	// Job Status -> "Complete" || "UnComplete"
	private String jobStatus;

	public Job(int jobID, int userID, int pcID, String jobStatus) {
		super();
		this.jobID = jobID;
		this.userID = userID;
		this.pcID = pcID;
		this.jobStatus = jobStatus;
	}

	public Job(int userID, int pcID, String jobStatus) {
		super();
		this.userID = userID;
		this.pcID = pcID;
		this.jobStatus = jobStatus;
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getPcID() {
		return pcID;
	}

	public void setPcID(int pcID) {
		this.pcID = pcID;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	// Logics
	public static int AddNewJob(int userID, int pcID) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"INSERT INTO `jobs` (`userID`, `pcID`, `jobStatus`) VALUES"
				+ "(?, ?, ?)", 
				sp->{
					int i = 1;
					try {
						sp.setInt(i++, userID);
						sp.setInt(i++, pcID);
						sp.setString(i++, "UnComplete");

					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
	
	// update job status
	public static int UpdateJobStatus(int jobID, String jobStatus) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"UPDATE `jobs`"
				+ "SET `jobStatus` = ?"
				+ "WHERE `jobID` = ?", 
				sp->{
					try {
						int i = 1;
						sp.setString(i++, jobStatus);
						sp.setInt(i++, jobID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
	
	
	//get All Job Data
	
	public static Vector<Job> GetAllJobData(){
		Vector<Job> jobs = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `jobs`",
				sp -> {
					
				},
				parser -> {
					Vector<Job> result = new Vector<>();
					
					try {
						while(parser.next()) {
							Job job = new Job(
									parser.getInt("jobID"), 
									parser.getInt("userID"),
									parser.getInt("pcID"),
									parser.getString("jobStatus"));
							
							result.add(job);
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					return result;
					
				});
		return jobs;
	}
	
	// Menampilkan technician X memiliki job apa saja
	public static Vector<Job> GetTechnicianJob(int userID) {
	    String sql = "SELECT * FROM `jobs` WHERE userID = ?";
	    
	    return DatabaseManager.getInstance().executePrepQuery(sql, 
	        sp -> {
	            try {
	            	int i = 1;
					sp.setInt(i++, userID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        },
	        rs -> {
	            Vector<Job> jobs = new Vector<>();
	            try {
	                while (rs.next()) {
	                	Job job = new Job(
	                		rs.getInt("jobID"),
	                		rs.getInt("userID"),
	                		rs.getInt("pcID"),
	                		rs.getString("jobStatus")
	                	);
	                    
	                    jobs.add(job);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            return jobs;
	        });
	}
	
	public static void getPcOnWorkingList(int pcID) {
		// kalau mengikuti sequence diagram, method ini tidak terpakai
	}
	
	public static Job getJobDetail(int jobID) {
		String sql = "SELECT * FROM `jobs` WHERE jobID = ?";
	    
		Vector<Job> jobs = DatabaseManager.getInstance().executePrepQuery(sql, 
	        sp -> {
	            try {
	            	int i = 1;
					sp.setInt(i++, jobID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        },
	        rs -> {
	            Vector<Job> result = new Vector<>();
	            try {
	                while (rs.next()) {
	                	Job job = new Job(
	                		rs.getInt("jobID"),
	                		rs.getInt("userID"),
	                		rs.getInt("pcID"),
	                		rs.getString("jobStatus")
	                	);
	                    
	                    result.add(job);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            return result;
	        });
		
		if(jobs.isEmpty())
			return null;
		
		return jobs.get(0);
	}
}
