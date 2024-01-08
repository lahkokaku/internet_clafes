package models;

import java.sql.SQLException;
import java.util.Vector;

import util.database.DatabaseManager;

public class PC {

	// Attribute
	private int pcID;
	
	// PC_Condition -> "Broken" || "Usable" || "Maintenance"
	private String pcCondition;

	// Constructor
	public PC(int pcID, String pcCondition) {
		super();
		this.pcID = pcID;
		this.pcCondition = pcCondition;
	}

	public PC(String pcCondition) {
		super();
		this.pcCondition = pcCondition;
	}

	// Getter & Setter
	public int getPcID() {
		return pcID;
	}

	public void setPcID(int pcID) {
		this.pcID = pcID;
	}

	public String getPcCondition() {
		return pcCondition;
	}

	public void setPcCondition(String pcCondition) {
		this.pcCondition = pcCondition;
	}
	
	// Logic
	public static Vector<PC> getAllPCData(){
		Vector<PC> pcs = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `pcs`",
				sp->{
					
				},
				parser->{
					Vector<PC> result = new Vector<>();
					try {
						while(parser.next()) {
							PC pc = new PC(
									parser.getInt("pcID"), 
									parser.getString("pcCondition"));
							
							result.add(pc);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return result;
				});
		
		return pcs;
	}
	
	public static PC getPCDetail(int pcID) {
		Vector<PC> pc = DatabaseManager.getInstance().executePrepQuery(
				"SELECT * FROM `pcs` WHERE `pcID` = ?",
				sp->{
					int i = 1;
					try {
						sp.setInt(i++, pcID);						
					} catch (Exception e) {
						e.printStackTrace();
					}
				},
				parser->{
					Vector<PC> result = new Vector<>();
					try {
						while (parser.next()) {
							PC newPc = new PC(
									parser.getInt("pcID"),
									parser.getString("pcCondition")
									);
							
							result.add(newPc);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					return result;
				});
		
		if(pc.isEmpty())
			return null;
					
		return pc.get(0);
	}
	
	public static int addNewPC(int pcID) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"INSERT INTO `pcs` (`pcID`, `pcCondition`) VALUES (?, 'Usable')", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, pcID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
	
	public static int updatePC(int pcID, String pcCondition) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"UPDATE `pcs`"
				+ "SET `pcCondition` = ?"
				+ "WHERE `pcID` = ?", 
				sp->{
					try {
						int i = 1;
						sp.setString(i++, pcCondition);
						sp.setInt(i++, pcID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
	
	public static int deletePC(int pcID) {
		return DatabaseManager.getInstance().executePrepUpdate(
				"DELETE FROM `pcs`"
				+ "WHERE `pcID` = ?", 
				sp->{
					try {
						int i = 1;
						sp.setInt(i++, pcID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
	}
}
