package util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import util.Constants;

public class DatabaseManager {
	private static DatabaseManager instance;

	public static DatabaseManager getInstance() {
		if (instance == null)
			instance = new DatabaseManager();
		return instance;
	}

	private Connection connection;

	private DatabaseManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public <T> Vector<T> executePrepQuery(String query, StatementPreparer sp, ResultSetParser<T> parser) {
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			sp.prepare(ps);
			return parser.parse(ps.executeQuery());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Vector<T>();
	}

	public int executePrepUpdate(String query, StatementPreparer sp) {
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			sp.prepare(ps);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
